<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<html>
<head>
    <title>Pending Requests</title>
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
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .logout-button {
            background-color: #f44336; /* Red */
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            float: right; /* Align to the right */
        }
        .logout-button:hover {
            background-color: #d32f2f;
        }
    </style>
</head>
<body>
<%
    HttpSession userSession = request.getSession(false);
    if (userSession == null || !"Manager".equals(userSession.getAttribute("role"))) {
        response.sendRedirect("login.jsp");
        return;
    }

    String dbUrl = "jdbc:postgresql://localhost:5432/user_access_management";
    String dbUser = "postgres";
    String dbPassword = "Bitra98851";
    StringBuilder requestsTable = new StringBuilder();

    try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
        String query = "SELECT r.id AS request_id, u.username AS user_name, s.software_name, r.access_type, r.reason " +
                       "FROM requests r " +
                       "JOIN users u ON r.user_id = u.user_id " +
                       "JOIN software s ON r.software_id = s.software_id " +
                       "WHERE r.status = 'Pending'";

        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            requestsTable.append("<table><tr><th>Employee Name</th><th>Software Name</th><th>Access Type</th><th>Reason</th><th>Actions</th></tr>");

            while (rs.next()) {
                String employeeName = rs.getString("user_name");
                String softwareName = rs.getString("software_name");
                String accessType = rs.getString("access_type");
                String reason = rs.getString("reason");
                int requestId = rs.getInt("request_id"); // Corrected this line to match the SQL alias

                requestsTable.append("<tr>")
                             .append("<td>").append(employeeName).append("</td>")
                             .append("<td>").append(softwareName).append("</td>")
                             .append("<td>").append(accessType).append("</td>")
                             .append("<td>").append(reason).append("</td>")
                             .append("<td>")
                             .append("<form action='approveRequest' method='post' style='display:inline;'>")
                             .append("<input type='hidden' name='requestId' value='").append(requestId).append("'>")
                             .append("<input type='submit' name='action' value='Approve'>")
                             .append("</form>")
                             .append("<form action='ApprovalServlet' method='post' style='display:inline;'>")
                             .append("<input type='hidden' name='requestId' value='").append(requestId).append("'>")
                             .append("<input type='submit' name='action' value='Reject'>")
                             .append("</form>")
                             .append("</td>")
                             .append("</tr>");
            }
            requestsTable.append("</table>");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        response.sendRedirect("error.jsp?error=database");
        return;
    }
%>
<%= requestsTable.toString() %>
<form action="login.jsp" method="post" style="display:inline;">
    <input type="submit" class="logout-button" value="Logout">
</form>
</body>
</html>
