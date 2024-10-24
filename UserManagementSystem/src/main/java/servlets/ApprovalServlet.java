package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException; // Update import
import jakarta.servlet.annotation.WebServlet; // Update import
import jakarta.servlet.http.HttpServlet; // Update import
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ApprovalServlet")
public class ApprovalServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/user_access_management";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Bitra98851";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql;
            if ("Approve".equals(action)) {
                sql = "UPDATE requests SET status = 'Approved' WHERE id = ?";
            } else if ("Reject".equals(action)) {
                sql = "UPDATE requests SET status = 'Rejected' WHERE id = ?";
            } else {
                throw new IllegalArgumentException("Invalid action: " + action);
            }
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, requestId);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?error=database");
            return;
        }
        response.sendRedirect("pendingRequests.jsp");
    }
}
