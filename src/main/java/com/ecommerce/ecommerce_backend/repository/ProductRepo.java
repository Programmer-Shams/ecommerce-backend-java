package com.ecommerce.ecommerce_backend.repository;

import com.ecommerce.ecommerce_backend.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<ProductModel, Integer> {

}
