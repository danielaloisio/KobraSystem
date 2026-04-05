package org.kobra.application.usecase;

import org.kobra.domain.entity.Product;
import org.kobra.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindProductUseCase {

    private final ProductRepository productRepository;

    public FindProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }
}
