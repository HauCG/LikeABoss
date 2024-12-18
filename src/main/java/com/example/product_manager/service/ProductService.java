package com.example.product_manager.service;

import com.example.product_manager.model.Product;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(int id);

    boolean updateProduct(int id, Product updatedProduct);

    // DELETE: Remove product by ID
    boolean deleteProduct(int id);

    List<Product> searchProductsByName(String name);
}
