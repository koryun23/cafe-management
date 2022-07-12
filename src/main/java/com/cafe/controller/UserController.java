package com.cafe.controller;

import com.cafe.dto.response.UserListRetrievalResponseDto;
import com.cafe.dto.request.UserRegistrationRequestDto;
import com.cafe.dto.response.UserRegistrationResponseDto;
import com.cafe.facade.core.user.UserFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "users", produces = "application/json", consumes = "application/json")
public class UserController { // reviewed

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<UserRegistrationResponseDto> register(@RequestBody UserRegistrationRequestDto requestDto) {
        UserRegistrationResponseDto registrationResponseDto = userFacade.registerUser(requestDto);
        return ResponseEntity
                .status(registrationResponseDto.getHttpStatus())
                .body(registrationResponseDto);
    }

    @GetMapping
    public ResponseEntity<UserListRetrievalResponseDto> fetchAll() {
        UserListRetrievalResponseDto userFetchResponseDto = userFacade.getAll();
        return ResponseEntity
                .status(userFetchResponseDto.getHttpStatus())
                .body(userFetchResponseDto);
    }
}
