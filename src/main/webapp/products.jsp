<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>

<%@ include file="include/header.jsp" %>
<!-- Header -->
<header class="header">
    <div class="container">
        <h1>Explore Our Featured Plants</h1>
        <p>Discover a variety of healthy and vibrant plants to beautify your home and garden.</p>
    </div>
</header>

<!-- Product Section -->
<section class="product-section">
    <div class="container">
        <h2 class="section-title">Shop Our Collection</h2>
        <div class="product-cards-container">
            <%
                List<Product> products = (List<Product>) request.getAttribute("products");

                if (products != null && !products.isEmpty()) {
                    for (Product product : products) {
            %>
            <!-- Product Card -->
            <div class="product-card">
                <img src="images/<%= product.getImageUrl() %>" alt="<%= product.getName() %>" class="product-img">
                <div class="product-info">
                    <h3 class="product-name"><%= product.getName() %></h3>
                    <p class="product-description"><%= product.getDescription() %></p>
                    <p class="product-price">$<%= product.getPrice() %></p>
                    <p class="product-stock"><%= product.getStock() %> in stock</p>
                    <form action="CartServlet" method="post">
                        <input type="hidden" name="action" value="add"> <!-- Specify action -->
                        <input type="hidden" name="productId" value="<%= product.getId() %>"> <!-- Product ID -->
                        <input type="number" name="quantity" value="1" min="1" class="quantity-input"> <!-- Quantity input -->
                        <button type="submit" class="add-to-cart-button">
                            Add to Cart <i class="fas fa-shopping-cart"></i> <!-- Cart Icon -->
                        </button>
                    </form>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <p class="no-products">No products available at the moment. Please check back later!</p>
            <%
                }
            %>
        </div>
    </div>
</section>

<!-- Call-to-Action Section -->
<section class="cta-section">
    <div class="container">
        <h2>Looking for Gardening Tips?</h2>
        <p>Visit our <a href="plantCare.jsp">Plant Care</a> section for tips and tricks to keep your plants healthy and happy!</p>
    </div>
</section>

<!-- Footer -->
<%@ include file="include/footer.jsp" %>

</body>
</html>
