package org.kobra.application.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kobra.domain.entity.Product;
import org.kobra.domain.repository.ProductRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllProductUserCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetAllProductUseCase getAllProductUseCase;

    @Test
    void shouldReturnProductWhenFound() {
        UUID id = UUID.randomUUID();
        List<Product> products = new ArrayList<>();

        products.add(new Product(id, "Notebook", new BigDecimal("3500.00")));

        when(productRepository.getAll()).thenReturn(Optional.of(products));

        List<Product> results = getAllProductUseCase.execute();

        assertThat(results.getFirst().getId()).isEqualTo(id);
        assertThat(results.getFirst().getName()).isEqualTo("Notebook");
        verify(productRepository, times(1)).getAll();
    }
}