package com.cafe.facade.core.table;

import com.cafe.dto.*;

public interface CafeTableFacade {
    CafeTableRegistrationResponseDto register(CafeTableRegistrationRequestDto dto);

    CafeTableAssignmentResponseDto assignTableToWaiter(CafeTableAssignmentRequestDto dto);

    CafeTablesAssignedToWaiterRetrievalResponseDto retrieveCafeTableList(CafeTablesAssignedToWaiterRetrievalRequestDto dto);

}
