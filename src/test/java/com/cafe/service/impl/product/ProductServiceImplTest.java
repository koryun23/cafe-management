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
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        Product product = new Product("Pepsi", 4, 300);
        product.setId(1L);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(product));

        Mockito.verify(productRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testFindByNameWhenProductDoesNotExist() {
        Mockito.when(productRepository.findByProductName("Pepsi")).thenReturn(Optional.empty());
        Assertions.assertThat(testSubject.findByName("Pepsi")).isEqualTo(Optional.empty());
        Mockito.verify(productRepository).findByProductName("Pepsi");
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testFindByNameWhenProductExists() {
        Product product = new Product("Pepsi", 4, 300);
        product.setId(1L);

        Mockito.when(productRepository.findByProductName("Pepsi")).thenReturn(Optional.of(product));

        Assertions.assertThat(testSubject.findByName("Pepsi")).isEqualTo(Optional.of(product));

        Mockito.verify(productRepository).findByProductName("Pepsi");
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetAmountByProductNameWhenProductDoesNotExist() {
        Mockito.when(productRepository.findByProductName("Pepsi")).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.getAmountByProductName("Pepsi")).isExactlyInstanceOf(ProductNotFoundException.class);
        Mockito.verify(productRepository).findByProductName("Pepsi");
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetAmountByProductNameWhenProductExists() {
        Product product = new Product("Pepsi", 4, 300);
        product.setId(1L);

        Mockito.when(productRepository.findByProductName("Pepsi")).thenReturn(Optional.of(product));

        Assertions.assertThat(testSubject.getAmountByProductName("Pepsi")).isEqualTo(300);

        Mockito.verify(productRepository).findByProductName("Pepsi");
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
        Product product = new Product("Pepsi", 4, 300);
        product.setId(1L);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Assertions.assertThat(testSubject.getAmountByProductId(1L)).isEqualTo(300);

        Mockito.verify(productRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetByNameWhenProductDoesNotExist() {
        Mockito.when(productRepository.findByProductName("Pepsi")).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> testSubject.getByName("Pepsi")).isExactlyInstanceOf(ProductNotFoundException.class);
        Mockito.verify(productRepository).findByProductName("Pepsi");
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetByNameWhenProductExists() {
        Product product = new Product("Pepsi", 4, 300);
        product.setId(1L);

        Mockito.when(productRepository.findByProductName("Pepsi")).thenReturn(Optional.of(product));

        Assertions.assertThat(testSubject.getByName("Pepsi")).isEqualTo(product);

        Mockito.verify(productRepository).findByProductName("Pepsi");
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
        Product product = new Product("Pepsi", 4, 300);
        product.setId(1L);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Assertions.assertThat(testSubject.getById(1L)).isEqualTo(product);

        Mockito.verify(productRepository).findById(1L);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testCreate() {
        Product product = new Product("Pepsi", 4, 300);

        Product savedProduct = new Product("Pepsi", 4, 300);
        savedProduct.setId(1L);

        Mockito.when(productRepository.save(product)).thenReturn(savedProduct);

        Assertions.assertThat(testSubject.create(new ProductCreationParams(
                "Pepsi", 4, 300
        ))).isEqualTo(savedProduct);

        Mockito.verify(productRepository).save(product);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testUpdate() {
        Product product = new Product("Pepsi", 4, 300);
        product.setId(1L);

        Product updatedProduct = new Product("Pepsi", 4, 500);
        updatedProduct.setId(1L);

        Mockito.when(productRepository.findByProductName("Pepsi")).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        Assertions.assertThat(testSubject.updateProduct(
                new ProductUpdateParams("Pepsi", "Pepsi", 500, 4)
        )).isEqualTo(updatedProduct);

        Mockito.verify(productRepository).save(updatedProduct);
        Mockito.verify(productRepository).findByProductName("Pepsi");
        Mockito.verifyNoMoreInteractions(productRepository);
    }
}