<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
</head>
<body style="font-family: Arial, sans-serif; background-color: #f4f4f9;">

    <div style="max-width: 400px; margin: 100px auto; background-color: #fff; padding: 20px;
                border-radius: 8px; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);">
        <form action="signup" method="post">
            <h2 style="text-align: center; color: #333;">Sign Up</h2>

            <!-- Error message display -->
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
                Sign Up
            </button>

            <p style="text-align: center; margin-top: 20px;">Already have an account?
                <a href="login.jsp" style="color: #007bff; text-decoration: none;">Login</a>
            </p>
        </form>
    </div>
</body>
</html>
