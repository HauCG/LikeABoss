package com.example.product_manager.controller;

import com.example.product_manager.model.Product;
import com.example.product_manager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class MainController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "/product/product_list";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "/product/product_detail";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "/product/add_product";
    }

//    @PostMapping("/add")
//    public String addProduct(@ModelAttribute("product") Product product) {
//        productService.addProduct(product);
//        return "redirect:/products";
//    }

    @PostMapping("/add")
    public String addProduct(
            @ModelAttribute("product") Product product,
            @RequestParam("image") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please select an image file to upload.");
            return "redirect:/products/add";
        }

        try {
            // Đảm bảo thư mục upload tồn tại
            String uploadDir = "C://Users/maitr/Downloads/images";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

//             Tạo tên file duy nhất để tránh trùng lặp
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + fileExtension;

            // Validate và lưu file
            if (!isImageFile(originalFileName)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Invalid file type. Please upload an image file.");
                return "redirect:/products/add";
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Lưu đường dẫn tương đối vào database
            product.setImgLink("/images/" + fileName);
            productService.addProduct(product);

            redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error uploading image: " + e.getMessage());
            return "redirect:/products/add";
        }

        return "redirect:/products";
    }

    private boolean isImageFile(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return Arrays.asList("jpg", "jpeg", "png", "gif", "bmp").contains(fileExtension);
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "/product/edit_product";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute Product updatedProduct) {
        boolean updated = productService.updateProduct(id, updatedProduct);
        if (updated) {
            return "redirect:/products";
        } else {
            return "redirect:/products";
        }
    }

    @GetMapping("/delete/{id}")
    public String showConfirmDeleteProduct(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "/product/confirm_delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam String name, Model model) {
        List<Product> products = productService.searchProductsByName(name);
        model.addAttribute("products", products);
        return "/product/product_list";
    }
}
