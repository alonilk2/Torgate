package com.appoint.controller;

import com.appoint.model.Business;
import com.appoint.model.ServiceProvider;
import com.appoint.repository.AppointmentRepository;
import com.appoint.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Autowired
    BusinessRepository businessRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping("")
    public List<Business> list() {
        return businessRepository.findAll();
    }

    @GetMapping("/{id}")
    public Business find(@PathVariable Integer id){
        return businessRepository.findById(id).get();
    }

    @GetMapping("/totalincome/{id}")
    public Integer findTotalIncome(@PathVariable Integer id){
        try{
            Business business =  businessRepository.findById(id).get();
            Set<ServiceProvider> serviceProviders = business.getServiceProviders();
            AtomicReference<Integer> total = new AtomicReference<>(0);
            serviceProviders.forEach(serviceProvider -> appointmentRepository.findAllByServiceProvider(serviceProvider).forEach(app -> {
                Date appDate = app.getDay();
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                int month = Integer.parseInt(monthFormat.format(appDate));
                Date today = new Date();
                LocalDate todayLocalDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if(todayLocalDate.getMonthValue() == month){
                    total.updateAndGet(v -> v + app.getService().getCost());
                }
            }));
            return total.get();
        } catch (NoSuchElementException e){
            return null;
        }
    }

    @PostMapping("")
    public void add (@RequestBody Business business) {
        businessRepository.save(business);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update (@RequestBody Business business, @PathVariable Integer id) {
        try {
            Business existingBusiness = businessRepository.findById(id).get();
            if(business.getName() != null) existingBusiness.setName(business.getName());
            if(business.getAboutus() != null) existingBusiness.setAboutus(business.getAboutus());
            if(business.getEmail() != null) existingBusiness.setEmail(business.getEmail());
            if(business.getAddress() != null) existingBusiness.setAddress(business.getAddress());
            if(business.getGallery() != null) existingBusiness.setGallery(business.getGallery());
            if(business.getHeaderImg() != null) existingBusiness.setHeaderImg(business.getHeaderImg());
            if(business.getPageColor() != null) existingBusiness.setPageColor(business.getPageColor());
            if(business.getPhone1() != null) existingBusiness.setPhone1(business.getPhone1());
            if(business.getPhone2() != null) existingBusiness.setPhone2(business.getPhone2());
            if(business.getWebsite() != null) existingBusiness.setWebsite(business.getWebsite());
            if(business.getWorkDays() != null) existingBusiness.setWorkDays(business.getWorkDays());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Integer id) {
        try {
            Business existingBusiness = businessRepository.findById(id).get();
            businessRepository.delete(existingBusiness);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
