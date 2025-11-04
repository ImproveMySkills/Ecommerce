package org.example.controllers;

import org.example.ConfigProperties;
import org.example.models.Customer;
import org.example.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RefreshScope
public class CustomerController {

    @Value("${global.parms.param1}")
    private Integer param1;

    @Value("${global.parms.param2}")
    private Integer param2;

    @Autowired
    private ConfigProperties  configProperties;


    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers/{id}")
    ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(
                customerService.getCustomer(id).orElse(null)
        );
    }

    @GetMapping("/customers/getconfigs")
    ResponseEntity<Map> getConfig(){
        return ResponseEntity.ok(
                Map.of("param1", param1, "param2", param2,
                        "configParam1", configProperties.getParam1(),
                        "configParam2", configProperties.getParam2()
                        )
        );
    }
    @GetMapping(path = "/customers")
    ResponseEntity<List<Customer>> getAllCustomers(){
        return ResponseEntity.ok(
                customerService.getAllCustomers()
        );
    }

    @PostMapping("/customers")
    ResponseEntity<Customer> postCustomer(@RequestBody Customer customer){
        return ResponseEntity.ok(
                customerService.save(customer)
        );
    }

    @GetMapping("/auth")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }

    @GetMapping("/auth2")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Authentication authentication2(Authentication authentication){
        return authentication;
    }
}
