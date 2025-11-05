package org.example.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.dto.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerRestClient {

    @GetMapping("/api/customers/{id}")
        @CircuitBreaker(name = "customerservice", fallbackMethod = "getDefaultCustomer")

    ResponseEntity<Customer> getCustomerById(@PathVariable Long id);
    @GetMapping(path = "/api/customers")
    ResponseEntity<List<Customer>> getAllCustomers();

    @PostMapping("/api/customers")
    ResponseEntity<Customer> postCustomer(@RequestBody Customer customer);

    default ResponseEntity<Customer> getDefaultCustomer(Long id, Exception exception){
        return new ResponseEntity<>(new Customer(1L, "UNKNOWN", "UNKNOWN", "UNKNOWN@improvemyskills.com"), HttpStatus.ACCEPTED);
    }
}
