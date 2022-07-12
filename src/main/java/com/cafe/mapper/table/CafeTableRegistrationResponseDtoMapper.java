package com.cafe.mapper.table;

import com.cafe.dto.response.CafeTableRegistrationResponseDto;
import com.cafe.entity.table.CafeTable;

import java.util.function.Function;

public interface CafeTableRegistrationResponseDtoMapper extends Function<CafeTable, CafeTableRegistrationResponseDto> {

    CafeTableRegistrationResponseDto apply(CafeTable cafeTable);
}
