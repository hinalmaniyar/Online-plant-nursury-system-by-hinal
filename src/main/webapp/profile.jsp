<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="model.User" %>
<%
    HttpSession sessionObj = request.getSession(false);
    User user = (User) sessionObj.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@ include file="include/header.jsp" %> <!-- Navbar/Header -->

<!DOCTYPE html>
<html>
<head>
    <title>My Plant Nursery Profile</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        html, body {
            height: 100%;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #e8f5e9;
        }

        body {
            display: flex;
            flex-direction: column;
        }

        .main-content {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 40px 20px;
        }

        .profile-card {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 8px 25px rgba(0, 128, 0, 0.1);
            width: 380px;
            text-align: center;
            transition: all 0.3s ease;
        }

        .profile-card:hover {
            box-shadow: 0 12px 30px rgba(0, 128, 0, 0.2);
        }

        .profile-title {
            font-size: 28px;
            color: #2e7d32;
            margin-bottom: 20px;
        }

        .greeting {
            font-size: 18px;
            color: #4e4e4e;
            margin-bottom: 30px;
        }

        .label {
            font-weight: 600;
            color: #555;
            margin-bottom: 8px;
            display: block;
        }

        .username-display {
            background-color: #f1f8e9;
            border: 2px dashed #81c784;
            padding: 12px;
            border-radius: 6px;
            font-size: 18px;
            color: #2e7d32;
            font-weight: bold;
        }

        footer {
            margin-top: auto;
        }
    </style>
</head>
<body>

<div class="main-content">
    <div class="profile-card">
        <h2 class="profile-title">My Profile</h2>
        <p class="greeting">Welcome, <strong><%= user.getUsername() %></strong>!</p>

        <label class="label">Username:</label>
        <div class="username-display"><%= user.getUsername() %></div>
    </div>
</div>

<%@ include file="include/footer.jsp" %> <!-- Footer now sticks to bottom -->

</body>
</html>
