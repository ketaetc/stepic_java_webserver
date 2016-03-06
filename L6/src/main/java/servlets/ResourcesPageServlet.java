package servlets;

import resources.ResourceServerI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sax.ReadXMLFileSAX;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * edited by ketaetc
 */
public class ResourcesPageServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(ResourcesPageServlet.class.getName());
    public static final String PAGE_URL = "/resources";
    private final ResourceServerI resourceServer;

    public ResourcesPageServlet(ResourceServerI resourceServer) {
        this.resourceServer = resourceServer;
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");

        String path = request.getParameter("path");
        if (!path.isEmpty()) {

            logger.info("Path: {}", path);

            ResourceServerI resourcesFromXML = (ResourceServerI) ReadXMLFileSAX.readXML(path);


            String name = resourceServer.getName();
            int age = resourceServer.getAge();


            resourceServer.setName(resourcesFromXML.getName());
            resourceServer.setAge(resourcesFromXML.getAge());

            logger.info("Name: {}. Age {}", name, age);

            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            logger.info("Path parameter is empty");
            response.getWriter().println("Path parameter is empty");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

//        if (limit > count) {
//            logger.info("User pass");
//            resources.addNewUser();
//            response.getWriter().println("Hello, world!");
//            response.setStatus(HttpServletResponse.SC_OK);
//        } else {
//            logger.info("User were rejected");
//            response.getWriter().println("Server is closed for maintenance!");
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        }
    }
}
