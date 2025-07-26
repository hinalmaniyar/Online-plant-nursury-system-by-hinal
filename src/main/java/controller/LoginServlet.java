package controller;

import model.User; // Make sure you import your User class
import util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isValidUser = false;
        int userId = -1;

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password); // In real apps, hash the password

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    isValidUser = true;
                    userId = resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error. Please try again later.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        if (isValidUser) {
            HttpSession session = request.getSession();

            // ✅ Create and store a User object in session
            User user = new User();
            user.setId(userId);
            user.setUsername(username); // You already have it from the form

            session.setAttribute("userId", userId); // existing line
            session.setAttribute("user", user);     // new line for profile.jsp use

            response.sendRedirect("index.jsp");
        } else {
            request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
