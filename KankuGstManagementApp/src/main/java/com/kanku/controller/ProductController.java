package com.kanku.controller;

import com.kanku.model.Product;
import com.kanku.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody Product product){

        return  ResponseEntity.ok(productService.addProduct(product));
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProducts(){
       return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/getAllProductsCategory")
    public ResponseEntity<?> getProductCategories(){
       return ResponseEntity.ok(productService.getProductsCategory());
    }

    @GetMapping("/getProductByProductCategory/{category}")
    public ResponseEntity<?> getProductByCategory(@PathVariable("category") String category){
        System.out.println(category);
        List<Product> productByCategory = productService.getProductByCategory(category);
        return ResponseEntity.ok(productByCategory);
    }

    @GetMapping("/getSignleProductByProductId/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") Long productId){

         return  ResponseEntity.ok(productService.getProductById(productId));
    }
}
