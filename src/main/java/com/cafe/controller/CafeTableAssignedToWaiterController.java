package com.cafe.controller;

import com.cafe.dto.request.CafeTableAssignmentRequestDto;
import com.cafe.dto.response.CafeTableAssignmentResponseDto;
import com.cafe.dto.request.CafeTablesAssignedToWaiterRetrievalRequestDto;
import com.cafe.dto.response.CafeTablesAssignedToWaiterRetrievalResponseDto;
import com.cafe.facade.core.table.CafeTableFacade;
import com.cafe.handler.BasicAuthorizationHttpServletRequestHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "tables-to-waiters", produces = "application/json", consumes = "application/json")
public class CafeTableAssignedToWaiterController {

    private final CafeTableFacade cafeTableFacade;
    private final BasicAuthorizationHttpServletRequestHandler basicAuthorizationHttpServletRequestHandler;

    public CafeTableAssignedToWaiterController(CafeTableFacade cafeTableFacade,
                                               BasicAuthorizationHttpServletRequestHandler basicAuthorizationHttpServletRequestHandler) {
        this.cafeTableFacade = cafeTableFacade;
        this.basicAuthorizationHttpServletRequestHandler = basicAuthorizationHttpServletRequestHandler;
    }

    @PostMapping(path = "/assign")
    public ResponseEntity<CafeTableAssignmentResponseDto> assignCafeTableToWaiter(@RequestBody CafeTableAssignmentRequestDto requestDto) {
        CafeTableAssignmentResponseDto responseDto = cafeTableFacade.assignTableToWaiter(requestDto);
        return ResponseEntity
                .status(responseDto.getHttpStatus())
                .body(responseDto);
    }

    @GetMapping
    public ResponseEntity<CafeTablesAssignedToWaiterRetrievalResponseDto> retrieveCafeTablesAssignedToWaiter(HttpServletRequest request,
                                                                                                             @RequestBody CafeTablesAssignedToWaiterRetrievalRequestDto dto) {
        String username = basicAuthorizationHttpServletRequestHandler.getUsernameAndPassword(request).getUsername();
        dto.setWaiterUsername(username);
        CafeTablesAssignedToWaiterRetrievalResponseDto responseDto = cafeTableFacade.retrieveCafeTableList(dto);
        return ResponseEntity.status(responseDto.getHttpStatus()).body(responseDto);
    }
}
