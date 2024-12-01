import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class viewpet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
          
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\shreynik jain\\Downloads\\apache-tomcat-9.0.97\\webapps\\demo\\WEB-INF\\classes\\petshop.db");

           
            String query = "SELECT * FROM pet";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

           
            out.println("<html><head><title>Pet Details</title></head><body>");
            out.println("<h1>Pet Details</h1>");
            out.println("<table border='1' style='border-collapse: collapse; width: 100%;'>");
            out.println("<tr>");
            out.println("<th>Pet Type</th>");
            out.println("<th>Gender</th>");
            out.println("<th>Quantity</th>");
            out.println("<th>Cost</th>");
            out.println("</tr>");

           
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("pettype") + "</td>");
                out.println("<td>" + rs.getString("gender") + "</td>");
                out.println("<td>" + rs.getInt("qty") + "</td>");
                out.println("<td>" + rs.getDouble("cost") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("An error occurred: " + e.getMessage());
        } finally {
           
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
