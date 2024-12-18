package com.example.product_manager.controller;

import com.example.product_manager.model.Product;
import com.example.product_manager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Product product) {
        productService.addProduct(product);
        return "redirect:/products";
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
