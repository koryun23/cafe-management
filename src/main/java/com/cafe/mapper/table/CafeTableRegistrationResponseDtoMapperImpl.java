package com.cafe.mapper.table;

import com.cafe.dto.CafeTableRegistrationResponseDto;
import com.cafe.entity.table.CafeTable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CafeTableRegistrationResponseDtoMapperImpl implements CafeTableRegistrationResponseDtoMapper{
    @Override
    public CafeTableRegistrationResponseDto apply(CafeTable cafeTable) {
        return new CafeTableRegistrationResponseDto(
                cafeTable.getCafeTableStatusType(),
                cafeTable.getNumberOfSeats(),
                cafeTable.getCode(),
                LocalDateTime.now()
        );
    }
}
