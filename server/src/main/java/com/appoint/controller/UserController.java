package com.appoint.controller;

import com.appoint.CurrentUser;
import com.appoint.model.Business;
import com.appoint.model.ConfirmationToken;
import com.appoint.model.ServiceProvider;
import com.appoint.model.User;
import com.appoint.repository.BusinessRepository;
import com.appoint.repository.ConfirmationTokenRepository;
import com.appoint.security.oauth2.user.UserPrincipal;
import com.appoint.service.EmailSenderService;
import com.appoint.service.ServiceProviderService;
import com.appoint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ServiceProviderService serviceProviderService;
    @Autowired
    BusinessRepository businessRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("")
    public void save(@RequestBody User user){
        User existingUser = userService.findByEmail(user.getEmail());
        if(existingUser != null) {
            return;
        }
        userService.save(user);
    }
    @GetMapping("/{email}")
    public ResponseEntity<User> findUserByMail(@PathVariable String email) {
        try{
            User user = userService.findByEmail(email);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/me")
//    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
            User user = userService.currentUser(userPrincipal);
        return userService.currentUser(userPrincipal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try{
            User user = userService.get(id);
            ConfirmationToken conf = confirmationTokenRepository.findByUser(user);
            if(conf != null) confirmationTokenRepository.delete(conf);
            userService.delete(id);
            ServiceProvider serviceProvider = user.getServiceProvider();
            if(serviceProvider != null) serviceProviderService.delete(serviceProvider.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<User> update (@RequestBody User user, @PathVariable Long id) {
        try{
            User existingUser = userService.get(id);
            user.setId(id);
            if(user.getPassword() != null){
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                user.setPassword(existingUser.getPassword());
            }
            if(existingUser.getServiceProvider() != null){
                user.setServiceProvider(existingUser.getServiceProvider());
            }
            userService.save(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
