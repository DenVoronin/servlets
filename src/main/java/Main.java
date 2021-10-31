import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {
    public static void main(String[] args){
        System.out.println("Start main class");
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        server.setHandler(context);
        context.addServlet(servlets.Servlet1.class, "/first");
        context.addServlet(servlets.Servlet2.class, "/second");
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("OK: server started");
    }
}
