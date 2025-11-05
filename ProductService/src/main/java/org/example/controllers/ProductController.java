package org.example.controllers;

import org.example.models.Product;
import org.example.repository.ProductRepository;
import org.example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
        @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Product> postProduct(@RequestBody Product product){
        return ResponseEntity.ok(
                productService.save(product)
        );
    }

    @GetMapping("/products")
        @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok(
                productService.getAllProducts()
        );
    }

    @GetMapping("/products/{productId}")
        @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<Product> getProduct(@PathVariable Long productId){
        return ResponseEntity.ok(
                productService.getProduct(productId).orElse(null)
        );
    }

    @GetMapping("/auth")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

    @PostMapping("/products/bind/{orderId}")
    ResponseEntity<List<Product>> bindToAnOrder(@PathVariable String orderId, @RequestBody HashSet<Long> productIdList){
        return ResponseEntity.ok(
                productService.bindToAnOrder(orderId, productIdList)
        );
    }
}
