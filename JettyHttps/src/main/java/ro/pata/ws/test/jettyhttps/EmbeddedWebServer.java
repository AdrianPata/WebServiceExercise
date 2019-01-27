package ro.pata.ws.test.jettyhttps;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.annotation.PostConstruct;

@Component
public class EmbeddedWebServer implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(EmbeddedWebServer.class);

    private Server server;
    private ApplicationContext appContext;
    private XmlWebApplicationContext webContext;

    @PostConstruct
    public void start() throws Exception {
        server=new Server();

        ServerConnector connector=new ServerConnector(server);
        connector.setPort(9999);

        HttpConfiguration https=new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());
        SslContextFactory sslContextFactory=new SslContextFactory();
        sslContextFactory.setKeyStorePath("https.ks");
        sslContextFactory.setKeyStorePassword("salam");
        sslContextFactory.setKeyManagerPassword("salam");
        sslContextFactory.setTrustStorePath("trust.ks");
        sslContextFactory.setTrustStorePassword("salam");
        sslContextFactory.setNeedClientAuth(true);
        ServerConnector sslConnector=new ServerConnector(server,new SslConnectionFactory(sslContextFactory,"http/1.1"),new HttpConnectionFactory(https));
        sslConnector.setPort(9998);

        server.setConnectors(new Connector[] {connector,sslConnector});

        ServletHolder servletHolder=new ServletHolder(new CXFServlet());
        ServletContextHandler context=new ServletContextHandler();

        context.setContextPath("/services");
        context.addServlet(servletHolder,"/*");

        webContext=new XmlWebApplicationContext();
        webContext.setParent(appContext);
        webContext.setServletContext(context.getServletContext());
        webContext.setConfigLocations("classpath:/Beans.xml");
        webContext.refresh();

        context.addEventListener(new ContextLoaderListener(webContext));

        HandlerCollection handlers=new HandlerCollection();
        handlers.addHandler(context);

        RequestLogHandler requestLogHandler=new RequestLogHandler();
        requestLogHandler.setRequestLog(new Slf4jRequestLog());
        handlers.addHandler(requestLogHandler);

        server.setHandler(handlers);
        server.start();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }
}
