package com.ecommerce.ecommerce_backend.controller;


import com.ecommerce.ecommerce_backend.model.ProductModel;
import com.ecommerce.ecommerce_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private  ProductService productService;


    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return  new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public  ResponseEntity<ProductModel> getProduct(@PathVariable int id) {
        ProductModel product = productService.getAllProductById(id);

        if (product != null )
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart ProductModel product, @RequestPart MultipartFile imageFile) {
        try {
            ProductModel product1 = productService.addProduct(product, imageFile);
            return  new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int productId) {
        ProductModel product = productService.getAllProductById(productId);
        byte[] imageFile = product.getImageDate();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart ProductModel product, @RequestPart MultipartFile imageFile) {
       try {
           productService.updateProduct(id, product, imageFile);
           return new ResponseEntity<>("Product updated", HttpStatus.OK);
       } catch (IOException e) {
           return new ResponseEntity<>("Faild to Update", HttpStatus.BAD_REQUEST);
       }
    }


    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        ProductModel product = productService.getAllProductById(id);
        if (product != null) {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product deleted", HttpStatus.OK);
        } else
            return new ResponseEntity<>("Product not deleted", HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/products/search")
    public  ResponseEntity<List<ProductModel>> searchProduct(@RequestParam String keyword){
        System.out.println("Searching with " + keyword);
        List<ProductModel> products = productService.searchProduct(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
