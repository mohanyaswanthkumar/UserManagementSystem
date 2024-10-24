package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SoftwareServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/user_access_management"; // Use your database URL
    private static final String DB_USER = "postgres"; // Use your database username
    private static final String DB_PASSWORD = "Bitra98851"; // Use your database password

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String softwareName = request.getParameter("softwareName");
        String description = request.getParameter("description");
        String[] accessLevels = request.getParameterValues("accessLevels");

        // Use StringBuilder to concatenate access levels
        StringBuilder accessLevelsStr = new StringBuilder();
        if (accessLevels != null) {
            for (String level : accessLevels) {
                accessLevelsStr.append(level).append(",");
            }
            // Remove the last comma if it exists
            if (accessLevelsStr.length() > 0) {
                accessLevelsStr.setLength(accessLevelsStr.length() - 1);
            }
        }

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            Class.forName("org.postgresql.Driver");

            // Update the SQL query to use 'software_name'
            String query = "INSERT INTO software (software_name, description,access_levels) VALUES (?, ?,?)";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, softwareName);
                ps.setString(2, description);
                // Note: If you need to store access levels, make sure to add that to your table definition
                // and update the query accordingly.
                 ps.setString(3, accessLevelsStr.toString()); // Uncomment if you add 'access_levels' in the table
                ps.executeUpdate();
            }
            response.sendRedirect("createSoftware.jsp?success=Software added successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("createSoftware.jsp?error=Error adding software.");
        }
    }
}
