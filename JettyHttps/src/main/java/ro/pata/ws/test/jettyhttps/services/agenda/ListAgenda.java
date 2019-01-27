package ro.pata.ws.test.jettyhttps.services.agenda;

import org.springframework.stereotype.Component;
import ro.pata.ws.test.jettyhttps.services.api.ServiceRole;
import ro.pata.ws.test.jettyhttps.services.api.SecuredService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component("agenda.list")
public class ListAgenda implements SecuredService {

    private ServiceRole[] allowedRoles={ServiceRole.ADMIN};

    @Override
    public ServiceRole[] getRoles() {
        return allowedRoles;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/agenda/list")
    public Response listAgenda(){
        return Response.ok("List agenda", "text/plain").build();
    }
}
