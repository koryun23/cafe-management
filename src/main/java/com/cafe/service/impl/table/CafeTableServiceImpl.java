package com.cafe.service.impl.table;

import com.cafe.entity.table.CafeTable;
import com.cafe.entity.table.CafeTableStatusType;
import com.cafe.repository.CafeTableRepository;
import com.cafe.service.core.table.CafeTableCreationParams;
import com.cafe.service.core.table.CafeTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class CafeTableServiceImpl implements CafeTableService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CafeTableServiceImpl.class);
    private final CafeTableRepository cafeTableRepository;

    public CafeTableServiceImpl(CafeTableRepository cafeTableRepository) {
        Assert.notNull(cafeTableRepository, "cafe table repository should not be null");
        this.cafeTableRepository = cafeTableRepository;
    }

    @Transactional
    @Override
    public CafeTable create(CafeTableCreationParams params) {
        Assert.notNull(params, "Cafe table creation params object should not be null");
        LOGGER.info("Creating a new cafe table according to the cafe table creation params - {}", params);
        CafeTable cafeTable = cafeTableRepository.save(new CafeTable(
                params.getCafeTableStatusType(),
                params.getNumberOfSeats(),
                params.getCode())
        );
        LOGGER.info("Successfully created a new cafe table according to the cafe table creation params - {}, result - {}", params, cafeTable);
        return cafeTable;
    } // tested

    @Transactional(readOnly = true)
    @Override
    public CafeTable getById(Long id) {
        Assert.notNull(id, "Cafe table id should not be null");
        LOGGER.info("Retrieving a cafe table having an id of {}", id);
        CafeTable cafeTable = cafeTableRepository.findById(id).orElseThrow(() -> new CafeTableNotFoundException(id));
        LOGGER.info("Successfully retrieved a cafe table having an id of {}, result - {}", id, cafeTable);
        return cafeTable;
    } // tested

    @Transactional(readOnly = true)
    @Override
    public Optional<CafeTable> findById(Long id) {
        Assert.notNull(id, "Cafe table id should not be null");
        LOGGER.info("Retrieving an optional of a cafe table with an id of {}", id);
        Optional<CafeTable> cafeTableOptional = cafeTableRepository.findById(id);
        LOGGER.info("Successfully retrieved an optional of a cafe table with an id of {}, result - {}", id, cafeTableOptional);
        return cafeTableOptional;
    } // tested

    @Transactional
    @Override
    public CafeTable markAs(Long id, CafeTableStatusType status) {
        Assert.notNull(id, "Cafe table id should not be null");
        Assert.notNull(status, "Cafe table status type should not be null");
        LOGGER.info("Changing the status of a table having an id of {} to {}", id, status);
        CafeTable cafeTable = cafeTableRepository.findById(id).orElseThrow(() -> new CafeTableNotFoundException(id));
        CafeTable newCafeTable = new CafeTable(
                status,
                cafeTable.getNumberOfSeats(),
                cafeTable.getCode()
        );
        newCafeTable.setId(cafeTable.getId());
        CafeTable savedCafeTable = cafeTableRepository.save(newCafeTable);
        LOGGER.info("Successfully changed the status of a table having an id of {} to {}, result - {}", id, status , savedCafeTable);
        return savedCafeTable;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CafeTable> findByCode(String code) {
        Assert.notNull(code, "Cafe table code should not be null");
        Assert.hasText(code, "Cafe table code should not be empty");
        LOGGER.info("Retrieving an optional of a cafe table having a code of {}", code);
        Optional<CafeTable> cafeTableOptional = cafeTableRepository.findByCode(code);
        LOGGER.info("Successfully retrieved an optional of a cafe table having a code of {}, result - {}", code, cafeTableOptional);
        return cafeTableOptional;
    } // tested

    @Transactional(readOnly = true)
    @Override
    public boolean existsByCode(String code) {
        Assert.notNull(code, "Cafe table code should not be null");
        Assert.notNull(code, "Cafe table code should not be empty");
        LOGGER.info("Checking if a cafe table having a code of {} exists or not", code);
        boolean result = cafeTableRepository.existsByCode(code);
        LOGGER.info("Checking if a cafe table having a code of {} exists or not, result - {}", code, result);
        return result;
    } // tested
}
