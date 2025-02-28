package com.project.catalogue.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.project.catalogue.model.Users;
import com.project.catalogue.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepo.findByEmailOrPhone(username, username);
        if (user.isPresent()) {

            var userDetails = user.get();

            return User.builder()
                    .username(userDetails.getName())
                    .password(userDetails.getPassword())
                    .roles("USER")
                    .build();
        }
        else
        {
           throw new UsernameNotFoundException("User not found"); 
        }
    }

}
