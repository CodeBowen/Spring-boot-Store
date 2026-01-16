package com.codewithmosh.store.auth;

import com.codewithmosh.store.users.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final JwtConfig jwtConfig;
    private final UserMapper userMapper;
    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {
        var loginResponse = authService.authenticateUser(request);

        var cookie = new Cookie("refreshToken", loginResponse.getRefreshToken().toString());
        cookie.setHttpOnly(true);  // To make sure it can not be access by javascript
        cookie.setPath("api/auth/refresh");  // Where the cookie can be sent to
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpirationInSeconds());   // 7d
        cookie.setSecure(true);   // only be sent via https
        response.addCookie(cookie);

        return new JwtResponse(loginResponse.getAccessToken().toString());
    }

    @PostMapping("/refresh")
    public Jwt refresh(@CookieValue(value = "refreshToken") String refreshToken) {
        return authService.refreshAccessToken(refreshToken);
    }

//    @PostMapping("/validate")
//    public boolean validateToken(@RequestHeader ("Authorization") String authHeader) {
//        System.out.println("validate called");
//        var token = authHeader.replace("Bearer ", "");
//        return jwtService.validateToken(token);
//    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        var user = authService.getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var userDto = userMapper.userToUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
