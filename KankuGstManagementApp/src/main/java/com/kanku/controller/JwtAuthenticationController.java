package com.kanku.controller;

import com.kanku.dao.IUserRepo;
import com.kanku.jwt.JwtUtils;
import com.kanku.model.JwtRequest;
import com.kanku.model.JwtResponse;
import com.kanku.model.MyUser;
import com.kanku.service.impl.jwt.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/service")
@CrossOrigin("*")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private IUserRepo userRepo;

    @PostMapping("/login")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws
            BadCredentialsException,
            DisabledException,
            UsernameNotFoundException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect Username or Password.");
        }
        final UserDetails userDetails = customerService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<MyUser> optionalCustomer = userRepo.getMyUserByUsername(userDetails.getUsername());
        final String jwt = jwtUtils.generateToken(userDetails.getUsername());
        JwtResponse authenticationResponse = new JwtResponse();
        if(optionalCustomer.isPresent()){
            authenticationResponse.setJwtToken(jwt);
            authenticationResponse.setRole(String.valueOf(optionalCustomer.get().getRole()));
            authenticationResponse.setUserId(optionalCustomer.get().getUserId());
            authenticationResponse.setUsername(optionalCustomer.get().getUsername());
        }
        return authenticationResponse;
    }

    // return the details of current logged user
    @GetMapping("/current-user")
    public UserDetails getCurrentUser(Principal principal){
        return customerService.loadUserByUsername(principal.getName());
//        return customerService.loadUserByUsername("admin@gmail.com");


    }
}
