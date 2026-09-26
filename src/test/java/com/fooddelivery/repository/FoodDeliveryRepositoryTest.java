package com.fooddelivery.repository;

import com.fooddelivery.bean.Customer;
import com.fooddelivery.bean.Order;

import org.junit.jupiter.api.BeforeEach; //Prathi test run avvadaniki mundu oka particular method ni execute chesthundi
import org.junit.jupiter.api.Test; //Test method ni identify cheyyadaniki use chesthundi

import java.io.IOException;
import java.nio.file.Files; //Files create/write cheyyadaniki Java utility class
import java.nio.file.Path; //File location/path ni represent chesthundi
import java.util.List;

import static org.junit.jupiter.api.Assertions.*; //JUnit lo results verify chestundi - assertEquals, assertNotNull, assertNull, assertTrue, assertFalse

class FoodDeliveryRepositoryTest {

    private Path customerFile;
    private Path orderFile;
    private FoodDeliveryRepository repository;

    @BeforeEach //This is the setup method - Prathi test run avvadaniki mundu execute avvadaniki
    void setUp() throws IOException { //throws IOException - file operations exception throw cheyyachu.

        // Create temporary CSV files for testing
        customerFile = Files.createTempFile("test-customers", ".csv");
        orderFile = Files.createTempFile("test-orders", ".csv");

        // Add test customer data
        Files.writeString(  //customerFile file lo ee String content write cheyyi
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
                customerFile.toString(), //customerFile is a Path, Path ni String ga convert chesthunam
                orderFile.toString()
        );
    }

    @Test
    void testCustomersLoadedFromCSV() { //Repository CSV nunchi customers correct ga load chesthundha check cheyyadaniki test method

        List<Customer> customers = repository.getAllCustomers();

        assertEquals(2, customers.size()); //2 - expected value, customers.size() - actual value
        assertEquals("C001", customers.get(0).getCustomerId());
        assertEquals("Ravi", customers.get(0).getCustomerName());
    }

    @Test
    void testOrdersLoadedFromCSV() { //Repository CSV nunchi orders correct ga load chesthundha check cheyyadaniki test method

        List<Order> orders = repository.getAllOrders();

        assertEquals(3, orders.size());
        assertEquals("O001", orders.get(0).getOrderId());
        assertEquals("Chicken Biryani", orders.get(0).getFoodName());
    }

    @Test
    void testFindExistingCustomer() { //Repository lo existing customer ni find chesthundha check cheyyadaniki test method

        Customer customer = repository.findCustomerById("C001");

        assertNotNull(customer);
        assertEquals("Ravi", customer.getCustomerName());
        assertEquals("9876543210", customer.getPhoneNumber());
    }

    @Test
    void testFindNonExistingCustomer() { //Repository lo non-existing customer ni find chesthundha check cheyyadaniki test method

        Customer customer = repository.findCustomerById("C999");

        assertNull(customer);
    }

    @Test
    void testFindExistingOrder() { //Repository lo existing order ni find chesthundha check cheyyadaniki test method

        Order order = repository.findOrderById("O001");

        assertNotNull(order);
        assertEquals("C001", order.getCustomerId());
        assertEquals("Chicken Biryani", order.getFoodName());
        assertEquals(2, order.getQuantity());
    }

    @Test
    void testFindNonExistingOrder() { //Repository lo non-existing order ni find chesthundha check cheyyadaniki test method

        Order order = repository.findOrderById("O999");

        assertNull(order);
    }

    @Test
    void testAddCustomer() { //Repository lo new customer ni add chesthundha check cheyyadaniki test method

        Customer customer = new Customer(
                "C003",
                "Sita",
                "9123456789",
                "Warangal"
        );

        repository.addCustomer(customer);

        Customer savedCustomer = repository.findCustomerById("C003");

        assertNotNull(savedCustomer); //Customer object null kaadu ani check chesthundhi
        assertEquals("Sita", savedCustomer.getCustomerName());
    }

    @Test
    void testAddOrder() { //Repository lo new order ni add chesthundha check cheyyadaniki test method

        Order order = new Order(
                "O004",
                "C001",
                "Burger",
                2,
                150,
                300
        );

        repository.addOrder(order);

        Order savedOrder = repository.findOrderById("O004");

        assertNotNull(savedOrder);
        assertEquals("Burger", savedOrder.getFoodName());
        assertEquals(2, savedOrder.getQuantity());
        assertEquals(300, savedOrder.getTotalAmount());
    }

    @Test
    void testGetOrdersByCustomerId() { //Repository lo particular customerId ki orders correct ga return chesthundha check cheyyadaniki test method

        List<Order> orders =
                repository.getOrdersByCustomerId("C001");

        assertEquals(2, orders.size());

        assertEquals("O001", orders.get(0).getOrderId());
        assertEquals("O003", orders.get(1).getOrderId());
    }

   @Test
   void testSaveCustomers() throws IOException { //Repository lo new customer add chesaka CSV file lo correct ga save chesthundha check cheyyadaniki test method

    Customer customer = new Customer(
            "C003",
            "Sita",
            "9123456789",
            "Warangal"
    );

    repository.addCustomer(customer);

    //Create a NEW Repository instance to load data from the CSV files
    FoodDeliveryRepository reloadedRepository = new FoodDeliveryRepository(
                    customerFile.toString(),
                    orderFile.toString()
            );

    //Check if the newly added customer is present in the reloaded repository
    Customer savedCustomer = reloadedRepository.findCustomerById("C003");

    assertNotNull(savedCustomer);
    assertEquals("Sita", savedCustomer.getCustomerName());
    assertEquals("9123456789", savedCustomer.getPhoneNumber());
    assertEquals("Warangal", savedCustomer.getAddress());
}

@Test
void testSaveOrders() throws IOException { //Repository lo new order add chesaka CSV file lo correct ga save chesthundha check cheyyadaniki test method

        Order order = new Order(
                    "O004",
                 "C001",
                 "Burger",
                 2,
                 150,
                 300
                );

        repository.addOrder(order);

        //Create a NEW Repository instance to load data from the CSV files
        FoodDeliveryRepository reloadedRepository = new FoodDeliveryRepository(
                            customerFile.toString(),
                            orderFile.toString()
                );

        Order savedOrder = reloadedRepository.findOrderById("O004");

                assertNotNull(savedOrder);
                assertEquals("C001", savedOrder.getCustomerId());
                assertEquals("Burger", savedOrder.getFoodName());
                assertEquals(2, savedOrder.getQuantity());
                assertEquals(150, savedOrder.getPrice());
                assertEquals(300, savedOrder.getTotalAmount());
        }
}

/*So entire test class ni 3 categories ga divide chestham
 1️⃣ LOAD tests - CSV → Java objects correct ga load ayyaya?
        testCustomersLoadedFromCSV()
        testOrdersLoadedFromCSV()
 2️⃣ FIND tests - Repository correct data ni search/filter chesthundha?
        testFindExistingCustomer()
        testFindNonExistingCustomer()

        testFindExistingOrder()
        testFindNonExistingOrder()

        testGetOrdersByCustomerId()
 3️⃣ SAVE tests - Java object → CSV correct ga save avuthundha? And saved data ni malli load cheyyagalama?

Entire Test Flow :-

                JUnit 5
                   │
                   ▼
             @BeforeEach
                   │
                   ▼
        Create temporary CSVs
                   │
          ┌────────┴────────┐
          ▼                 ▼
   customers.csv       orders.csv
          │                 │
          └────────┬────────┘
                   ▼
          FoodDeliveryRepository
                   │
        ┌──────────┼───────────┐
        ▼          ▼           ▼
      LOAD        FIND        SAVE
        │          │           │
        ▼          ▼           ▼
      Tests      Tests       Tests
        │          │           │
        └──────────┼───────────┘
                   ▼
             Assertions
                   │
             PASS / FAIL
 */