package com.appoint.controller;

import com.appoint.model.Customer;
import com.appoint.service.CustomerService;
import com.appoint.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @PostMapping("")
    public ResponseEntity<Customer> add(@RequestBody Customer customer) {
        try{
            Customer newCustomer = customerService.saveCustomer(customer);
            return new ResponseEntity<Customer>(newCustomer, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Customer>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @GetMapping("")
    public List<Customer> list(){
        return customerService.listAllCustomer();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> get(@PathVariable Long id){
        try {
            Customer customer = customerService.getCustomer(id);
            return new ResponseEntity<Customer>(customer, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/phone/{phone}")
    public ResponseEntity<Customer> get(@PathVariable String phone){
        try {
            Customer customer = customerService.getCustomerByPhone(phone);
            return new ResponseEntity<Customer>(customer, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Customer customer, @PathVariable Long id) {
        try {
            Customer existCustomer = customerService.getCustomer(id);
            customer.setId(id);
            customerService.saveCustomer(customer);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
