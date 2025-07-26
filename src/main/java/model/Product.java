package model;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity; // Quantity in the cart
    private int stock;    // Available stock
    private String imageUrl; // URL for product image

    // Default Constructor
    public Product() {
    }

    // Parameterized Constructor
    public Product(int id, String name, String description, double price, int stock, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;  // Initialize stock
        this.imageUrl = imageUrl; // Initialize image URL
        this.quantity = 0;   // Default quantity when created
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity; // Quantity in the cart
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity; // Update quantity in cart
    }

    public int getStock() {
        return stock; // Available stock
    }

    public void setStock(int stock) {
        this.stock = stock; // Update available stock
    }

    public String getImageUrl() {
        return imageUrl; // URL for product image
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl; // Update product image URL
    }
}