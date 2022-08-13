package com.appoint.controller;

import com.appoint.model.WorkDays;
import com.appoint.model.WorkDays;
import com.appoint.repository.WorkdaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/workdays")
public class WorkDaysController {

    @Autowired
    WorkdaysRepository workdaysRepository;

    @GetMapping("")
    public List<WorkDays> list() {
        return workdaysRepository.findAll();
    }

    @PostMapping("")
    public ResponseEntity<?> add (@RequestBody WorkDays workDays) {
        WorkDays savedWorkDays = workdaysRepository.save(workDays);
        return new ResponseEntity<>(savedWorkDays.getId(), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update (@RequestBody WorkDays workDays, @PathVariable Integer id) {
        try {
            WorkDays existingWorkDays = workdaysRepository.findById(id).get();
            workDays.setId(id);
            workdaysRepository.save(workDays);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Integer id) {
        try {
            WorkDays workDays = workdaysRepository.findById(id).get();
            workdaysRepository.delete(workDays);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
