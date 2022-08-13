package com.appoint.controller;

import com.appoint.model.Business;
import com.appoint.model.ServiceProvider;
import com.appoint.service.FilesStorageService;
import com.appoint.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/serviceproviders")
public class ServiceProviderController {

    @Autowired
    ServiceProviderService serviceProviderService;

    @GetMapping("")
    public List<ServiceProvider> list() {
        return serviceProviderService.list();
    }

    @PostMapping("")
    public ResponseEntity<ServiceProvider> add (@RequestBody ServiceProvider serviceProvider) {
        try {
            ServiceProvider _provider = serviceProviderService.save(serviceProvider);
            return new ResponseEntity<ServiceProvider>(_provider, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ServiceProvider>(HttpStatus.NOT_FOUND);

        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceProvider> update (@RequestBody ServiceProvider serviceProvider, @PathVariable Integer id) {
        try {
            ServiceProvider existingServiceProvider = serviceProviderService.get(id);
            serviceProvider.setId(id);
            serviceProvider.setAppointments(existingServiceProvider.getAppointments());
            serviceProviderService.save(serviceProvider);
            return new ResponseEntity<ServiceProvider>(serviceProvider, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Integer id) {
        try {
            serviceProviderService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
