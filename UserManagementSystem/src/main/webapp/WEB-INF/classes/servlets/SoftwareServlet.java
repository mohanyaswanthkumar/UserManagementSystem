package servlets;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


@WebServlet("/addSoftware")
public class SoftwareServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String softwareName = request.getParameter("name");
        String description = request.getParameter("description");

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/yourdb", "youruser", "yourpass")) {
            String query = "INSERT INTO software (name, description) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, softwareName);
            ps.setString(2, description);
            ps.executeUpdate();
            response.sendRedirect("createSoftware.jsp?success=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
