package ro.pata.ws.test.jetty;

import javax.xml.ws.Endpoint;

public class App {
    public static void main(String[] args) {
        WsImpl service=new WsImpl();
        String address = "http://localhost:8080/service";
        Endpoint.publish(address, service);
        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
