package com.cafe.mapper.table;

import com.cafe.dto.request.CafeTableAssignmentRequestDto;
import com.cafe.service.core.table.CafeTableAssignedToWaiterCreationParams;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CafeTableAssignedToWaiterCreationParamsMapperImpl implements CafeTableAssignedToWaiterCreationParamsMapper{
    @Override
    public CafeTableAssignedToWaiterCreationParams apply(CafeTableAssignmentRequestDto dto) {
        return new CafeTableAssignedToWaiterCreationParams(
                dto.getCafeTableId(),
                dto.getWaiterUsername(),
                LocalDateTime.now()
        );
    }
}
