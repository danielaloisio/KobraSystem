package org.kobra.adapter.dto;

import org.kobra.domain.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ProductResponse(
        UUID id,
        String name,
        BigDecimal price
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice());
    }

    public static List<ProductResponse> fromList(List<Product> products){

        return products.stream().map(product -> new ProductResponse(product.getId(), product.getName(), product.getPrice())).collect(Collectors.toList());
    }

}
