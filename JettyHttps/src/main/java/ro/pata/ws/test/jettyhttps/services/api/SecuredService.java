package ro.pata.ws.test.jettyhttps.services.api;

@FunctionalInterface
public interface SecuredService {
    //Returns the roles needed to access a specific service.
    ServiceRole[] getRoles();
}
