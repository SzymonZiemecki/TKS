package com.tks.services;

import com.tks.infrastructure.carts.AddToUserCartPort;
import com.tks.infrastructure.products.AddProductPort;
import com.tks.infrastructure.products.DeleteProductPort;
import com.tks.infrastructure.products.GetProductPort;
import com.tks.infrastructure.products.UpdateProductPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProductService {

    @Inject
    AddProductPort addProductPort;
    @Inject
    UpdateProductPort updateProductPort;
    @Inject
    GetProductPort getProductPort;
    @Inject
    DeleteProductPort deleteProductPort;
}
