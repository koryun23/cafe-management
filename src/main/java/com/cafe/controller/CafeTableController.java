package com.cafe.controller;

import com.cafe.dto.request.CafeTableRegistrationRequestDto;
import com.cafe.dto.response.CafeTableRegistrationResponseDto;
import com.cafe.entity.table.CafeTableStatusType;
import com.cafe.facade.core.table.CafeTableFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "tables", consumes = "application/json", produces = "application/json")
public class CafeTableController {

    private final CafeTableFacade cafeTableFacade;

    public CafeTableController(CafeTableFacade cafeTableFacade) {
        this.cafeTableFacade = cafeTableFacade;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<CafeTableRegistrationResponseDto> register(@RequestBody CafeTableRegistrationRequestDto requestDto) {
        requestDto.setCafeTableStatusType(CafeTableStatusType.FREE);
        CafeTableRegistrationResponseDto responseDto = cafeTableFacade.register(requestDto);
        return ResponseEntity
                .status(responseDto.getHttpStatus())
                .body(responseDto);
    }
}
