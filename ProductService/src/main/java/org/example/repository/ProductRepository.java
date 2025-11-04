package org.example.repository;

import org.example.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

//RepositoryRestResource
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getByOrderId(String orderId);
}
