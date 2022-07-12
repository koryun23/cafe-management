package com.cafe.mapper.table;

import com.cafe.dto.response.CafeTableAssignmentResponseDto;
import com.cafe.entity.table.CafeTableAssignedToWaiter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CafeTableAssignmentResponseDtoMapperImpl implements CafeTableAssignmentResponseDtoMapper {
    @Override
    public CafeTableAssignmentResponseDto apply(CafeTableAssignedToWaiter cafeTableAssignedToWaiter) {
        return new CafeTableAssignmentResponseDto(
                cafeTableAssignedToWaiter.getCafeTable().getId(),
                cafeTableAssignedToWaiter.getWaiter().getUsername(),
                cafeTableAssignedToWaiter.getAssignedAt(),
                HttpStatus.OK
        );
    }
}
