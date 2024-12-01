import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

public class select extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve the value of the button that was clicked
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            out.print("<h3>Error: No action specified!</h3>");
        } else if ("addpet".equals(action)) {
            RequestDispatcher rd = request.getRequestDispatcher("addpet.html");
            rd.forward(request, response);
        } else if ("viewpet".equals(action)) {
            RequestDispatcher rd = request.getRequestDispatcher("viewpets");
            rd.forward(request, response);
        } else if ("deletepet".equals(action)) {
            RequestDispatcher rd = request.getRequestDispatcher("deletepet.html");
            rd.forward(request, response);
        } else if ("sellpet".equals(action)) {
            RequestDispatcher rd = request.getRequestDispatcher("sellpet.html");
            rd.forward(request, response);
        } else if ("sellsreport".equals(action)) {
            RequestDispatcher rd = request.getRequestDispatcher("reportss");
            rd.forward(request, response);
        } else {
            out.print("<h3>Error: Invalid action!</h3>");
        }

        out.close();
    }
}

//javac -cp "C:\Users\shreynik jain\Downloads\apache-tomcat-9.0.97\webapps\demo\WEB-INF\classes\servlet-api.jar" select.java