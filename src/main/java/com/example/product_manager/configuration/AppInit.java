package com.example.product_manager.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        String uploadPath = "C:\\Users\\maitr\\Downloads\\Product_Manager\\Product_Manager\\uploads";
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(uploadPath, 10240000, 102400000, 0);
        registration.setMultipartConfig(multipartConfigElement);
    }
}