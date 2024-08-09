package com.kanku.service.impl;

import com.kanku.dao.IProductRepository;
import com.kanku.model.Product;
import com.kanku.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<String> getProductsCategory() {
        List<String> list = productRepository.findAll().stream()
                .map(Product::getProductCategory)
                .distinct()
                .toList();
        System.out.println(list);
        return list;

    }



    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.getProductsByProductCategory(category);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).get();
    }
}
