package ro.pata.ws.test.jettyhttps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    static {
        if (!System.getProperties().containsKey("logback.configurationFile")) System.setProperty("logback.configurationFile", "logback.xml");
    }

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        App app=new App();

        try{
            app.run();
        } catch (Exception ex){
            log.error("Error in main!",ex);
        }
    }

    private void run() {
        ApplicationContext ctx=new AnnotationConfigApplicationContext(AppConfig.class);
        ((AnnotationConfigApplicationContext) ctx).registerShutdownHook();
    }
}
