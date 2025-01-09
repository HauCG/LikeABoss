package com.example.product_manager.service;

import com.example.product_manager.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void addProduct(Product product);

    String saveImage(MultipartFile file) throws IOException;

    List<Product> getAllProducts();

    Product getProductById(int id);

    boolean updateProduct(int id, Product updatedProduct);

    // DELETE: Remove product by ID
    boolean deleteProduct(int id);

    List<Product> searchProductsByName(String name);
}
