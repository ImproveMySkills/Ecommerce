package org.example.services;

import org.example.models.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer save(Customer customer);
    Optional<Customer> getCustomer(Long customerId);
    List<Customer> getAllCustomers();
}
