package com.kanku.service.impl.jwt;

import com.kanku.dao.IUserRepo;
import com.kanku.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements UserDetailsService {
    @Autowired
    private IUserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = repo.getMyUserByUsername(username);
        System.out.println(user);
        if(user.isEmpty()){
            System.out.println("MyUser Not Found!!");
            throw new UsernameNotFoundException("No MyUser Found!!");
        }
        return new User(user.get().getUsername(),user.get().getPassword(),user.get().getAuthorities());
    }
}
