package ro.pata.ws.test.tomcat.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/dbop")
public interface DbOperationController {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    Response getPersons();
}
