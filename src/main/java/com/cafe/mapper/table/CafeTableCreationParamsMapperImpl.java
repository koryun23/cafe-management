package com.cafe.mapper.table;

import com.cafe.dto.request.CafeTableRegistrationRequestDto;
import com.cafe.service.core.table.CafeTableCreationParams;
import org.springframework.stereotype.Component;

@Component
public class CafeTableCreationParamsMapperImpl implements CafeTableCreationParamsMapper{

    @Override
    public CafeTableCreationParams apply(CafeTableRegistrationRequestDto dto) {
        return new CafeTableCreationParams(
                dto.getCafeTableStatusType(),
                dto.getNumberOfSeats(),
                dto.getCode()
        );
    }
}
