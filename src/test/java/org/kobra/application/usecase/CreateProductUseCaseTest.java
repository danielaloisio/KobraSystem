package org.kobra.application.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kobra.domain.entity.Product;
import org.kobra.domain.repository.ProductRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductUseCase createProductUseCase;

    @Test
    void shouldCreateProductAndReturnSaved() {
        Product input = new Product(null, "Notebook", new BigDecimal("3500.00"));
        Product saved = new Product(UUID.randomUUID(), "Notebook", new BigDecimal("3500.00"));

        when(productRepository.save(input)).thenReturn(saved);

        Product result = createProductUseCase.execute(input);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo("Notebook");
        assertThat(result.getPrice()).isEqualByComparingTo("3500.00");
        verify(productRepository, times(1)).save(input);
    }
}
