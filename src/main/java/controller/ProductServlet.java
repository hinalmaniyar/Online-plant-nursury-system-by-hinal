package controller;

import dao.ProductDAO;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("doGet called");
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts(); // Fetch products from DAO

        request.setAttribute("products", products); // Set products in request scope
        request.getRequestDispatcher("products.jsp").forward(request, response); // Forward to JSP
    }
}