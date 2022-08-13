package com.appoint.controller;

import com.appoint.model.Appointments;
import com.appoint.repository.AppointmentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appoint")
public class AppointmentsController {
    @Autowired
    AppointmentRepository appointmentRepository;

    @PostMapping("")
    public ResponseEntity<Appointments> add(@RequestBody Appointments appointment) {
        try{
            Appointments newAppointments = appointmentRepository.save(appointment);
            return new ResponseEntity<Appointments>(newAppointments, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<Appointments>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @GetMapping("")
    public List<Appointments> list(){
        return appointmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointments> get(@PathVariable Integer id){
        try {
            Appointments appointment = appointmentRepository.findById(id).get();
            return new ResponseEntity<Appointments>(appointment, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Appointments>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/day/{_date}/{id}")
    public ResponseEntity<Integer> getCountByDate(@PathVariable String _date, @PathVariable Integer id){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = formatter.parse(_date);
            Set<Appointments> appointment = appointmentRepository.findAllByDay(date).stream().filter(app -> app.getServiceProvider().getBusiness().getId() == id).collect(Collectors.toSet());
            return new ResponseEntity<>(appointment.size(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/month/{_date}/{id}")
    public ResponseEntity<Integer> getCountByMonthAndYear(@PathVariable String _date, @PathVariable Integer id){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = formatter.parse(_date);
            List<Appointments> appointment = appointmentRepository.findAllByMonthAndYear(date.getYear() + 1900, date.getMonth() + 1, id);
            return new ResponseEntity<>(appointment.size(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/services/{_date}/{id}")
    public ResponseEntity<ObjectNode> getCountByServicesAndMonth(@PathVariable String _date, @PathVariable Integer id){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = formatter.parse(_date);
            List<Appointments> appointment = appointmentRepository.findAllByMonthAndYear(date.getYear()+1900, date.getMonth()+1, id);
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode servicesCount = mapper.createObjectNode();
            for(Appointments a : appointment){
                int count = servicesCount.get(a.getService().getName()) != null ? servicesCount.get(a.getService().getName()).asInt() : 0;
                servicesCount.put(a.getService().getName(), count+1);
            }
            return new ResponseEntity<>(servicesCount, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/serviceProviders/{_date}/{id}")
    public ResponseEntity<ObjectNode> getCountByServiceProviders(@PathVariable String _date, @PathVariable Integer id){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = formatter.parse(_date);
            List<Appointments> appointment = appointmentRepository.findAllByMonthAndYear(date.getYear()+1900, date.getMonth()+1, id);
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode serviceProvidersCount = mapper.createObjectNode();
            for(Appointments a : appointment){
                String providerName = a.getServiceProvider().getFirstname() + " " + a.getServiceProvider().getLastname();
                JsonNode node = serviceProvidersCount.get(providerName);
                int count = 0;
                if(node != null) count = node.asInt();
                serviceProvidersCount.put(providerName, count+1);
            }
            return new ResponseEntity<>(serviceProvidersCount, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Appointments appointment, @PathVariable Integer id) {
        try {
            Appointments existAppointments = appointmentRepository.findById(id).get();
            appointment.setId(id);
            appointmentRepository.save(appointment);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
            appointmentRepository.deleteById(id);

    }
}
