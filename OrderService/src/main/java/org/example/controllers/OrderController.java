package org.example.controllers;

import org.example.clients.CustomerRestClient;
import org.example.dto.Customer;
import org.example.models.Order;
import org.example.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    Logger logger = LoggerFactory.getLogger(OrderController.class);
    OrderService orderService;
    CustomerRestClient  customerRestClient;

    public OrderController(OrderService orderService,  CustomerRestClient customerRestClient) {
        this.orderService = orderService;
        this.customerRestClient = customerRestClient;
    }

    @GetMapping("/orders/{id}")
    ResponseEntity<Order> getOrderById(@PathVariable String id){
        return ResponseEntity.ok(
                orderService.getOrderById(id).orElse(null)
        );
    }
    @GetMapping("/orders")
    ResponseEntity<List<Order>> getAllOrders(){
        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }
    @PostMapping("/orders/purshase/{customerId}")
    ResponseEntity<Order> purshase(@PathVariable Long customerId, @RequestBody HashSet<Long> productIdList){
        if (productIdList == null || productIdList.isEmpty()){
            throw new IllegalArgumentException("Param productIdList cannot be null or empty");
        }
        return ResponseEntity.ok(
                orderService.purchase(customerId, productIdList)
        );
    }

    @GetMapping("/orders/getbycustomerid/{customerId}")
    ResponseEntity<List<Order>> getOrderByCustomer(@PathVariable Long customerId){
        return ResponseEntity.ok(
                orderService.getOrderByCustomer(customerId)
        );
    }

    @GetMapping("/circuitbreaker/{id}")
    ResponseEntity<Customer> getOrderByCustomerId(@PathVariable Long id){
        return  customerRestClient.getCustomerById(id);
    }
}
