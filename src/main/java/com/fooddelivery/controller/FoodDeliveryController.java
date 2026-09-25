package com.fooddelivery.controller;

import java.util.List;

import com.fooddelivery.bean.Customer;
import com.fooddelivery.bean.Order;
import com.fooddelivery.service.FoodDeliveryService;


public class FoodDeliveryController {
    
    private static FoodDeliveryController instance;
    private FoodDeliveryController() {
        
    }

    public static FoodDeliveryController getInstance() {
        if (instance == null) {
            instance = new FoodDeliveryController();
        }
        return instance;
    }
    
    private FoodDeliveryService service = FoodDeliveryService.getInstance();

    public boolean addCustomer(Customer customer) {
        return service.addCustomer(customer);
    }

    public Customer findCustomerById(String customerId) {
        return service.findCustomerById(customerId);
    }

     public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }

    public boolean addOrder(Order order) {
    return service.addOrder(order);
}

    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }

    public Order findOrderById(String orderId) {
        return service.findOrderById(orderId);
    }

    public List<Order> getOrdersByCustomerId(String customerId) {
        return service.getOrdersByCustomerId(customerId);
    }
}
