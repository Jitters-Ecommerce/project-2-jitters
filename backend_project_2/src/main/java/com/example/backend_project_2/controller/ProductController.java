// package com.example.backend_project_2.controller;

// import com.example.backend_project_2.entity.Product;
// import com.example.backend_project_2.service.ProductService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/products")
// public class ProductController {

//     @Autowired
//     private ProductService productService;

//     // Public endpoint to get all products
//     @GetMapping
//     public ResponseEntity<List<Product>> getAllProducts() {
//         List<Product> products = productService.getAllProducts();
//         return ResponseEntity.ok(products);
//     }

//     // Public endpoint to get product by ID
//     @GetMapping("/{id}")
//     public ResponseEntity<Product> getProductById(@PathVariable Long id) {
//         Product product = productService.getProductById(id);
//         return ResponseEntity.ok(product);
//     }

//     // Admin-only endpoint to create a product
//     @PreAuthorize("hasAuthority('ADMIN')")
//     @PostMapping
//     public ResponseEntity<Product> createProduct(@RequestBody Product product) {
//         Product createdProduct = productService.createProduct(product);
//         return ResponseEntity.ok(createdProduct);
//     }

//     // Admin-only endpoint to update a product
//     @PreAuthorize("hasAuthority('ADMIN')")
//     @PutMapping("/{id}")
//     public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
//         Product updatedProduct = productService.updateProduct(id, product);
//         return ResponseEntity.ok(updatedProduct);
//     }

//     // Admin-only endpoint to delete a product
//     @PreAuthorize("hasAuthority('ADMIN')")
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
//         productService.deleteProduct(id);
//         return ResponseEntity.noContent().build();
//     }
// }


package com.example.backend_project_2.controller;

import com.example.backend_project_2.entity.Product;
import com.example.backend_project_2.service.ProductService;
import com.example.backend_project_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    // Get all products (accessible to everyone)
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Get product by ID (accessible to everyone)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // Create a new product (restricted to admins)
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        userService.verifyAdminAccess(); // Verify the user has admin privileges
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    // Update a product (restricted to admins)
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        userService.verifyAdminAccess(); // Verify the user has admin privileges
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete a product (restricted to admins)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        userService.verifyAdminAccess(); // Verify the user has admin privileges
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
