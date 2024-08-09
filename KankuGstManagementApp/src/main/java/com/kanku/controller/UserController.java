package com.kanku.controller;

import com.kanku.enums.UserRole;
import com.kanku.model.MyUser;
import com.kanku.service.IUserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private IUserService service;

    @PostConstruct
    public void createAdmin() {
        MyUser myUser = new MyUser();
        myUser.setUserId(1);
        myUser.setFullName("Admin");
        myUser.setUsername("admin@gmail.com");
        myUser.setUserContact("9421100477");
        myUser.setPassword(new BCryptPasswordEncoder().encode("123"));
        myUser.setRole(UserRole.ADMIN);
        myUser.setAddress("139, Ward Surya Plaza ,Opp Kasaba gate police station, Mahadwar road,Kolhapur-416002");
        myUser.setUserGstNumber("27AALPRO247B1ZW");
        myUser.setBankName("XYZ bank");
        myUser.setBankAccNo("000000000000000");
        myUser.setBankBranch("Abc");
        myUser.setBankIFSCCode("BANK@XYZ123");
        service.saveUser(myUser);
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody MyUser myUser) {
        myUser.setRole(UserRole.NORMAL);
        return ResponseEntity.ok(service.saveUser(myUser));
    }

    @GetMapping("/getUser/{uid}")
    public ResponseEntity<?> getUser(@PathVariable("uid") Integer uid) {

        MyUser myUser = service.fetchUserById(uid);
        return new ResponseEntity<MyUser>(myUser, HttpStatus.OK);

    }

    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody MyUser myUser){
        return ResponseEntity.ok(service.loginUser(myUser));
    }
}
