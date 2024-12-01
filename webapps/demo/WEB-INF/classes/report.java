import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class report extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
           
            Class.forName("org.sqlite.JDBC");

       
            String dbPath = getServletContext().getRealPath("/WEB-INF/classes/petshop.db");
            String dbURL = "jdbc:sqlite:" + dbPath;

         
            connection = DriverManager.getConnection(dbURL);

           
            String query = "SELECT * FROM sales";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            out.println("<html><head><title>Sales Report</title>");
            out.println("<style>");
            out.println("table { border-collapse: collapse; width: 100%; }");
            out.println("th, td { border: 1px solid #ddd; padding: 8px; }");
            out.println("th { background-color: #f2f2f2; text-align: center; }");
            out.println("</style></head><body>");
            out.println("<h1>Sales Report</h1>");
            out.println("<table>");
            out.println("<tr><th>Pet Type</th><th>Quantity</th><th>Gender</th><th>Date</th><th>Cost</th></tr>");

          
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("pettype") + "</td>");
                out.println("<td>" + rs.getInt("qty") + "</td>");
                out.println("<td>" + rs.getString("gender") + "</td>");
                out.println("<td>" + rs.getString("date") + "</td>");
                out.println("<td>" + rs.getDouble("cost") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p style='color: red;'>An error occurred: " + e.getMessage() + "</p>");
        } finally {
            
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            out.close();
        }
    }
}
