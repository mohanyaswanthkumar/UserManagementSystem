package servlets;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


@WebServlet("/approveRequest")
public class ApprovalServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestId = request.getParameter("requestId");
        String status = request.getParameter("status");

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/yourdb", "youruser", "yourpass")) {
            String query = "UPDATE requests SET status = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, Integer.parseInt(requestId));
            ps.executeUpdate();
            response.sendRedirect("pendingRequests.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
