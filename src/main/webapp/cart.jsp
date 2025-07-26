<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Cart - Online Plant Nursery</title>
    <style>
        /* General Styles */
        body {
            margin: 0;
            font-family: 'Arial', sans-serif;
            color: black;
            display: flex;
            flex-direction: column;
            height: 100vh;
            justify-content: space-between;
        }

        nav {
            background-color: rgba(0, 0, 0, 0.7);
            padding: 15px 0;
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        nav ul {
            list-style: none;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: flex-end;
            align-items: center;
        }

        nav ul li {
            margin: 0 20px;
        }

        nav ul li a {
            color: white;
            text-decoration: none;
            font-weight: bold;
            font-size: 1.2rem;
            padding: 5px 15px;
            transition: background-color 0.3s ease, color 0.3s ease;
        }

        nav ul li a:hover {
            background-color: rgba(255, 255, 255, 0.2);
            color: #00b894;
            border-radius: 5px;
        }

        .website-name {
            font-size: 1.8rem;
            font-weight: bold;
            color: white;
            padding-left: 20px;
        }

        .website-name a {
            text-decoration: none;
            color: white;
        }

        .cart-section .container {
            padding: 50px 20px;
            background-color: #f8f9fa;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            margin-top: 80px;
        }

        .cart-table-container {
            overflow-x: auto;
            margin-top: 20px;
        }

        .cart-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        .cart-table th, .cart-table td {
            padding: 15px;
            text-align: center;
            border-bottom: 1px solid #ddd;
            color: #333;
        }

        .cart-table th {
            background-color: #00b894;
            color: white;
            font-weight: bold;
        }

        .cart-table tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        .cart-table tr:hover {
            background-color: #eafaf1;
        }

        .cart-summary h3 {
            text-align: right;
            font-size: 1.5rem;
            margin-top: 10px;
            color: #333;
        }

        footer {
            background-color: #00000080;
            color: white;
            text-align: center;
            padding: 15px;
        }
    </style>
</head>
<body>

<nav>
    <div class="website-name">
        <a href="index.jsp">Plant Nursery</a>
    </div>
    <ul>
        <li><a href="index.jsp">Home</a></li>
        <li><a href="products">Products</a></li>

        <li><a href="CartServlet">Cart</a></li>
        <li><a href="login.jsp">Login</a></li>
        <li><a href="register.jsp">Register</a></li>
    </ul>
</nav>

<section class="cart-section">
    <div class="container">
        <h2>Your Shopping Cart</h2>
        <%
            List<Product> cartItems = (List<Product>) session.getAttribute("cart");
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }

            if (!cartItems.isEmpty()) {
        %>
        <div class="cart-table-container">
            <table class="cart-table">
                <thead>
                <tr>
                    <th>Product Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <%
                    double grandTotal = 0.0;
                    for (Product product : cartItems) {
                        double total = product.getPrice() * product.getQuantity();
                        grandTotal += total;
                %>
                <tr>
                    <td><%= product.getName() %></td>
                    <td><%= product.getDescription() %></td>
                    <td>$<%= String.format("%.2f", product.getPrice()) %></td>
                    <td><%= product.getQuantity() %></td>
                    <td>$<%= String.format("%.2f", total) %></td>
                    <td>
                        <form action="CartServlet" method="post">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="productId" value="<%= product.getId() %>">
                            <button type="submit" style="background: none; border: none; color: #d63031; font-weight: bold; cursor: pointer;">Remove</button>
                        </form>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <h3 class="cart-summary">Grand Total: $<%= String.format("%.2f", grandTotal) %></h3>
        </div>
        <% } else { %>
        <p>Your cart is empty. Please add some products!</p>
        <% } %>
    </div>
</section>

<footer>
    <p>&copy; 2024 Plant Nursery. All rights reserved.</p>
</footer>

</body>
</html>
