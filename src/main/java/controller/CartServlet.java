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
            response.sendRedirect("login.jsp");
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
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add":
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));

                    // Add the product to the cart
                    insertIntoCart(userId, productId, quantity);
                    session.setAttribute("cartMessage", "Product added to cart.");
                    break;

                case "remove":
                    try {
                        int removeProductId = Integer.parseInt(request.getParameter("productId"));
                        boolean isRemoved = removeFromCart(userId, removeProductId);

                        if (isRemoved) {
                            session.setAttribute("cartMessage", "Product removed from cart.");
                        } else {
                            session.setAttribute("cartMessage", "Product not found in cart.");
                        }
                    } catch (NumberFormatException e) {
                        session.setAttribute("cartMessage", "Invalid product ID.");
                        e.printStackTrace();
                    }
                    break;

                default:
                    System.out.println("Unknown action: " + action);
            }
        }

        // Refresh the cart data after any action
        List<Product> cartItems = getCartItems(userId);
        session.setAttribute("cart", cartItems);

        response.sendRedirect("cart.jsp");
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
                        0,
                        null
                );
                product.setQuantity(resultSet.getInt("quantity"));
                cartItems.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }

    private void insertIntoCart(int userId, int productId, int quantity) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?) " +
                             "ON DUPLICATE KEY UPDATE quantity = quantity + ?")) {

            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.setInt(3, quantity);
            statement.setInt(4, quantity);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean removeFromCart(int userId, int productId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM cart WHERE user_id = ? AND product_id = ?")) {

            statement.setInt(1, userId);
            statement.setInt(2, productId);

            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows affected (delete): " + rowsAffected);

            return rowsAffected > 0; // Return true if the product was successfully removed
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
