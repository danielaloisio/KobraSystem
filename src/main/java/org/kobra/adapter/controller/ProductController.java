package org.kobra.adapter.controller;

import jakarta.validation.Valid;
import org.kobra.adapter.dto.ProductRequest;
import org.kobra.adapter.dto.ProductResponse;
import org.kobra.application.usecase.CreateProductUseCase;
import org.kobra.application.usecase.FindProductUseCase;
import org.kobra.application.usecase.GetAllProductUseCase;
import org.kobra.domain.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final FindProductUseCase findProductUseCase;
    private final GetAllProductUseCase getAllProductUseCase;

    public ProductController(CreateProductUseCase createProductUseCase,
                             FindProductUseCase findProductUseCase, GetAllProductUseCase getAllProductUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.findProductUseCase = findProductUseCase;
        this.getAllProductUseCase = getAllProductUseCase;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        Product product = new Product(null, request.name(), request.price());
        Product created = createProductUseCase.execute(product);
        return ResponseEntity.ok(ProductResponse.from(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> find(@PathVariable UUID id) {
        Product product = findProductUseCase.execute(id);
        return ResponseEntity.ok(ProductResponse.from(product));
    }

    @GetMapping("")
    public ResponseEntity<List<ProductResponse>> getAll() {
        List<Product> products = getAllProductUseCase.execute();
        return ResponseEntity.ok(ProductResponse.fromList(products));
    }
}
