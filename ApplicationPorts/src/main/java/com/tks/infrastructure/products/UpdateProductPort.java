package com.tks.infrastructure.products;

import com.tks.Product.Product;

import java.util.UUID;

public interface UpdateProductPort {
    Product updateProduct(UUID id, Product product);
}
