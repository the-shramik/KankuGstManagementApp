package com.kanku.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanku.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@ToString
public class MyUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String fullName;
    @Column(unique = true)
    private String userContact;

    private UserRole role;
    private Boolean enabled;
    private String address;
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String userGstNumber;
    private String bankName;
    private String bankAccNo;
    private String bankBranch;
    private String bankIFSCCode;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> authority = new HashSet<>();
        Authority auth = new Authority();
        auth.setAuthority(role.toString());
        authority.add(auth);
        return authority;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }


    @Override
    public boolean isEnabled() {
        return false;
    }
}
