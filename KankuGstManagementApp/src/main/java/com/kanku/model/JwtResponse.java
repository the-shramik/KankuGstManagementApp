package com.kanku.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private Integer userId;
    private String username;
    private String jwtToken;
    private String role;
}
