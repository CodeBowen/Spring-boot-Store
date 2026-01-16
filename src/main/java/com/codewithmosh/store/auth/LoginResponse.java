package com.codewithmosh.store.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {
    private Jwt accessToken;
    private Jwt refreshToken;


}
