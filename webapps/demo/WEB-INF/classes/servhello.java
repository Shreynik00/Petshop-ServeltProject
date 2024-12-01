import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

public class servhello extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String usernam = request.getParameter("username");
        String passwor = request.getParameter("userpass");

        if (usernam == null || usernam.isEmpty()) {
            out.print("Enter username");
            return;
        }
        if (passwor == null || passwor.isEmpty()) {
            out.print("Enter password");
            return;
        }

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
           
            Class.forName("org.sqlite.JDBC");

           
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\shreynik jain\\Downloads\\apache-tomcat-9.0.97\\webapps\\demo\\WEB-INF\\classes\\petshop.db");


            String query = "SELECT * FROM register;";
            pstmt = connection.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            boolean isAuthenticated = false;
            while (rs.next()) {
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
            
                if (dbUsername.equals(usernam) && dbPassword.equals(passwor)) {
                    isAuthenticated = true;
                    break; // Exit the loop once a match is found
                }
            }
            
            if (isAuthenticated) {
                out.print("Login Successful");
                RequestDispatcher rd = request.getRequestDispatcher("menu.html");
                rd.forward(request, response);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("index.html");
                rd.include(request, response);
                out.print("Wrong details");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out.print("An error occurred: " + e.getMessage());
        } finally {
          
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
