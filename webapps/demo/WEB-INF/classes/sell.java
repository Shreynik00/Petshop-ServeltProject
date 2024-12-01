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


public class sell extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String petGender = request.getParameter("petGender");
        String petQuantityStr = request.getParameter("petQuantity");

        if (username == null || username.isEmpty()) {
            out.print("Enter username");
            return;
        }
        if (petGender == null || petGender.isEmpty()) {
            out.print("Select pet gender");
            return;
        }
        if (petQuantityStr == null) {
            out.print("Enter pet quantity");
            return;
        }

        int petQuantity;
        try {
            petQuantity = Integer.parseInt(petQuantityStr);
        } catch (NumberFormatException e) {
            out.print("Invalid number format for pet quantity");
            return;
        }

        Connection connection = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;
        PreparedStatement deleteStmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\shreynik jain\\Downloads\\apache-tomcat-9.0.97\\webapps\\demo\\WEB-INF\\classes\\petshop.db");

            String selectQuery = "SELECT qty FROM pet WHERE pettype = ? AND gender = ?";
            selectStmt = connection.prepareStatement(selectQuery);
            selectStmt.setString(1, username);
            selectStmt.setString(2, petGender);

            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int currentQuantity = rs.getInt("qty");

                if (petQuantity > currentQuantity) {
                    out.print("Insufficient quantity available.");
                    return;
                }

                int updatedQuantity = currentQuantity - petQuantity;

                if (updatedQuantity == 0) {
                    String deleteQuery = "DELETE FROM pet WHERE pettype = ? AND gender = ?";
                    deleteStmt = connection.prepareStatement(deleteQuery);
                    deleteStmt.setString(1, username);
                    deleteStmt.setString(2, petGender);
                    deleteStmt.executeUpdate();
                    out.print("Record deleted as quantity reached zero.");
                } else {
                    String updateQuery = "UPDATE pet SET qty = ? WHERE pettype = ? AND gender = ?";
                    updateStmt = connection.prepareStatement(updateQuery);
                    updateStmt.setInt(1, updatedQuantity);
                    updateStmt.setString(2, username);
                    updateStmt.setString(3, petGender);
                    updateStmt.executeUpdate();
                    out.print("Quantity updated successfully.");
                }
               
            } else {
                out.print("No matching record found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("An error occurred: " + e.getMessage());
        } finally {
            try {
                
                if (selectStmt != null) selectStmt.close();
                if (updateStmt != null) updateStmt.close();
                if (deleteStmt != null) deleteStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        RequestDispatcher rd = request.getRequestDispatcher("salesreport");
        rd.forward(request, response);
    }
}
