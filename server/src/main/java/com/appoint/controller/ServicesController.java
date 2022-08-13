package com.appoint.controller;

import com.appoint.model.Services;
import com.appoint.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/services")
public class ServicesController {
    @Autowired
    ServicesRepository servicesRepository;

    @GetMapping("")
    public List<Services> list() {
        return servicesRepository.findAll();
    }

    @PostMapping("")
    public void add (@RequestBody Services serviceProvider) {
        servicesRepository.save(serviceProvider);
    }

    @PutMapping("")
    public ResponseEntity<?> update (@RequestBody Services serviceProvider) {
        try {

            Services existingServices = servicesRepository.findById(serviceProvider.getId()).get();
            serviceProvider.setId(serviceProvider.getId());
            servicesRepository.save(serviceProvider);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Integer id) {
        try {
            Services service = servicesRepository.findById(id).get();
            servicesRepository.delete(service);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
