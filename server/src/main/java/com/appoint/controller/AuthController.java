package com.appoint.controller;

import com.appoint.exception.BadRequestException;
import com.appoint.model.AuthProvider;
import com.appoint.model.Business;
import com.appoint.model.ConfirmationToken;
import com.appoint.model.User;

import com.appoint.payload.*;
import com.appoint.repository.ConfirmationTokenRepository;
import com.appoint.repository.UserRepository;
import com.appoint.security.oauth2.TokenProvider;
import com.appoint.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }
        // Creating user's account
        User user = new User();
        Business business = new Business();
        business.setName("Business Name");
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        user.setBusiness(business);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(user.getEmail());
//        mailMessage.setSubject("Complete Registration!");
//        mailMessage.setFrom("chand312902@gmail.com");
//        mailMessage.setText("To confirm your account, please click here : "
//                +"http://localhost:8082/confirm-account?token="+confirmationToken.getConfirmationToken());
//
//        emailSenderService.sendEmail(mailMessage);

        return new ResponseEntity<User>(result, HttpStatus.OK);
    }


    @PostMapping("/createProviderUser")
    public ResponseEntity<User> createprovideruser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        // Creating user's account
        User user = new User();
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(user.getEmail());
//        mailMessage.setSubject("Complete Registration!");
//        mailMessage.setFrom("chand312902@gmail.com");
//        mailMessage.setText("To confirm your account, please click here : "
//                +"http://localhost:8082/confirm-account?token="+confirmationToken.getConfirmationToken());
//
//        emailSenderService.sendEmail(mailMessage);

        return new ResponseEntity<User>(result, HttpStatus.OK);
    }


    @PutMapping("/signup")
    public ResponseEntity<?> update(@Valid @RequestBody SignUpRequest signUpRequest) {
        User exist;
        System.out.println("ABC");
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            exist = userRepository.findByEmail(signUpRequest.getEmail()).get();
            exist.setFirstname(signUpRequest.getFirstname());
            exist.setLastname(signUpRequest.getLastname());
            exist.setEmail(signUpRequest.getEmail());
            exist.setPassword(passwordEncoder.encode(exist.getPassword()));
            User result = userRepository.save(exist);

            return new ResponseEntity<User>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/confirmemail")
    public ResponseEntity<?> confirmEmail(@Valid @RequestBody ConfirmRequest confirmRequest) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmRequest.getToken());
        if (token != null) {
            User user = userRepository.findByEmail(token.getUser().getEmail()).get();
            user.setEmailVerified(true);
            userRepository.save(user);
            return ResponseEntity.ok(new ApiResponse(true, "User registered successfully@"));

        }
        return null;
    }

}