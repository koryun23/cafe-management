package com.cafe.service.impl.product;

import com.cafe.entity.product.Product;
import com.cafe.repository.ProductRepository;
import com.cafe.service.core.product.ProductCreationParams;
import com.cafe.service.core.product.ProductService;
import com.cafe.service.core.product.ProductUpdateParams;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private ProductService testSubject;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        testSubject = new ProductServiceImpl(productRepository);
    }

    @Test
    public void testFindByIdWhenProductDoesNotExist() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.empty());
        Mockito.verify(productRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testFindByIdWhenProductExists() {
        Product product = new Product("Pepsi", 4, 300, LocalDateTime.MAX);
        product.setId(1L);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(product));

        Mockito.verify(productRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testFindByNameWhenProductDoesNotExist() {
        Mockito.when(productRepository.findByName("Pepsi")).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.findByName("Pepsi")).isEqualTo(Optional.empty());
        Mockito.verify(productRepository).findByName("Pepsi");
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testFindByNameWhenProductExists() {
        Product product = new Product("Pepsi", 4, 300, LocalDateTime.MAX);
        product.setId(1L);

        Mockito.when(productRepository.findByName("Pepsi")).thenReturn(Optional.of(product));

        Assertions.assertThat(testSubject.findByName("Pepsi")).isEqualTo(Optional.of(product));

        Mockito.verify(productRepository).findByName("Pepsi");
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetAmountByProductNameWhenProductDoesNotExist() {
        Mockito.when(productRepository.findByName("Pepsi")).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.getAmountByProductName("Pepsi")).isExactlyInstanceOf(ProductNotFoundException.class);
        Mockito.verify(productRepository).findByName("Pepsi");
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetAmountByProductNameWhenProductExists() {
        Product product = new Product("Pepsi", 4, 300, LocalDateTime.MAX);
        product.setId(1L);

        Mockito.when(productRepository.findByName("Pepsi")).thenReturn(Optional.of(product));

        Assertions.assertThat(testSubject.getAmountByProductName("Pepsi")).isEqualTo(300);

        Mockito.verify(productRepository).findByName("Pepsi");
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetAmountByProductIdWhenProductDoesNotExist() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.getAmountByProductId(1L)).isExactlyInstanceOf(ProductNotFoundException.class);
        Mockito.verify(productRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetAmountByProductIdWhenProductExists() {
        Product product = new Product("Pepsi", 4, 300, LocalDateTime.MAX);
        product.setId(1L);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Assertions.assertThat(testSubject.getAmountByProductId(1L)).isEqualTo(300);

        Mockito.verify(productRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetByNameWhenProductDoesNotExist() {
        Mockito.when(productRepository.findByName("Pepsi")).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.getByName("Pepsi")).isExactlyInstanceOf(ProductNotFoundException.class);
        Mockito.verify(productRepository).findByName("Pepsi");
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetByNameWhenProductExists() {
        Product product = new Product("Pepsi", 4, 300, LocalDateTime.MAX);
        product.setId(1L);

        Mockito.when(productRepository.findByName("Pepsi")).thenReturn(Optional.of(product));

        Assertions.assertThat(testSubject.getByName("Pepsi")).isEqualTo(product);

        Mockito.verify(productRepository).findByName("Pepsi");
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetByIdWhenProductDoesNotExist() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.getById(1L)).isExactlyInstanceOf(ProductNotFoundException.class);
        Mockito.verify(productRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetByIdWhenProductExists() {
        Product product = new Product("Pepsi", 4, 300, LocalDateTime.MAX);
        product.setId(1L);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Assertions.assertThat(testSubject.getById(1L)).isEqualTo(product);

        Mockito.verify(productRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testCreate() {
        Product product = new Product("Pepsi", 4, 300, LocalDateTime.MAX);

        Product savedProduct = new Product("Pepsi", 4, 300, LocalDateTime.MAX);
        savedProduct.setId(1L);

        Mockito.when(productRepository.save(product)).thenReturn(savedProduct);

        Assertions.assertThat(testSubject.create(new ProductCreationParams(
                "Pepsi", 4, 300, LocalDateTime.MAX
        ))).isEqualTo(savedProduct);

        Mockito.verify(productRepository).save(product);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testUpdate() {
        Product product = new Product("Pepsi", 4, 300, LocalDateTime.MAX);
        product.setId(1L);

        Product updatedProduct = new Product("Pepsi", 4, 500, LocalDateTime.MAX);
        updatedProduct.setId(1L);

        Mockito.when(productRepository.findByName("Pepsi")).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        Assertions.assertThat(testSubject.updateProduct(
                new ProductUpdateParams("Pepsi", "Pepsi", 500, 4)
        )).isEqualTo(updatedProduct);

        Mockito.verify(productRepository).save(updatedProduct);
        Mockito.verify(productRepository).findByName("Pepsi");
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testCreateWhenParamsObjectIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.create(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetByIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getById(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findById(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testFindByNameWhenNameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.findByName(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetAmountByProductNameWhenNameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getAmountByProductName(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetAmountByProductIdWhenIdIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getAmountByProductId(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testUpdateProductWhenUpdateParamsIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.updateProduct(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetByNameWhenNameIsNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getByName(null))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}