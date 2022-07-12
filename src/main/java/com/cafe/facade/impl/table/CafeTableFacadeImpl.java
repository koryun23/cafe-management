package com.cafe.facade.impl.table;

import com.cafe.dto.request.CafeTableAssignmentRequestDto;
import com.cafe.dto.request.CafeTableRegistrationRequestDto;
import com.cafe.dto.request.CafeTablesAssignedToWaiterRetrievalRequestDto;
import com.cafe.dto.response.CafeTableAssignmentResponseDto;
import com.cafe.dto.response.CafeTableRegistrationResponseDto;
import com.cafe.dto.response.CafeTablesAssignedToWaiterRetrievalResponseDto;
import com.cafe.dto.response.error.ErrorCafeTableAssignmentResponseDto;
import com.cafe.dto.response.error.ErrorCafeTableRegistrationResponseDto;
import com.cafe.entity.table.CafeTable;
import com.cafe.entity.table.CafeTableAssignedToWaiter;
import com.cafe.entity.table.CafeTableStatusType;
import com.cafe.entity.user.User;
import com.cafe.entity.user.UserRoleType;
import com.cafe.facade.core.table.CafeTableFacade;
import com.cafe.mapper.table.CafeTableAssignedToWaiterCreationParamsMapper;
import com.cafe.mapper.table.CafeTableAssignmentResponseDtoMapper;
import com.cafe.mapper.table.CafeTableCreationParamsMapper;
import com.cafe.mapper.table.CafeTableRegistrationResponseDtoMapper;
import com.cafe.service.core.table.CafeTableAssignedToWaiterService;
import com.cafe.service.core.table.CafeTableService;
import com.cafe.service.core.user.UserRoleService;
import com.cafe.service.core.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
public class CafeTableFacadeImpl implements CafeTableFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(CafeTableFacadeImpl.class);
    private final CafeTableService cafeTableService;
    private final UserService userService;
    private final CafeTableAssignedToWaiterService cafeTableAssignedToWaiterService;
    private final UserRoleService userRoleService;
    private final CafeTableCreationParamsMapper cafeTableCreationParamsMapper;
    private final CafeTableRegistrationResponseDtoMapper cafeTableRegistrationResponseDtoMapper;
    private final CafeTableAssignedToWaiterCreationParamsMapper cafeTableAssignedToWaiterCreationParamsMapper;
    private final CafeTableAssignmentResponseDtoMapper cafeTableAssignmentResponseDtoMapper;

    public CafeTableFacadeImpl(CafeTableService cafeTableService,
                               UserService userService,
                               CafeTableAssignedToWaiterService cafeTableAssignedToWaiterService,
                               UserRoleService userRoleService,
                               CafeTableCreationParamsMapper cafeTableCreationParamsMapper,
                               CafeTableRegistrationResponseDtoMapper cafeTableRegistrationResponseDtoMapper,
                               CafeTableAssignedToWaiterCreationParamsMapper cafeTableAssignedToWaiterCreationParamsMapper,
                               CafeTableAssignmentResponseDtoMapper cafeTableAssignmentResponseDtoMapper) {
        Assert.notNull(cafeTableService, "Cafe table service should not be null");
        Assert.notNull(userService, "User service should not be null");
        Assert.notNull(cafeTableAssignedToWaiterService, "Cafe table assigned to waiter service should not be null");
        Assert.notNull(userRoleService, "userRoleService should not be null");
        Assert.notNull(cafeTableCreationParamsMapper, "cafe table creation params mapper should not be null");
        Assert.notNull(cafeTableAssignedToWaiterCreationParamsMapper, "cafe table assigned to waiter creation params mapper should not be null");
        Assert.notNull(cafeTableAssignmentResponseDtoMapper, "cafe table assignment response dto mapper");
        this.cafeTableService = cafeTableService;
        this.userService = userService;
        this.cafeTableAssignedToWaiterService = cafeTableAssignedToWaiterService;
        this.userRoleService = userRoleService;
        this.cafeTableCreationParamsMapper = cafeTableCreationParamsMapper;
        this.cafeTableRegistrationResponseDtoMapper = cafeTableRegistrationResponseDtoMapper;
        this.cafeTableAssignedToWaiterCreationParamsMapper = cafeTableAssignedToWaiterCreationParamsMapper;
        this.cafeTableAssignmentResponseDtoMapper = cafeTableAssignmentResponseDtoMapper;
    }

    @Override
    public CafeTableRegistrationResponseDto register(CafeTableRegistrationRequestDto dto) {
        Assert.notNull(dto, "Cafe table registration response dto should not be null");
        LOGGER.info("Creating a new cafe table according to the cafe table registration request dto - {}", dto);
        if(cafeTableService.existsByCode(dto.getCode())) {
            return new ErrorCafeTableRegistrationResponseDto(
                    List.of(String.format("Cannot register a cafe table with a code of %s because that code is already taken", dto.getCode())),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        CafeTable cafeTable = cafeTableService.create(cafeTableCreationParamsMapper.apply(dto));
        CafeTableRegistrationResponseDto responseDto = cafeTableRegistrationResponseDtoMapper.apply(cafeTable);
        LOGGER.info("Successfully created a new cafe table according to the cafe table registration request - {}, response - {}", dto, responseDto);
        return responseDto;
    }

    @Override
    public CafeTableAssignmentResponseDto assignTableToWaiter(CafeTableAssignmentRequestDto dto) {
        Assert.notNull(dto, "Cafe table assignment request dto should not be null");
        LOGGER.info("Assigning a table to a waiter according to the cafe table assignment request dto - {}", dto);
        Optional<User> userOptional = userService.findByUsername(dto.getWaiterUsername());
        if (userOptional.isEmpty()) {
            return new ErrorCafeTableAssignmentResponseDto(
                    List.of(String.format("No user found with a username of %s", dto.getWaiterUsername())),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        User user = userOptional.get();
        if (userRoleService.getRoleType(user.getUsername()) != UserRoleType.WAITER) {
            return new ErrorCafeTableAssignmentResponseDto(
                    List.of("Cannot assign a cafe table to a user who is not a waiter"),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        Optional<CafeTable> cafeTableOptional = cafeTableService.findById(dto.getCafeTableId());
        if (cafeTableOptional.isEmpty()) {
            return new ErrorCafeTableAssignmentResponseDto(
                    List.of(String.format("No table found with an id of %d", dto.getCafeTableId())),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        CafeTable cafeTable = cafeTableOptional.get();
        CafeTableStatusType cafeTableStatusType = cafeTable.getCafeTableStatusType();
        if(cafeTableStatusType != CafeTableStatusType.FREE) {
            return new ErrorCafeTableAssignmentResponseDto(
                    List.of(String.format("The table having an id of %d is not free, its status is %s", dto.getCafeTableId(), cafeTableStatusType)),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }

        if(cafeTableAssignedToWaiterService.findByCafeTableId(dto.getCafeTableId()).isPresent()) {
            return new ErrorCafeTableAssignmentResponseDto(
                    List.of(String.format("The table having an id of %d is already assigned to another waiter", dto.getCafeTableId())),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = cafeTableAssignedToWaiterService.create(cafeTableAssignedToWaiterCreationParamsMapper.apply(dto));
        CafeTableAssignmentResponseDto responseDto = cafeTableAssignmentResponseDtoMapper.apply(cafeTableAssignedToWaiter);
        LOGGER.info("Successfully assigned a table with an id of {} to a waiter with a username of {}, response - {}",
                dto.getCafeTableId(), dto.getWaiterUsername(), responseDto);
        return responseDto;
    }

    @Override
    public CafeTablesAssignedToWaiterRetrievalResponseDto retrieveCafeTableList(CafeTablesAssignedToWaiterRetrievalRequestDto dto) {
        Assert.notNull(dto, "Cafe table list retrieval request dto should not be null");
        LOGGER.info("Retrieving a list of cafe tables according to the cafe table retrieval request dto - {}", dto);
        List<CafeTableAssignedToWaiter> allByWaiterUsername = cafeTableAssignedToWaiterService.findAllByWaiterUsername(dto.getWaiterUsername());
        CafeTablesAssignedToWaiterRetrievalResponseDto responseDto = new CafeTablesAssignedToWaiterRetrievalResponseDto(allByWaiterUsername, HttpStatus.OK);
        LOGGER.info("Successfully retrieved a list of cafe tables according to the cafe table retrieval request dto - {}, result - {}", dto, responseDto);
        return responseDto;
    }
}
