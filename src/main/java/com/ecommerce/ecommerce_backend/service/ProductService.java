package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.model.ProductModel;
import com.ecommerce.ecommerce_backend.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;
    public List<ProductModel> getAllProducts() {
      return repo.findAll();
    }

    public ProductModel getAllProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public ProductModel addProduct(ProductModel product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return  repo.save(product);
    }

    public ProductModel updateProduct(int id, ProductModel product, MultipartFile imageFile) throws IOException {
        product.setImageDate(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<ProductModel> searchProduct(String keyword) {
       return repo.serachProducts(keyword);
    }
}
