package org.example.services;

import org.example.models.Order;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAllOrders();
    Optional<Order> getOrderById(String id);
    Order purchase(Long customerId, HashSet<Long> productIdList);
    List<Order> getOrderByCustomer(Long customerId);
}
