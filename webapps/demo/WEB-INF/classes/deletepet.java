import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher; 
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class deletepet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

     
        String petType = request.getParameter("petType");
        String petGender = request.getParameter("petGender");

    
        if (petType == null || petType.isEmpty()) {
            out.print("Enter pet type");
            return;
        }
        if (petGender == null || petGender.isEmpty()) {
            out.print("Select pet gender");
            return;
        }

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
          
            Class.forName("org.sqlite.JDBC");

           
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\shreynik jain\\Downloads\\apache-tomcat-9.0.97\\webapps\\demo\\WEB-INF\\classes\\petshop.db");

           
            String query = "DELETE FROM pet WHERE pettype = ? AND gender = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, petType);
            pstmt.setString(2, petGender);

            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                
            } else {
                out.print("<h2>No matching pet found to delete</h2>");
            }
           
            RequestDispatcher rd = request.getRequestDispatcher("menu.html");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            out.print("<h2>An error occurred: " + e.getMessage() + "</h2>");
        } finally {
           
            try {
                if (pstmt != null) pstmt.close();
               
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
