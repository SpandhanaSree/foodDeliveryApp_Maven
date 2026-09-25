package com.fooddelivery.repository;

import com.fooddelivery.bean.Customer;
import com.fooddelivery.bean.Order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FoodDeliveryRepositoryTest {

    private Path customerFile;
    private Path orderFile;
    private FoodDeliveryRepository repository;

    @BeforeEach
    void setUp() throws IOException {

        // Create temporary CSV files for testing
        customerFile = Files.createTempFile("test-customers", ".csv");
        orderFile = Files.createTempFile("test-orders", ".csv");

        // Add test customer data
        Files.writeString(
                customerFile,
                "customerId,customerName,phoneNumber,address\n" +
                "C001,Ravi,9876543210,Hyderabad\n" +
                "C002,Anil,9988776655,Secunderabad\n"
        );

        // Add test order data
        Files.writeString(
                orderFile,
                "orderId,customerId,foodName,quantity,price,totalAmount\n" +
                "O001,C001,Chicken Biryani,2,250,500\n" +
                "O002,C002,Veg Fried Rice,1,180,180\n" +
                "O003,C001,Pizza,1,300,300\n"
        );

        // Create repository using test CSV files
        repository = new FoodDeliveryRepository(
                customerFile.toString(),
                orderFile.toString()
        );
    }

    @Test
    void testCustomersLoadedFromCSV() {

        List<Customer> customers = repository.getAllCustomers();

        assertEquals(2, customers.size());
        assertEquals("C001", customers.get(0).getCustomerId());
        assertEquals("Ravi", customers.get(0).getCustomerName());
    }

    @Test
    void testOrdersLoadedFromCSV() {

        List<Order> orders = repository.getAllOrders();

        assertEquals(3, orders.size());
        assertEquals("O001", orders.get(0).getOrderId());
        assertEquals("Chicken Biryani", orders.get(0).getFoodName());
    }

    @Test
    void testFindExistingCustomer() {

        Customer customer = repository.findCustomerById("C001");

        assertNotNull(customer);
        assertEquals("Ravi", customer.getCustomerName());
        assertEquals("9876543210", customer.getPhoneNumber());
    }

    @Test
    void testFindNonExistingCustomer() {

        Customer customer = repository.findCustomerById("C999");

        assertNull(customer);
    }

    @Test
    void testFindExistingOrder() {

        Order order = repository.findOrderById("O001");

        assertNotNull(order);
        assertEquals("C001", order.getCustomerId());
        assertEquals("Chicken Biryani", order.getFoodName());
        assertEquals(2, order.getQuantity());
    }

    @Test
    void testFindNonExistingOrder() {

        Order order = repository.findOrderById("O999");

        assertNull(order);
    }

    @Test
    void testAddCustomer() {

        Customer customer = new Customer(
                "C003",
                "Sita",
                "9123456789",
                "Warangal"
        );

        repository.addCustomer(customer);

        Customer savedCustomer =
                repository.findCustomerById("C003");

        assertNotNull(savedCustomer);
        assertEquals("Sita", savedCustomer.getCustomerName());
    }

    @Test
    void testAddOrder() {

        Order order = new Order(
                "O004",
                "C001",
                "Burger",
                2,
                150,
                300
        );

        repository.addOrder(order);

        Order savedOrder =
                repository.findOrderById("O004");

        assertNotNull(savedOrder);
        assertEquals("Burger", savedOrder.getFoodName());
        assertEquals(2, savedOrder.getQuantity());
        assertEquals(300, savedOrder.getTotalAmount());
    }

    @Test
    void testGetOrdersByCustomerId() {

        List<Order> orders =
                repository.getOrdersByCustomerId("C001");

        assertEquals(2, orders.size());

        assertEquals("O001", orders.get(0).getOrderId());
        assertEquals("O003", orders.get(1).getOrderId());
    }

   @Test
void testSaveCustomers() throws IOException {

    Customer customer = new Customer(
            "C003",
            "Sita",
            "9123456789",
            "Warangal"
    );

    repository.addCustomer(customer);

    FoodDeliveryRepository reloadedRepository =
            new FoodDeliveryRepository(
                    customerFile.toString(),
                    orderFile.toString()
            );

    Customer savedCustomer =
            reloadedRepository.findCustomerById("C003");

    assertNotNull(savedCustomer);
    assertEquals("Sita", savedCustomer.getCustomerName());
    assertEquals("9123456789", savedCustomer.getPhoneNumber());
    assertEquals("Warangal", savedCustomer.getAddress());
}

@Test
void testSaveOrders() throws IOException {

    Order order = new Order(
            "O004",
            "C001",
            "Burger",
            2,
            150,
            300
    );

    repository.addOrder(order);

    FoodDeliveryRepository reloadedRepository =
            new FoodDeliveryRepository(
                    customerFile.toString(),
                    orderFile.toString()
            );

    Order savedOrder =
            reloadedRepository.findOrderById("O004");

    assertNotNull(savedOrder);
    assertEquals("C001", savedOrder.getCustomerId());
    assertEquals("Burger", savedOrder.getFoodName());
    assertEquals(2, savedOrder.getQuantity());
    assertEquals(150, savedOrder.getPrice());
    assertEquals(300, savedOrder.getTotalAmount());
}
}