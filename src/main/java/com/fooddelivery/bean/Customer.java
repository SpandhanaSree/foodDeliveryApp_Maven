package com.fooddelivery.bean;

//importing Lombok annotations
import lombok.AllArgsConstructor; //generates a constructor with all the fields as parameters - Customer customer = new Customer("C001", "John Doe", "1234567890", "123 Main St");
import lombok.Getter; //automatically generates getter methods during compilation
import lombok.NoArgsConstructor; //generates a no-argument constructor - Customer customer = new Customer();
import lombok.Setter; //automatically generates setter methods during compilation
import lombok.ToString; //automatically generates a toString() method during compilation

//Annotations
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString //Object ni readable string ga print cheyyadaniki

public class Customer {
    
    //private means other classes shouldn't directly access the variable - use getter and setter methods instead
    private String customerId;
    private String customerName;
    private String phoneNumber;
    private String address;
}