package example;

import resources.ResourceServerController;
import resources.ResourceServerControllerMBean;
import resources.ResourceServerI;
import resources.TestResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.ResourcesPageServlet;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * edited by ketaetc
 */
public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            logger.error("Use port as the first argument");
            System.exit(1);
        }

        String portString = args[0];
        int port = Integer.valueOf(portString);

        logger.info("Starting at http://127.0.0.1:" + portString);

        ResourceServerI resourceServer = new TestResource();

        ResourceServerControllerMBean serverStatistics = new ResourceServerController(resourceServer);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=ResourceServerController");
        mbs.registerMBean(serverStatistics, name);

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new ResourcesPageServlet(resourceServer)), ResourcesPageServlet.PAGE_URL);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        System.out.println("Server started");
        logger.info("Server started");

        server.join();
    }
}
