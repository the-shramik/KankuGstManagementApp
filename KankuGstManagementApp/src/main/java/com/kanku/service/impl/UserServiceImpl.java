package com.kanku.service.impl;

import com.kanku.dao.IUserRepo;
import com.kanku.model.MyUser;
import com.kanku.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepo userRepo;


    @Override
    public MyUser saveUser(MyUser myUser) {


        String userEmail=myUser.getUsername();
        String userContact=myUser.getUserContact();

        if(userRepo.getUserByUsernameAndUserContact(userEmail,userContact)==null){
            userRepo.save(myUser);
        }
        return null;
    }

    @Override
    public MyUser fetchUserById(Integer id) {
        return userRepo.findById(id).get();
    }

    @Override
    public MyUser isUserPreset(String email, String contact) {
        return userRepo.getUserByUsernameAndUserContact(email,contact);
    }

    @Override
    public MyUser loginUser(MyUser myUser) {
        return userRepo.getUserByUsernameAndPassword(myUser.getUsername(),myUser.getPassword());
    }
}
