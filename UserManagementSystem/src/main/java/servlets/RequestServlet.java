package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return; // Redirect to login if session is invalid
        }

        String username = (String) session.getAttribute("username");
        String softwareName = request.getParameter("softwareName"); // Get software name from the request
        String accessType = request.getParameter("accessType");
        String reason = request.getParameter("reason");

        // Log for debugging
        System.out.println(username + " " + softwareName + " " + accessType + " " + reason);

        // Database connection details
        String dbUrl = "jdbc:postgresql://localhost:5432/user_access_management";
        String dbUser = "postgres";
        String dbPassword = "Bitra98851";

        try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            // Check if software ID exists by querying with the software name
            String softwareIdQuery = "SELECT software_id FROM software WHERE software_name = ?";
            int softwareId = -1; // Initialize softwareId
            try (PreparedStatement softwareIdStmt = con.prepareStatement(softwareIdQuery)) {
                softwareIdStmt.setString(1, softwareName); // Use software name for lookup
                ResultSet softwareRs = softwareIdStmt.executeQuery();
                if (softwareRs.next()) {
                    softwareId = softwareRs.getInt("software_id"); // Retrieve software_id
                } else {
                    response.sendRedirect("requestAccess.jsp?error=SoftwareNotFound"); // Handle software not found
                    return; // Stop further processing if software ID is invalid
                }
            }

            // Query to get user_id from username
            String userIdQuery = "SELECT user_id FROM users WHERE username = ?";
            try (PreparedStatement userIdStmt = con.prepareStatement(userIdQuery)) {
                userIdStmt.setString(1, username);
                ResultSet rs = userIdStmt.executeQuery();
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    // SQL query to insert the request
                    String insertQuery = "INSERT INTO requests (user_id, software_id, access_type, reason, status) VALUES (?, ?, ?, ?, 'Pending')";
                    try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
                        ps.setInt(1, userId); // Set user_id
                        ps.setInt(2, softwareId); // Set software_id from database
                        ps.setString(3, accessType); // Set access_type
                        ps.setString(4, reason); // Set reason

                        // Execute the insert
                        ps.executeUpdate();
                        response.sendRedirect("requestAccess.jsp?success=true"); // Redirect on success
                    }
                } else {
                    response.sendRedirect("requestAccess.jsp?error=UserNotFound"); // Handle user not found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("requestAccess.jsp?error=DatabaseError"); // Handle database error
        }
    }
}
