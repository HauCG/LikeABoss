package com.example.product_manager.service;

import com.example.product_manager.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final List<Product> products = new ArrayList<>();
    private int currentId = 5;

    public ProductServiceImpl() {
        products.add(new Product(1, "Laptop", 1000.0, "Máy tính xách tay hiệu suất cao", "laptop.jpg"));
        products.add(new Product(2, "Smartphone", 500.0, "Điện thoại thông minh mới nhất", "smartphone.jpg"));
        products.add(new Product(3, "iPhone 13", 800.0, "Điện thoại iPhone 13 chính hãng", "iphone13.jpg"));
        products.add(new Product(4, "iPhone 15", 1100.0, "Điện thoại iPhone 15 chính hãng", "iphone15.jpg"));
        products.add(new Product(5, "iPhone 16", 1200.0, "Điện thoại iPhone 16 chính hãng", "iphone16.jpg"));
    }

    @Override
    public void addProduct(Product product) {
        product.setId(currentId++);
        products.add(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateProduct(int id, Product updatedProduct) {
        Optional<Product> existingProduct = products.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        // kiểm tra product cũ có giá trị ko nếu có thì update  ko thì trả về update ko thành công
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setDescription(updatedProduct.getDescription());
            product.setImgLink(updatedProduct.getImgLink());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteProduct(int id) {
        return products.removeIf(product -> product.getId() == id);
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        String nameLower = name.toLowerCase();
        List<Product> result = new ArrayList<>();
        // chuyển từ về chữ viết thường
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(nameLower)) {
                result.add(product);
            }
        }
        return result;
    }
}
