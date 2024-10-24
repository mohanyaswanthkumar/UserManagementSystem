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

// Ensure that the servlet is properly annotated
//@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Ensure database connection parameters are correct and use a try-with-resources block
        String dbUrl = "jdbc:postgresql://localhost:5432/user_access_management";
        String dbUser = "postgres";
        String dbPassword = "Bitra98851";

        // Use try-with-resources to handle automatic resource management
        try {
            // Load the PostgreSQL JDBC driver (this line can be optional based on JDBC version)
            Class.forName("org.postgresql.Driver");

            // Establish the connection in try-with-resources to auto-close it
            try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
                // SQL query to check the user role based on the username and password
                String query = "SELECT role FROM users WHERE username = ? AND password = ?";

                // Use try-with-resources for PreparedStatement
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, username);
                    ps.setString(2, password);

                    // Execute the query and handle the result
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            HttpSession session = request.getSession();
                            session.setAttribute("username", username);

                            String role = rs.getString("role");
                            session.setAttribute("role",role);
                            // Redirect based on user role
                            switch (role) {
                                case "Employee":
                                    response.sendRedirect("requestAccess.jsp");
                                    break;
                                case "Manager":
                                    response.sendRedirect("pendingRequests.jsp");
                                    break;
                                case "Admin":
                                    response.sendRedirect("createSoftware.jsp");
                                    break;
                                default:
                                    response.sendRedirect("login.jsp?error=role_not_found");
                                    break;
                            }
                        } else {
                            // Redirect on invalid login
                            request.setAttribute("error", "Invalid username or password.");
                            request.getRequestDispatcher("login.jsp").forward(request, response);

                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Optionally, redirect to an error page or show an error message
            response.sendRedirect("login.jsp?error=database");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Prevent user from accessing login page if already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            response.sendRedirect("error.jsp"); // Redirect to dashboard if already logged in
        } else {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
