package com.fooddelivery.service;

import java.util.List;
import com.fooddelivery.bean.Customer;
import com.fooddelivery.bean.Order;
import com.fooddelivery.repository.FoodDeliveryRepository;

public class FoodDeliveryService {

    
    private static FoodDeliveryService instance;
    
    
    private FoodDeliveryService(){
        
    }

    public static FoodDeliveryService getInstance() {
        if (instance == null) {
            instance = new FoodDeliveryService();
        }
        return instance;        
    }

    private FoodDeliveryRepository repository = FoodDeliveryRepository.getInstance();

    public boolean addCustomer(Customer customer) {
        Customer existingCustomer = repository.findCustomerById(customer.getCustomerId());
            if (existingCustomer != null) {
                return false; 
            }
        repository.addCustomer(customer);  
        return true; 
    }

    public Customer findCustomerById(String customerId) { 
        return repository.findCustomerById(customerId); 
    }

    public List<Customer> getAllCustomers() {
        return repository.getAllCustomers(); 
    }

    public boolean addOrder(Order order) {

        
        Customer customer = repository.findCustomerById(order.getCustomerId());

        if (customer == null) {
            return false;
        }

        
        Order existingOrder = repository.findOrderById(order.getOrderId());

        if (existingOrder != null) {
            return false;
        }

        
        if (order.getQuantity() <= 0) {
            return false;
        }

        
        double totalAmount = order.getPrice() * order.getQuantity();
        order.setTotalAmount(totalAmount);

       
        repository.addOrder(order);

        return true;
    }

    public List<Order> getAllOrders() {
        return repository.getAllOrders();
    }

    public Order findOrderById(String orderId) {
        return repository.findOrderById(orderId);
    }

    public List<Order> getOrdersByCustomerId(String customerId) {
        return repository.getOrdersByCustomerId(customerId);
    }
}