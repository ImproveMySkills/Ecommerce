package org.example.services.Impl;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.example.clients.CustomerRestClient;
import org.example.clients.ProductRestClient;
import org.example.dto.Customer;
import org.example.dto.Product;
import org.example.models.Order;
import org.example.repository.OrderRepository;
import org.example.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    OrderRepository orderRepository;
    CustomerRestClient customerRestClient;
    ProductRestClient productRestClient;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        this.orderRepository = orderRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order purchase(Long customerId, HashSet<Long> productIdList) {
        ResponseEntity<Customer> customerResponseEntity = customerRestClient.getCustomerById(customerId);
        if (!HttpStatus.OK.equals(customerResponseEntity.getStatusCode())){
            logger.error("The customer with the id %s does not exist", customerId);
            throw new NotFoundException(String.format("The customer with the id %s does not exist", customerId));
        }

        Order newOrder = orderRepository.save(
                Order.builder().id(UUID.randomUUID().toString())
                        .customerId(customerId).isPaid(Boolean.TRUE).build()
        );

        ResponseEntity<List<Product>> bindToAnOrderResponse = productRestClient.bindToAnOrder(newOrder.getId(), productIdList);
        if (!HttpStatus.OK.equals(bindToAnOrderResponse.getStatusCode())){
            logger.error("An error occured while trying to bind products to the correspondant order");
            throw new RuntimeException("An error occured while trying to bind products to the correspondant order");
        }
        newOrder.setProducts(bindToAnOrderResponse.getBody());

        return newOrder;
    }

    @Override
    public List<Order> getOrderByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}
