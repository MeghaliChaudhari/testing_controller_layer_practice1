package com.example.springmongodemo.practice1.repository;

import com.example.springmongodemo.practice1.domain.Customer;
import com.example.springmongodemo.practice1.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;
    private Product product;
    private Customer customer;

    @BeforeEach
    public void setUp(){
        product = new Product(202,"Bed","Good");
        customer = new Customer(220,"Mani1",214578914535L,product);

    }

    @AfterEach
    public void tearDown(){
        product = null;
        customer = null;
//        customerRepository.deleteAll();
        customerRepository = null;
    }

    @Test
    @DisplayName("Test case for saving customer object")
    public void saveCustomerTest(){
        customerRepository.save(customer);
        Customer customer1 = customerRepository.findById(customer.getCustomerId()).get();
        assertNotNull(customer1);
        assertEquals(customer.getCustomerId(),customer1.getCustomerId());

    }

    @Test
    @DisplayName("Test case for retreiving all customer object")
    public void fetchCustomerTest(){
        customerRepository.insert(customer);
        List<Customer> list = customerRepository.findAll();
        assertEquals(5,list.size());
        assertEquals("Mani",list.get(3).getCustomerName());
    }
    @Test
    @DisplayName("Test case for deleting customer object")
    public void deleteCustomerTest(){
        Customer customer1 = customerRepository.findById(customer.getCustomerId()).get();
        customerRepository.delete(customer1);
        assertEquals(Optional.empty(),customerRepository.findById(customer.getCustomerId()));
    }

}
