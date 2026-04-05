package org.kobra.infrastructure.persistence;

import org.kobra.domain.entity.Product;
import org.kobra.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductSpringDataRepository springDataRepository;

    public ProductRepositoryImpl(ProductSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Product save(Product product) {
        ProductJpaEntity entity = toEntity(product);
        ProductJpaEntity saved = springDataRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return springDataRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<List<Product>> getAll() {
        return Optional.of(toDomains(springDataRepository.findAll()));
    }

    private ProductJpaEntity toEntity(Product product) {
        return new ProductJpaEntity(product.getId(), product.getName(), product.getPrice());
    }

    private Product toDomain(ProductJpaEntity entity) {
        return new Product(entity.getId(), entity.getName(), entity.getPrice());
    }

    private List<Product> toDomains(List<ProductJpaEntity> entities) {

        return entities.stream().map(entity -> new Product(entity.getId(), entity.getName(), entity.getPrice())).collect(Collectors.toList());
    }
}
