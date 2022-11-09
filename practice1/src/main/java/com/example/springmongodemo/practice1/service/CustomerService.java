package com.example.springmongodemo.practice1.service;

import com.example.springmongodemo.practice1.domain.Customer;
import com.example.springmongodemo.practice1.exception.CustomerAlreadyExistsException;
import com.example.springmongodemo.practice1.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customer) throws CustomerAlreadyExistsException;

    List<Customer> getAllCustomer() throws CustomerNotFoundException;

    boolean deleteCustomerById(int customerId) throws CustomerNotFoundException;

    List<Customer> getCustomerByProductName(String productName) throws CustomerNotFoundException;
}
