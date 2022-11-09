package com.example.springmongodemo.practice1.controller;

import com.example.springmongodemo.practice1.domain.Customer;
import com.example.springmongodemo.practice1.domain.Product;
import com.example.springmongodemo.practice1.exception.CustomerAlreadyExistsException;
import com.example.springmongodemo.practice1.service.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerServiceImpl customerService;

    @InjectMocks
    private CustomerController customerController;

    Product product = null;
    Customer customer = null;

    @BeforeEach
    public void setUp(){
        product = new Product(1,"AC","Good");
        customer = new Customer(203,"Tonu",1248524844L,product);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @AfterEach
    public void tearDown(){
        product = null;
        customer = null;
    }

    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }


    @Test
    public void saveCustomerTest() throws Exception {
        when(customerService.saveCustomer(any())).thenReturn(customer);
        mockMvc.perform(
                post("/custdata/api/cust").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(customer))).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(customerService,times(1)).saveCustomer(any());
    }

    @Test
    public void saveCustomerFailureTest() throws Exception {
        when(customerService.saveCustomer(any())).thenThrow(CustomerAlreadyExistsException.class);
        mockMvc.perform(
                post("/custdata/api/cust").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(customer))).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(customerService,times(1)).saveCustomer(any());
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        when(customerService.deleteCustomerById(anyInt())).thenReturn(true);
        mockMvc.perform(delete("/custdata/api/customer/203")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(customerService,times(1)).deleteCustomerById(anyInt());
    }
}
