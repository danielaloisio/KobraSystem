package org.kobra.application.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kobra.domain.entity.Product;
import org.kobra.domain.repository.ProductRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private FindProductUseCase findProductUseCase;

    @Test
    void shouldReturnProductWhenFound() {
        UUID id = UUID.randomUUID();
        Product product = new Product(id, "Notebook", new BigDecimal("3500.00"));

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product result = findProductUseCase.execute(id);

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo("Notebook");
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowWhenProductNotFound() {
        UUID id = UUID.randomUUID();

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> findProductUseCase.execute(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Product not found");
    }
}
