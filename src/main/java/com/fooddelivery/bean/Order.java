package com.fooddelivery.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

    private String orderId;
    private String customerId;
    private String foodName;
    private int quantity;
    private double price;
    private double totalAmount;
}