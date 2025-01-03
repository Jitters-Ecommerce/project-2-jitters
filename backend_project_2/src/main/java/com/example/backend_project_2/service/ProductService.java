package com.example.backend_project_2.service;

import com.example.backend_project_2.entity.Product;
import com.example.backend_project_2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get a product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    // Create a new product (Restricted to Admin)
    public Product createProduct(Product product) {
        userService.verifyAdminAccess(); // Ensure user is Admin
        return productRepository.save(product);
    }

    // Update an existing product (Restricted to Admin)
    public Product updateProduct(Long id, Product productDetails) {
        userService.verifyAdminAccess(); // Ensure user is Admin
        Product product = getProductById(id);

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setImageUrl(productDetails.getImageUrl());
        product.setPrice(productDetails.getPrice());
        product.setRating(productDetails.getRating());
        product.setRegion(productDetails.getRegion());
        product.setWeight(productDetails.getWeight());
        product.setFlavorProfile(productDetails.getFlavorProfile());
        product.setGrindOptions(productDetails.getGrindOptions());
        product.setRoastLevel(productDetails.getRoastLevel());

        return productRepository.save(product);
    }

    // Delete a product by ID (Restricted to Admin)
    public void deleteProduct(Long id) {
        userService.verifyAdminAccess(); // Ensure user is Admin
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
