package com.kanku.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@RequiredArgsConstructor
public class Authority implements GrantedAuthority {
    private String authority;
    @Override
    public String getAuthority() {
        return this.authority;
    }
}
