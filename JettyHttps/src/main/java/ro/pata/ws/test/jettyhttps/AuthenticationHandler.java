package ro.pata.ws.test.jettyhttps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Component;
import ro.pata.ws.test.jettyhttps.services.api.ServiceRole;
import ro.pata.ws.test.jettyhttps.services.api.SecuredService;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.security.auth.x500.X500Principal;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Component("authenticationHandler")
public class AuthenticationHandler implements ContainerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Context
    private ResourceInfo resourceInfo;

    @Autowired
    private ApplicationContext appContext;

    public void filter(ContainerRequestContext ctx) throws IOException {
        List<Object> destinations= ctx.getUriInfo().getMatchedResources();

        //We are expecting exactly one service resource to be matched.
        if(destinations.size()!=1){
            log.error("More than one service is matched on the server side. This is unexpected.");
            ctx.abortWith(Response.status(500,"More than one service is matched on the server side. This is unexpected.").build());
            return;
        }

        //Get the service class. The list should contain exactly one element.
        Object serviceResource=destinations.get(0);

        //Test is the resource is marked as secure (it needs authorization).
        if(SecuredService.class.isAssignableFrom((Class<?>) serviceResource)){
            //Get the provided certificates
            X509Certificate[] certificates = (X509Certificate[]) ctx.getProperty("javax.servlet.request.X509Certificate");

            //No certificates provided. The service cannot be authorized without a client certificate.
            if(certificates==null){
                log.error("The target service requires a client certificate for authentication.");
                ctx.abortWith(Response.status(401).entity("This service requires client certificate for authentication.").build());
                return;
            }

            //Get the client certificate (from tests it should be the first)
            X500Principal principal = certificates[0].getSubjectX500Principal();

            //Get the roles from the certificate
            ServiceRole[] certificateRoles;
            try {
                certificateRoles=getRolesFromDN(principal.getName("RFC1779"));
            } catch (InvalidNameException| NoSuchElementException e) {
                log.error("Error while parsing the DN in the client certificate. Err:"+e.getMessage());
                ctx.abortWith(Response.status(500).entity("Error while parsing the DN in the client certificate. Err:"+e.getMessage()).build());
                return;
            }

            SecuredService service= (SecuredService) appContext.getBean(((Class<?>) serviceResource));
            ServiceRole[] allowedRoles=service.getRoles();

            boolean authorized=false;
            for(ServiceRole r:allowedRoles){
                if(Arrays.stream(certificateRoles).anyMatch(r::equals)){
                    authorized=true;
                    break;
                }
            }

            if(!authorized){
                log.warn("Client certificate is not allowed to access the resource."+principal);
                ctx.abortWith(Response.status(401).entity("Provided certificate is not allowed to access the resource.").build());
            }
        }
    }

    private ServiceRole[] getRolesFromDN(String dn) throws InvalidNameException {
        ServiceRole[] certificateRoles;
        String serial=LdapUtils.getStringValue(new LdapName(dn), "OID.2.5.4.5"); //SERIALNUMBER
        String[] roles=serial.split("\\|"); //In the serial number field of the DN of the certificate there can be multiple roles slitted by |.

        certificateRoles=new ServiceRole[roles.length];
        for(int i=0;i<roles.length;i++){
            certificateRoles[i]= ServiceRole.valueOf(roles[i]);
        }

        return certificateRoles;
    }
}
