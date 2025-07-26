<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <style>
        /* CSS Styles */
        body {
            font-family: 'Montserrat', sans-serif;
            background-color: #f8f8f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .register-container {
            background-color: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 0 30px rgba(0, 0, 0, 0.1);
            width: 400px;
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
            font-weight: 600;
        }

        .error-message {
            color: #e74c3c;
            text-align: center;
            margin-bottom: 15px;
        }

        .form-group {
            margin-bottom: 25px;
        }

        .form-group label {
            display: block;
            font-size: 14px;
            color: #666;
            margin-bottom: 8px;
        }

        .form-group input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            color: #333;
            box-sizing: border-box;
        }

        .form-group input[type="submit"] {
            background-color: #00b894;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .form-group input[type="submit"]:hover {
            background-color: #00b894;
        }

        .login-link {
            text-align: center;
            margin-top: 15px;
            font-size: 14px;
        }

        .login-link a {
            color: #019a73;
            text-decoration: none;
            transition: color 0.3s ease;
        }

        .login-link a:hover {
            color: #00b894;
        }
    </style>
</head>
<body>
<div class="register-container">
    <h2>Register</h2>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
    <p class="error-message"><%= errorMessage %></p> <!-- Display error message -->
    <%
        }
    %>

    <form action="register" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="confirmPassword">Confirm Password:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>
        <div class="form-group">
            <input type="submit" value="Register">
        </div>
    </form>

    <div class="login-link">
        <a href="login.jsp">Already have an account? Login</a>
    </div>
</div>
</body>
</html>