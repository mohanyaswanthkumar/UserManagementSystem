<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<html>
<head>
    <title>Request Access</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            padding: 20px;
            background-color: #f4f4f4;
        }
        h2 {
            color: #333;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        select, textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"], .logout-button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        input[type="submit"]:hover, .logout-button:hover {
            background-color: #45a049;
        }
        .logout-button {
            background-color: #f44336; /* Red color for logout button */
            margin-bottom: 20px; /* Add margin to separate from form */
        }
    </style>
</head>
<body>
<%
    HttpSession userSession = request.getSession(false);
    if (userSession == null || !"Employee".equals(userSession.getAttribute("role"))) {
        // Redirect if session is invalid or user is not an Employee
        response.sendRedirect("login.jsp");
        return;
    }

    // Database connection variables
    String dbUrl = "jdbc:postgresql://localhost:5432/user_access_management";
    String dbUser = "postgres";
    String dbPassword = "Bitra98851";
    StringBuilder softwareOptions = new StringBuilder();
    try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
        String query = "SELECT software_name FROM software"; // Ensure this column name matches your database
        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String softwareName = rs.getString("software_name"); // Ensure this column name matches your database
                softwareOptions.append("<option value='").append(softwareName).append("'>").append(softwareName).append("</option>");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        response.sendRedirect("error.jsp?error=database");
        return;
    }
%>
<h2>Request Access to Software</h2>

<!-- Logout Button -->
<form action="logout" method="post">
    <input type="submit" class="logout-button" value="Logout">
</form>

<form action="requestAccess" method="post">
    <label for="softwareName">Software Name:</label>
    <select id="softwareName" name="softwareName" required>
        <option value="">Select Software</option>
        <%= softwareOptions.toString() %>
    </select>

    <label for="accessType">Access Type:</label>
    <select id="accessType" name="accessType" required>
        <option value="Read">Read</option>
        <option value="Write">Write</option>
        <option value="Admin">Admin</option>
    </select>

    <label for="reason">Reason for Request:</label>
    <textarea id="reason" name="reason" required></textarea>

    <input type="submit" value="Request Access">
</form>
</body>
</html>
