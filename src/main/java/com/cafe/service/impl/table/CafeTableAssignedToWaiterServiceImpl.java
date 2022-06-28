package com.cafe.service.impl.table;

import com.cafe.entity.table.CafeTableAssignedToWaiter;
import com.cafe.repository.CafeTableAssignedToWaiterRepository;
import com.cafe.service.core.table.CafeTableAssignedToWaiterCreationParams;
import com.cafe.service.core.table.CafeTableAssignedToWaiterService;
import com.cafe.service.core.table.CafeTableService;
import com.cafe.service.core.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class CafeTableAssignedToWaiterServiceImpl implements CafeTableAssignedToWaiterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CafeTableAssignedToWaiterServiceImpl.class);
    private final CafeTableAssignedToWaiterRepository cafeTableAssignedToWaiterRepository;
    private final CafeTableService cafeTableService;
    private final UserService userService;

    public CafeTableAssignedToWaiterServiceImpl(CafeTableAssignedToWaiterRepository cafeTableAssignedToWaiterRepository,
                                                CafeTableService cafeTableService,
                                                UserService userService) {
        this.cafeTableAssignedToWaiterRepository = cafeTableAssignedToWaiterRepository;
        this.cafeTableService = cafeTableService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public CafeTableAssignedToWaiter create(CafeTableAssignedToWaiterCreationParams params) {
        Assert.notNull(params, "CafeTableAssignedToWaiter creation params should not be null");
        LOGGER.info("Assigning a table with an id of {} to waiter having an id of {}", params.getCafeTableId(), params.getWaiterId());
        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = cafeTableAssignedToWaiterRepository.save(new CafeTableAssignedToWaiter(
                cafeTableService.getById(params.getCafeTableId()),
                userService.getById(params.getWaiterId())
        ));
        LOGGER.info("Successfully assigned a table with an id of {} to waiter having an id of {}, result - {}", params.getCafeTableId(), params.getWaiterId(), cafeTableAssignedToWaiter);
        return cafeTableAssignedToWaiter;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CafeTableAssignedToWaiter> findById(Long id) {
        Assert.notNull(id, "Provided id should not be null");
        LOGGER.info("Retrieving an optional of a table assigned to user, provided id - {}", id);
        Optional<CafeTableAssignedToWaiter> optional = cafeTableAssignedToWaiterRepository.findById(id);
        LOGGER.info("Successfully retrieved an optional of a table assigned to user, provided id - {}, result - {}", id, optional);
        return optional;
    }

    @Transactional(readOnly = true)
    @Override
    public CafeTableAssignedToWaiter getById(Long id) {
        Assert.notNull(id, "Provided id should not be null");
        LOGGER.info("Retrieving a table assigned to user, provided id - {}", id);
        CafeTableAssignedToWaiter cafeTableAssignedToWaiter = cafeTableAssignedToWaiterRepository.findById(id).orElseThrow(() -> new CafeTableAssignedToWaiterException(id));
        LOGGER.info("Successfully retrieved a table assigned to user, provided id - {}, result - {}", id, cafeTableAssignedToWaiter);
        return cafeTableAssignedToWaiter;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CafeTableAssignedToWaiter> findAllByWaiterId(Long id) {
        Assert.notNull(id, "Waiter id should not be null");
        LOGGER.info("Retrieving all cafe tables assigned to the waiter having an id of {}", id);
        List<CafeTableAssignedToWaiter> allByWaiterId = cafeTableAssignedToWaiterRepository.findAllByWaiterId(id);
        LOGGER.info("Successfully retrieved all cafe tables assigned to the waiter having an id of {}, result - {}", id, allByWaiterId);
        return allByWaiterId;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CafeTableAssignedToWaiter> findByCafeTableId(Long id) {
        Assert.notNull(id, "Cafe table id should not be null");
        LOGGER.info("Fetching an optional of a cafe table assigned to waiter, id - {}", id);
        Optional<CafeTableAssignedToWaiter> cafeTableAssignedToWaiterOptional = cafeTableAssignedToWaiterRepository.findByCafeTableId(id);
        LOGGER.info("Successfully fetched an optional of a cafe table assigned to waiter, given id - {}, result - {}", id, cafeTableAssignedToWaiterOptional);
        return cafeTableAssignedToWaiterOptional;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByWaiterUsername(String username) {
        Assert.notNull(username, "Waiter username should not be null");
        LOGGER.info("Checking if a waiter having a username of '{}' exists or not", username);
        boolean result = cafeTableAssignedToWaiterRepository.existsByWaiterUsername(username);
        LOGGER.info("Checked if a waiter having a username of '{}' exists or not, result - {}", username, result);
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CafeTableAssignedToWaiter> findAllByWaiterUsername(String username) {
        Assert.notNull(username, "Waiter username should not be null");
        Assert.notNull(username, "Waiter username should not be empty");
        LOGGER.info("Retrieving a list of cafe tables assigned to waiter according to the waiter username - {}", username);
        List<CafeTableAssignedToWaiter> allByWaiterUsername = cafeTableAssignedToWaiterRepository.findAllByWaiterUsername(username);
        LOGGER.info("Successfully retrieved a list of cafe tables assigned to waiter according to the waiter username - {}, result - {}", username, allByWaiterUsername);
        return allByWaiterUsername;
    }

    @Transactional
    @Override
    public void deleteByCafeTableId(Long cafeTableId) {
        Assert.notNull(cafeTableId, "Cafe table id should not be null");
        LOGGER.info("Discharging the cafe table having an id of {} from being served", cafeTableId);
        cafeTableAssignedToWaiterRepository.deleteByCafeTableId(cafeTableId);
        LOGGER.info("Successfully discharged the cafe table having an id of {} from being served", cafeTableId);
    }
}
