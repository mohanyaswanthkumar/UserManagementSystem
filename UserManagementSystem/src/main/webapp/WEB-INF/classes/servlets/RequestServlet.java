package servlets;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


@WebServlet("/requestAccess")
public class RequestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        String softwareId = request.getParameter("softwareId");
        String accessType = request.getParameter("accessType");
        String reason = request.getParameter("reason");

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/yourdb", "youruser", "yourpass")) {
            String query = "INSERT INTO requests (user_id, software_id, access_type, reason, status) VALUES (?, ?, ?, ?, 'Pending')";
            PreparedStatement ps = con.prepareStatement(query);
            // Query to get user_id from username and then fill the other fields.
            ps.executeUpdate();
            response.sendRedirect("requestAccess.jsp?success=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
