package com.cafe.service.impl.table;

import com.cafe.entity.table.CafeTable;
import com.cafe.entity.table.CafeTableStatusType;
import com.cafe.repository.CafeTableRepository;
import com.cafe.service.core.table.CafeTableCreationParams;
import com.cafe.service.core.table.CafeTableService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CafeTableServiceImplTest {

    private CafeTableService testSubject;

    @Mock
    private CafeTableRepository cafeTableRepository;

    @BeforeEach
    public void init() {
        testSubject = new CafeTableServiceImpl(cafeTableRepository);
    }

    @Test
    public void testFindByIdWhenCafeTableIsNotFound() {
        Mockito.when(cafeTableRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.empty());
        Mockito.verify(cafeTableRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(cafeTableRepository);
    }

    @Test
    public void testFindByIdWhenCafeTableIsFound() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        Mockito.when(cafeTableRepository.findById(1L)).thenReturn(Optional.of(cafeTable));
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(cafeTable));
        Mockito.verify(cafeTableRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(cafeTableRepository);
    }

    @Test
    public void testGetByIdWhenCafeTableIsNotFound() {
        Mockito.when(cafeTableRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.getById(1L)).isExactlyInstanceOf(CafeTableNotFoundException.class);
        Mockito.verify(cafeTableRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(cafeTableRepository);
    }

    @Test
    public void testGetByIdWhenCafeTableIsFound() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        Mockito.when(cafeTableRepository.findById(1L)).thenReturn(Optional.of(cafeTable));
        Assertions.assertThat(testSubject.getById(1L)).isEqualTo(cafeTable);
        Mockito.verify(cafeTableRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(cafeTableRepository);
    }

    @Test
    public void testFindByCodeWhenCafeTableIsNotFound() {
        Mockito.when(cafeTableRepository.findByCode("qwerty")).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.findByCode("qwerty")).isEqualTo(Optional.empty());
        Mockito.verify(cafeTableRepository).findByCode("qwerty");
        Mockito.verifyNoMoreInteractions(cafeTableRepository);
    }

    @Test
    public void testFindByCodeWhenCafeTableIsFound() {
        CafeTable table = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        Mockito.when(cafeTableRepository.findByCode("qwerty")).thenReturn(Optional.of(table));
        Assertions.assertThat(testSubject.findByCode("qwerty")).isEqualTo(Optional.of(table));
        Mockito.verify(cafeTableRepository).findByCode("qwerty");
        Mockito.verifyNoMoreInteractions(cafeTableRepository);
    }

    @Test
    public void testExistsByCodeWhenTableDoesNotExists() {
        Mockito.when(cafeTableRepository.existsByCode("qwerty")).thenReturn(false);
        Assertions.assertThat(testSubject.existsByCode("qwerty")).isEqualTo(false);
        Mockito.verify(cafeTableRepository).existsByCode("qwerty");
        Mockito.verifyNoMoreInteractions(cafeTableRepository);
    }

    @Test
    public void testExistsByCodeWhenTableExists() {
        Mockito.when(cafeTableRepository.existsByCode("qwerty")).thenReturn(true);
        Assertions.assertThat(testSubject.existsByCode("qwerty")).isEqualTo(true);
        Mockito.verify(cafeTableRepository).existsByCode("qwerty");
        Mockito.verifyNoMoreInteractions(cafeTableRepository);
    }

    @Test
    public void testCreate() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");

        CafeTable savedCafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        savedCafeTable.setId(1L);

        Mockito.when(cafeTableRepository.save(cafeTable)).thenReturn(savedCafeTable);

        Assertions.assertThat(testSubject.create(new CafeTableCreationParams(CafeTableStatusType.FREE, 5, "qwerty")))
                .isEqualTo(savedCafeTable);

        Mockito.verify(cafeTableRepository).save(cafeTable);
        Mockito.verifyNoMoreInteractions(cafeTableRepository);
    }

    @Test
    public void testMarkAs() {
        CafeTable cafeTable = new CafeTable(CafeTableStatusType.FREE, 5, "qwerty");
        cafeTable.setId(1L);

        CafeTable updatedCafeTable = new CafeTable(CafeTableStatusType.TAKEN, 5, "qwerty");
        updatedCafeTable.setId(1L);

        Mockito.when(cafeTableRepository.findById(1L)).thenReturn(Optional.of(cafeTable));
        Mockito.when(cafeTableRepository.save(updatedCafeTable)).thenReturn(updatedCafeTable);

        Assertions.assertThat(testSubject.markAs(1L, CafeTableStatusType.TAKEN)).isEqualTo(updatedCafeTable);

        Mockito.verify(cafeTableRepository).save(updatedCafeTable);
        Mockito.verify(cafeTableRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(cafeTableRepository);
    }

    @Test
    public void testCreateWhenParamsObjectIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.create(null)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetByIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getById(null)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testMarkAsWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.markAs(null, CafeTableStatusType.FREE)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testMarkAsWhenStatusIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.markAs(1L, null)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findById(null)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByCodeWhenCodeIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findByCode(null)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testExistsByCodeWhenCodeIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.existsByCode(null)).isExactlyInstanceOf(IllegalArgumentException.class);
    }
}