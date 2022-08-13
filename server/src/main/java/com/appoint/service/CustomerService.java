package com.appoint.service;

import com.appoint.model.Customer;
import com.appoint.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> listAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer getCustomer(Long id){
        return customerRepository.findById(id).get();
    }
    public Customer getCustomerByPhone(String phone){
        return customerRepository.findByPhone(phone).get();
    }

    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }
}
