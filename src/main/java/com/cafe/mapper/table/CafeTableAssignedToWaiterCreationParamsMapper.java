package com.cafe.mapper.table;

import com.cafe.dto.request.CafeTableAssignmentRequestDto;
import com.cafe.service.core.table.CafeTableAssignedToWaiterCreationParams;

import java.util.function.Function;

public interface CafeTableAssignedToWaiterCreationParamsMapper extends Function<CafeTableAssignmentRequestDto, CafeTableAssignedToWaiterCreationParams> {

    CafeTableAssignedToWaiterCreationParams apply(CafeTableAssignmentRequestDto dto);
}
