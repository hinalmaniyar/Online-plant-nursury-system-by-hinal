package controller;

import model.Product;
import util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            System.out.println("User ID is not set in session.");
            response.sendRedirect("login.jsp"); // Redirect to login page if userId is not set
            return;
        }

        // Retrieve all cart items for the user from the database
        List<Product> cartItems = getCartItems(userId);

        // Update the session with the latest cart items
        session.setAttribute("cart", cartItems);

        // Forward to the cart.jsp page
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action"); // Retrieve action parameter
        HttpSession session = request.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>(); // Initialize cart if it doesn't exist
        }

        // Check if userId exists in session
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp"); // Redirect to login page if userId is not set
            return; // Exit the method
        }

        if ("add".equals(action)) { // Add product to cart
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Product product = getProductById(productId); // Fetch product details

            if (product != null) {
                boolean found = false;
                for (Product p : cart) {
                    if (p.getId() == product.getId()) {
                        p.setQuantity(p.getQuantity() + quantity); // Update existing product quantity
                        found = true;
                        break;
                    }
                }

                // If the product does not exist in the cart, add it
                if (!found) {
                    product.setQuantity(quantity);
                    cart.add(product);
                }

                // Insert into database
                insertIntoCart(userId, productId, quantity);
            } else {
                System.out.println("Product not found for ID: " + productId);
            }
        } else if ("remove".equals(action)) { // Remove product from cart
            int productId = Integer.parseInt(request.getParameter("productId"));
            cart.removeIf(product -> product.getId() == productId); // Remove product from cart
            removeFromCart(userId, productId); // Remove from database
        }

        session.setAttribute("cart", cart); // Update session with new cart
        response.sendRedirect("cart.jsp"); // Redirect to the cart page
    }

    private List<Product> getCartItems(int userId) {
        List<Product> cartItems = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT p.id, p.name, p.description, p.price, c.quantity " +
                             "FROM products p JOIN cart c ON p.id = c.product_id WHERE c.user_id = ?")) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        0,  // Assuming stock is not needed here; replace with actual value if available.
                        null  // Assuming imageUrl is not needed here; replace with actual URL if available.
                );
                product.setQuantity(resultSet.getInt("quantity")); // Set quantity from database
                
                // Print product details to console for debugging
                System.out.println("Product ID: " + product.getId());
                System.out.println("Product Name: " + product.getName());
                System.out.println("Description: " + product.getDescription());
                System.out.println("Price: $" + String.format("%.2f", product.getPrice()));
                System.out.println("Quantity in Cart: " + product.getQuantity());
                System.out.println("-----------------------------------");
                
                cartItems.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log any SQL exceptions
        }

        return cartItems; // Return list of products in the user's cart
    }

    private Product getProductById(int id) {
        Product product = null; 
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM products WHERE id = ?")) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock"),
                        resultSet.getString("imageUrl")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return product; 
    }

    private void insertIntoCart(int userId, int productId, int quantity) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?) " +
                                     "ON DUPLICATE KEY UPDATE quantity=quantity + ?")) {

            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.setInt(3, quantity);
            statement.setInt(4, quantity);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Successfully added/updated item in the cart.");
            } else {
                System.out.println("No rows affected. Check if the user ID and product ID are correct.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void removeFromCart(int userId, int productId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             "DELETE FROM cart WHERE user_id = ? AND product_id = ?")) {

            statement.setInt(1, userId);
            statement.setInt(2, productId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Successfully removed item from the cart.");
            } else {
                System.out.println("No rows affected. Check if the item exists in the cart.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}