    package controller;

    import util.DBConnection; // Import the DBConnection class
    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.io.IOException;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.SQLException;

     // Ensure this annotation is present for servlet mapping
    public class RegisterServlet extends HttpServlet {

        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            System.out.println("RegisterServlet");
            String username = request.getParameter("username");
            System.out.println(username);
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            System.out.println(password);

            // Basic validation checks
            if (username == null || username.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Username is required.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            if (!password.equals(confirmPassword)) {
                request.setAttribute("errorMessage", "Passwords do not match.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Insert user into the database
            try (Connection connection = DBConnection.getConnection()) { // Use DBConnection to get the connection
                String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    statement.setString(2, password);

                    statement.executeUpdate();
                }
                // Redirect to login page after successful registration
                response.sendRedirect("login.jsp");
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        }
    }