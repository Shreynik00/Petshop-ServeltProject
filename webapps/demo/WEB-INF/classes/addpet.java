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

public class addpet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        
        String petType = request.getParameter("petType");
        String petGender = request.getParameter("petGender");
        String petQuantityStr = request.getParameter("petQuantity");
        String petCostStr = request.getParameter("petCost");


        if (petType == null || petType.isEmpty()) {
          
            return;
        }
        if (petGender == null || petGender.isEmpty()) {
          
            return;
        }
        if (petQuantityStr == null || petQuantityStr.isEmpty()) {
           
            return;
        }
        if (petCostStr == null || petCostStr.isEmpty()) {
         
            return;
        }

        int petQuantity;
        double petCost;

        try {
            petQuantity = Integer.parseInt(petQuantityStr);
            petCost = Double.parseDouble(petCostStr);
        } catch (NumberFormatException e) {
            out.print("Invalid number format for quantity or cost");
            return;
        }

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
        
            Class.forName("org.sqlite.JDBC");

            
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\shreynik jain\\Downloads\\apache-tomcat-9.0.97\\webapps\\demo\\WEB-INF\\classes\\petshop.db");

            
            String query = "INSERT INTO pet (pettype, gender, qty, cost) VALUES (?, ?, ?, ?)";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, petType);
            pstmt.setString(2, petGender);
            pstmt.setInt(3, petQuantity);
            pstmt.setDouble(4, petCost);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
            
            } else {
                out.print("Failed to add pet");
            }
            RequestDispatcher rd = request.getRequestDispatcher("menu.html");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        
        } finally {

            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
               
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
