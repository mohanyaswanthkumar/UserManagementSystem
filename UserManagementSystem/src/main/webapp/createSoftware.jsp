<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<html>
<head>
    <title>Create Software</title>
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
        input[type="text"], textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="checkbox"] {
            margin-right: 10px;
        }
        input[type="submit"], .logout-button {
            background-color: #4CAF50; /* Green */
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            font-size: 16px;
        }
        input[type="submit"]:hover, .logout-button:hover {
            background-color: #45a049; /* Darker green */
        }
        .logout-button {
            background-color: #dc3545; /* Red */
            margin-top: 20px;
            text-align: center;
        }
        .logout-button:hover {
            background-color: #c82333; /* Darker red */
        }
    </style>
</head>
<body>
<%
    HttpSession userSession = request.getSession(false);
    if (userSession == null || !"Admin".equals(userSession.getAttribute("role"))) {
        // Handle the case where the session is null or user is not Admin
        response.sendRedirect("login.jsp");
        return;
    }
%>
<h2>Create New Software</h2>
<form action="addSoftware" method="post">
    <label for="softwareName">Software Name:</label>
    <input type="text" id="softwareName" name="softwareName" required>
    <label for="description">Description:</label>
    <textarea id="description" name="description" required></textarea>
    <label>Access Levels:</label>
    <input type="checkbox" name="accessLevels" value="Read"> Read<br>
    <input type="checkbox" name="accessLevels" value="Write"> Write<br>
    <input type="checkbox" name="accessLevels" value="Admin"> Admin<br>
    <input type="submit" value="Add Software">
</form>

<!-- Logout Button -->
<form action="logout" method="post" style="margin-top: 20px;">
    <input type="submit" value="Logout" class="logout-button">
</form>

</body>
</html>
