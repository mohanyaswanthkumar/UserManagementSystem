package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

//@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String dbUrl = "jdbc:postgresql://localhost:5432/user_access_management";
        String dbUser = "postgres";
        String dbPassword = "Bitra98851";

        try {
            Class.forName("org.postgresql.Driver");

            try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
                // Check if the user already exists
                String checkUserQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
                try (PreparedStatement checkPs = con.prepareStatement(checkUserQuery)) {
                    checkPs.setString(1, username);
                    try (ResultSet rs = checkPs.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            // User already exists
                            request.setAttribute("error", "Username already exists.");
                            request.getRequestDispatcher("signup.jsp").forward(request, response);
                            return;
                        }
                    }
                }

                // If user doesn't exist, insert into the database
                String insertQuery = "INSERT INTO users (username, password, role) VALUES (?, ?, 'Employee')";
                try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
                    ps.setString(1, username);
                    ps.setString(2, password);
                    ps.executeUpdate();
                }

                // Redirect to login page after successful signup
                response.sendRedirect("login.jsp?signup=success");

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("signup.jsp?error=database");
        }
    }
}