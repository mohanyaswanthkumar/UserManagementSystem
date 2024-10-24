<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="sessionCheck.jsp" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body style="font-family: Arial, sans-serif; background-color: #f4f4f9;">
    <div style="max-width: 400px; margin: 100px auto; background-color: #fff; padding: 20px;
                border-radius: 8px; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);">
        <form action="login" method="post">
            <h2 style="text-align: center; color: #333;">Login</h2>

            <c:if test="${not empty error}">
                <div style="background-color: #f8d7da; color: #721c24; padding: 10px;
                            border: 1px solid #f5c6cb; border-radius: 4px; margin-bottom: 15px;">
                    ${error}
                </div>
            </c:if>

            <label for="username" style="display: block; margin: 10px 0;">Username:</label>
            <input type="text" id="username" name="username" required
                   style="width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ccc; border-radius: 4px;">

            <label for="password" style="display: block; margin: 10px 0;">Password:</label>
            <input type="password" id="password" name="password" required
                   style="width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ccc; border-radius: 4px;">

            <button type="submit" style="width: 100%; padding: 10px; background-color: #5cb85c;
                                        color: white; border: none; border-radius: 4px; cursor: pointer;">
                Login
            </button>

            <p style="text-align: center; margin-top: 20px;">Don't have an account?
                <a href="signup.jsp" style="color: #007bff; text-decoration: none;">Sign Up</a>
            </p>
        </form>
    </div>
</body>
</html>
