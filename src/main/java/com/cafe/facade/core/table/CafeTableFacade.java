package com.cafe.facade.core.table;

import com.cafe.dto.request.CafeTableAssignmentRequestDto;
import com.cafe.dto.request.CafeTableRegistrationRequestDto;
import com.cafe.dto.request.CafeTablesAssignedToWaiterRetrievalRequestDto;
import com.cafe.dto.response.CafeTableAssignmentResponseDto;
import com.cafe.dto.response.CafeTableRegistrationResponseDto;
import com.cafe.dto.response.CafeTablesAssignedToWaiterRetrievalResponseDto;

public interface CafeTableFacade {
    CafeTableRegistrationResponseDto register(CafeTableRegistrationRequestDto dto);

    CafeTableAssignmentResponseDto assignTableToWaiter(CafeTableAssignmentRequestDto dto);

    CafeTablesAssignedToWaiterRetrievalResponseDto retrieveCafeTableList(CafeTablesAssignedToWaiterRetrievalRequestDto dto);

}
