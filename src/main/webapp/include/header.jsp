<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css"> <!-- Link to your CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

</head>
<body>

<!-- Navbar -->
<nav>
    <div class="website-name">
        <a href="index.jsp">Plant Nursery</a>
    </div>

    <ul>
        <li><a href="index.jsp">Home</a></li>
        <li><a href="products">Products</a></li>
        <li><a href="CartServlet">Cart</a></li>

        <% if (session.getAttribute("userId") != null) { %>
            <li><a href="profile.jsp">Profile</a></li>
            <li><a href="logout.jsp">Logout</a></li>
        <% } else { %>
            <li><a href="login.jsp">Login</a></li>
            <li><a href="register.jsp">Register</a></li>
        <% } %>
    </ul>
</nav>

</body>
</html>
