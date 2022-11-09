package com.example.springmongodemo.practice1.service;

import com.example.springmongodemo.practice1.domain.Customer;
import com.example.springmongodemo.practice1.exception.CustomerAlreadyExistsException;
import com.example.springmongodemo.practice1.exception.CustomerNotFoundException;
import com.example.springmongodemo.practice1.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) throws CustomerAlreadyExistsException {
        if (customerRepository.findById(customer.getCustomerId()).isPresent()){
            throw new CustomerAlreadyExistsException();
        }
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomer() throws CustomerNotFoundException {
        return customerRepository.findAll();
    }

    @Override
    public boolean deleteCustomerById(int customerId) throws CustomerNotFoundException {
        boolean res = false;
        if (customerRepository.findById(customerId).isEmpty()){
            throw new CustomerNotFoundException();
        }else {
            customerRepository.deleteById(customerId);
            res = true;
        }
        return res;
    }

    @Override
    public List<Customer> getCustomerByProductName(String productName) throws CustomerNotFoundException {
        if (customerRepository.findAllDetailsOfCustomerByProductName(productName).isEmpty()){
            throw new CustomerNotFoundException();
        }
        return customerRepository.findAllDetailsOfCustomerByProductName(productName);
    }
}
