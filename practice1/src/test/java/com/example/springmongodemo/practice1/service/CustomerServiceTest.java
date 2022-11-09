package com.example.springmongodemo.practice1.service;

import com.example.springmongodemo.practice1.domain.Customer;
import com.example.springmongodemo.practice1.domain.Product;
import com.example.springmongodemo.practice1.exception.CustomerAlreadyExistsException;
import com.example.springmongodemo.practice1.exception.CustomerNotFoundException;
import com.example.springmongodemo.practice1.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer1,customer2;
    List<Customer> customerList;
    Product product1,product2;

    @BeforeEach
    public void setUp(){
        product1 = new Product(2,"Tv","Good");
        product2 = new Product(3,"Refrigirator","Good");
        customer1 = new Customer(201,"Sonu",2145789655L,product1);
        customer2 = new Customer(202,"Monu",1452526685L,product2);

        customerList = Arrays.asList(customer1,customer2);
    }

    @AfterEach
    void tearDown() {
        customer1 = null;
        customer2 = null;

    }

    @Test
    public void saveCustomerTest() throws CustomerAlreadyExistsException {
        when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.ofNullable(null));
        when(customerRepository.save(any())).thenReturn(customer1);
        assertEquals(customer1,customerService.saveCustomer(customer1));
        verify(customerRepository,times(1)).save(any());
        verify(customerRepository,times(1)).findById(any());
    }

    @Test
    public void saveCustomerFailureTest(){
        when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.ofNullable(customer1));
        assertThrows(CustomerAlreadyExistsException.class,()->customerService.saveCustomer(customer1));
        verify(customerRepository,times(0)).save(any());
        verify(customerRepository,times(1)).findById(any());
    }

    @Test
    public void deleteCustomerTest() throws CustomerNotFoundException {
        when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.ofNullable(customer1));
        boolean flag = customerService.deleteCustomerById(customer1.getCustomerId());
        assertEquals(true,flag);
        verify(customerRepository,times(1)).deleteById(any());
        verify(customerRepository,times(1)).findById(any());
    }
}
