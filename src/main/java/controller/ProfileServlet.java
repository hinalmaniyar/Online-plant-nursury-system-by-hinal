package controller;

import model.User;
import util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class ProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        String newPassword = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE users SET password = ? WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, newPassword);
                ps.setString(2, user.getUsername());
                ps.executeUpdate();
                user.setPassword(newPassword);
                session.setAttribute("user", user);
                request.setAttribute("message", "Password updated successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("profile.jsp");
    }
}
