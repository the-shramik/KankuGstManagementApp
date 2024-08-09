package com.kanku.service;

import com.kanku.model.Product;

import java.util.List;

public interface IProductService {

    Product addProduct(Product product);

    List<Product> getAllProducts();

    List<String> getProductsCategory();

    List<Product> getProductByCategory(String category);

    Product getProductById(Long productId);
}
