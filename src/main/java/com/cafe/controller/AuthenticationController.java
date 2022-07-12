package com.cafe.controller;

import com.cafe.security.UsernamePasswordHolder;
import com.cafe.service.core.jwt.JwtService;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.Date;

@RestController
@RequestMapping(path = "login")
public class AuthenticationController {

    private JwtService jwtService;

    public AuthenticationController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody UsernamePasswordHolder usernamePasswordHolder, HttpServletResponse response) {
        System.out.println("AUTHENTICATING");
        String token = jwtService.createToken(usernamePasswordHolder.getUsername(), new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7));
        response.setHeader("token", token);
        return ResponseEntity.status(HttpStatus.FOUND).body(token);
    }
}
