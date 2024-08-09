package com.kanku.service;

import com.kanku.model.MyUser;

public interface IUserService {

    MyUser saveUser(MyUser myUser);

    MyUser fetchUserById(Integer id);

    MyUser isUserPreset(String email, String contact);

    MyUser loginUser(MyUser myUser);
}
