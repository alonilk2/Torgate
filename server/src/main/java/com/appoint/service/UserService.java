package com.appoint.service;

import com.appoint.CurrentUser;
import com.appoint.exception.ResourceNotFoundException;
import com.appoint.model.User;
import com.appoint.repository.UserRepository;
import com.appoint.security.oauth2.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User get(Long id){
        try {
            return userRepository.findById(id).get();
        }catch (NoSuchElementException e){
            return null;
        }
    }

    public User findByEmail(String email){
        try {
            return userRepository.findByEmail(email).get();
        } catch (NoSuchElementException e){
            return null;
        }
    }

    public void save(User user){
        userRepository.save(user);
    }

    public User currentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId()).orElseThrow(()->new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
