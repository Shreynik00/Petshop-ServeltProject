import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class salesreport extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieving form data from the request
        String petType = request.getParameter("username");  // Pet type (name) field from the form
        String petGender = request.getParameter("petGender"); // Pet gender (Male/Female)
        String petQuantityStr = request.getParameter("petQuantity"); // Pet quantity field
        String petCostStr = request.getParameter("petCost"); // Pet cost field

        // Input validation for required fields
        if (petType == null || petType.isEmpty()) {
            out.print("<p style='color:red;'>Enter pet type (name).</p>");
            return;
        }
        if (petGender == null || petGender.isEmpty()) {
            out.print("<p style='color:red;'>Select pet gender.</p>");
            return;
        }
        if (petQuantityStr == null || petQuantityStr.isEmpty()) {
            out.print("<p style='color:red;'>Enter pet quantity.</p>");
            return;
        }
        if (petCostStr == null || petCostStr.isEmpty()) {
            out.print("<p style='color:red;'>Enter pet cost.</p>");
            return;
        }

        // Convert quantity and cost from strings to integers and floats
        int petQuantity;
        float petCost;
        try {
            petQuantity = Integer.parseInt(petQuantityStr);
            petCost = Float.parseFloat(petCostStr);
        } catch (NumberFormatException e) {
            out.print("<p style='color:red;'>Invalid number format for quantity or cost.</p>");
            return;
        }

        // Database connection variables
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Establish connection to the SQLite database
            String dbURL = "jdbc:sqlite:C:\\Users\\shreynik jain\\Downloads\\apache-tomcat-9.0.97\\webapps\\demo\\WEB-INF\\classes\\petshop.db";
            connection = DriverManager.getConnection(dbURL);

            // SQL query to insert data into sales table
            String insertQuery = "INSERT INTO sales (pettype, qty, gender, date, cost) VALUES (?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(insertQuery);

            // Setting values for the prepared statement
            pstmt.setString(1, petType);  // Pet type (name)
            pstmt.setInt(2, petQuantity);  // Pet quantity
            pstmt.setString(3, petGender);  // Pet gender

            // Get current date in yyyy-MM-dd format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(new Date());
            pstmt.setString(4, currentDate);  // Current date

            pstmt.setFloat(5, petCost);  // Pet cost

            // Execute the insertion
            int rowsInserted = pstmt.executeUpdate();

            // Check if insertion was successful
            if (rowsInserted > 0) {
              
                // Optionally redirect to another page or display a success message
                // RequestDispatcher rd = request.getRequestDispatcher("successPage.jsp");
                // rd.forward(request, response);
            } else {
                out.print("<p style='color:red;'>Failed to insert sale record.</p>");
            }

        } catch (Exception e) {
            // Print the exception stack trace and show an error message
            e.printStackTrace();
            out.print("<p style='color:red;'>An error occurred: " + e.getMessage() + "</p>");
        } finally {
            // Ensure resources are closed in the finally block
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
