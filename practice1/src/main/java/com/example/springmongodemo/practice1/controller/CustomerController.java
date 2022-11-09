package com.example.springmongodemo.practice1.controller;


import com.example.springmongodemo.practice1.domain.Customer;
import com.example.springmongodemo.practice1.exception.CustomerAlreadyExistsException;
import com.example.springmongodemo.practice1.exception.CustomerNotFoundException;
import com.example.springmongodemo.practice1.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/custdata/api")
public class CustomerController {
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    public CustomerController(CustomerServiceImpl customerServiceImpl) {
        this.customerServiceImpl = customerServiceImpl;
    }

    @PostMapping("cust")
    public ResponseEntity<?> insertData(@RequestBody Customer customer) throws CustomerAlreadyExistsException {
        return new ResponseEntity<>(customerServiceImpl.saveCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping("custs")
    public ResponseEntity<?> fetchDataOfCustomer() throws CustomerNotFoundException {
        ResponseEntity responseEntity = null;

        try {
            responseEntity = new ResponseEntity<>(customerServiceImpl.getAllCustomer(),HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException();
        }catch(Exception e){
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping("customer/{customerId}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable("customerId") int customerId) throws CustomerNotFoundException {
        ResponseEntity responseEntity = null;

        try{
            customerServiceImpl.deleteCustomerById(customerId);
            responseEntity = new ResponseEntity<>("Succesfully Deleted",HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException();
        }catch (Exception e){
            responseEntity = new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("customer/{productName}")
    public ResponseEntity<?> getCustomerByProductName(@PathVariable("productName") String productName) throws CustomerNotFoundException {
        ResponseEntity responseEntity = null;

        try {
            responseEntity = new ResponseEntity(customerServiceImpl.getCustomerByProductName(productName),HttpStatus.FOUND);
        }catch (CustomerNotFoundException e){
            throw new CustomerNotFoundException();
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
