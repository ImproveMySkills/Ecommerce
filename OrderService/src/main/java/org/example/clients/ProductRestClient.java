package org.example.clients;

import org.example.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductRestClient {
    @PostMapping("/api/products")
    ResponseEntity<Product> postProduct(@RequestBody Product product);

    @GetMapping("/api/products")
    ResponseEntity<List<Product>> getAll();

    @GetMapping("/api/products/{productId}")
    ResponseEntity<Product> getProduct(@PathVariable Long productId);

    @PostMapping("/api/products/bind/{orderId}")
    ResponseEntity<List<Product>> bindToAnOrder(@PathVariable String orderId, @RequestBody HashSet<Long> productIdList);
}
