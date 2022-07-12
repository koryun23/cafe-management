package com.cafe.mapper.table;

import com.cafe.dto.response.CafeTableAssignmentResponseDto;
import com.cafe.entity.table.CafeTableAssignedToWaiter;

import java.util.function.Function;

public interface CafeTableAssignmentResponseDtoMapper extends Function<CafeTableAssignedToWaiter, CafeTableAssignmentResponseDto> {
    CafeTableAssignmentResponseDto apply(CafeTableAssignedToWaiter cafeTableAssignedToWaiter);
}
