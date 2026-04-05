package org.kobra.application.usecase;

import org.kobra.domain.entity.Product;
import org.kobra.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllProductUseCase {

    private final ProductRepository productRepository;

    public GetAllProductUseCase(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> execute() {
        return productRepository.getAll()
                .orElseThrow(() -> new RuntimeException("Products not found"));
    }
}
