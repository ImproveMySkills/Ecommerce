package org.example.dto;

public record Product(Long id, Long categoryId, double price, double discount,String orderId) {
}
