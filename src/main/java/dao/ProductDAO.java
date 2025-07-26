package dao;

import model.Product;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (Connection connection = DBConnection.getConnection(); // Use the DBConnection class
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("Executing query: " + query); // Debugging line

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setImageUrl(resultSet.getString("imageUrl")); // Set image URL
                products.add(product);

            }

            System.out.println("Total products retrieved: " + products.size()); // Debugging line
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}