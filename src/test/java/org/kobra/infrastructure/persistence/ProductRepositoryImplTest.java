package org.kobra.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kobra.domain.entity.Product;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryImplTest {

    @Mock
    private ProductSpringDataRepository springDataRepository;

    @InjectMocks
    private ProductRepositoryImpl productRepositoryImpl;

    @Test
    void shouldSaveAndMapToDomain() {
        UUID id = UUID.randomUUID();
        Product input = new Product(null, "Monitor", new BigDecimal("1200.00"));
        ProductJpaEntity savedEntity = new ProductJpaEntity(id, "Monitor", new BigDecimal("1200.00"));

        when(springDataRepository.save(any(ProductJpaEntity.class))).thenReturn(savedEntity);

        Product result = productRepositoryImpl.save(input);

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo("Monitor");
        assertThat(result.getPrice()).isEqualByComparingTo("1200.00");
    }

    @Test
    void shouldFindByIdAndMapToDomain() {
        UUID id = UUID.randomUUID();
        ProductJpaEntity entity = new ProductJpaEntity(id, "Monitor", new BigDecimal("1200.00"));

        when(springDataRepository.findById(id)).thenReturn(Optional.of(entity));

        Optional<Product> result = productRepositoryImpl.findById(id);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(id);
        assertThat(result.get().getName()).isEqualTo("Monitor");
    }

    @Test
    void shouldFindAllAndMapToDomains() {
        UUID id = UUID.randomUUID();
        List<ProductJpaEntity> entities = new ArrayList<>();

        entities.add(new ProductJpaEntity(id, "Monitor", new BigDecimal("1200.00")));

        when(springDataRepository.findAll()).thenReturn(entities);

        Optional<List<Product>> results = productRepositoryImpl.getAll();

        assertThat(results).isPresent();
        assertThat(results.get().getFirst().getId()).isEqualTo(id);
        assertThat(results.get().getFirst().getName()).isEqualTo("Monitor");
    }

    @Test
    void shouldReturnEmptyWhenProductNotFound() {
        UUID id = UUID.randomUUID();

        when(springDataRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Product> result = productRepositoryImpl.findById(id);

        assertThat(result).isEmpty();
    }
}
