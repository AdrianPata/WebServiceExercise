package ro.pata.ws.test.tomcat.rs;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DbOperationControllerImpl implements DbOperationController {
    @Override
    public Response getPersons() {
        return Response.status(Response.Status.OK).entity("Test").type(MediaType.TEXT_PLAIN).build();
    }
}
