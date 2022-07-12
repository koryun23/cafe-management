package com.cafe.mapper.table;

import com.cafe.dto.request.CafeTableRegistrationRequestDto;
import com.cafe.service.core.table.CafeTableCreationParams;

import java.util.function.Function;

public interface CafeTableCreationParamsMapper extends Function<CafeTableRegistrationRequestDto, CafeTableCreationParams> {

    CafeTableCreationParams apply(CafeTableRegistrationRequestDto dto);
}
