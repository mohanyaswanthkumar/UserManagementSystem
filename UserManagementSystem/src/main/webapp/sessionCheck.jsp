<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jakarta.servlet.http.HttpSession"%>
<%
    // Check if the user is already logged in
    HttpSession currentSession = request.getSession(false);
    if (currentSession != null && currentSession.getAttribute("username") != null) {
        String role = (String) currentSession.getAttribute("role");

        // Check if the role is not null before comparing
        if ("Employee".equals(role)) {
            response.sendRedirect("requestAccess.jsp");
        } else if ("Manager".equals(role)) {
            response.sendRedirect("pendingRequests.jsp");
        } else if ("Admin".equals(role)) {
            response.sendRedirect("createSoftware.jsp");
        }
        return; // Prevent further processing
    } else {
        // Redirect to login page if not logged in
        response.sendRedirect("login.jsp");
    }
%>
