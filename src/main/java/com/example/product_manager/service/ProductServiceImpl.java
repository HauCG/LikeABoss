package com.example.product_manager.service;

import com.example.product_manager.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String UPLOAD_DIR = "C:/Users/maitr/Downloads/images";

    @Override
    public void addProduct(Product product) {
        entityManager.persist(product);
    }

    @Override
    public String saveImage(MultipartFile file) throws IOException {
        try {
            // Đảm bảo thư mục upload tồn tại
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Kiểm tra xem file có tồn tại không
            if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
                throw new IOException("File is empty or invalid");
            }

            // Tạo tên file duy nhất để tránh trùng lặp
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = UUID.randomUUID() + fileExtension;

            // Validate và lưu file
            if (!isImageFile(originalFileName)) {
                throw new IOException("Invalid file type. Please upload an image file.");
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Trả về đường dẫn tương đối
            return "/product-images/" + fileName;
        } catch (IOException e) {
            throw new IOException("Failed to store file: " + e.getMessage(), e);
        }
    }

    private boolean isImageFile(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return false;
        }
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return Arrays.asList("jpg", "jpeg", "png", "gif", "bmp").contains(fileExtension);
    }

    @Override
    public List<Product> getAllProducts() {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p", Product.class);
        return query.getResultList();
    }

    @Override
    public Product getProductById(int id) {
        return entityManager.find(Product.class, (long) id);
    }

    @Override
    public boolean updateProduct(int id, Product updatedProduct) {
        Product product = entityManager.find(Product.class, (long) id);
        if (product != null) {
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setDescription(updatedProduct.getDescription());
            if (updatedProduct.getImgLink() != null) {
                product.setImgLink(updatedProduct.getImgLink());
            }
            entityManager.merge(product);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteProduct(int id) {
        Product product = entityManager.find(Product.class, (long) id);
        if (product != null) {
            entityManager.remove(product);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        String queryStr = "SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(:name)";
        TypedQuery<Product> query = entityManager.createQuery(queryStr, Product.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }
}