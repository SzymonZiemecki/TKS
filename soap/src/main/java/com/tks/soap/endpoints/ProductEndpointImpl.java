package com.tks.soap.endpoints;

import com.tks.Product.Product;
import com.tks.soap.model.ProductSoap;
import com.tks.userinterface.ProductService;
import jakarta.inject.Inject;
import jakarta.jws.WebService;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@WebService(serviceName = "productSoapApi", endpointInterface = "com.tks.soap.endpoints.ProductEndpointApi")

public class ProductEndpointImpl implements ProductEndpointApi {
    @Inject
    ProductService productManager;

    @Override
    public Response getProducts(Optional<String> producer, Optional<String> name) {

        List<Product> productList = productManager.getProducts(producer, name);
        List<ProductSoap> productSoapList = new ArrayList<>();

        for (Product product : productList) {
            productSoapList.add(ProductSoap.productToSoap(product));
        }

        return Response.ok().entity(productSoapList).build();
    }

    @Override
    public Response getProductById(UUID id) {
        return Response.ok().entity(ProductSoap.productToSoap(productManager.findById(id))).build();
    }

    @Override
    public Response addProduct(Product product) {
        return Response.ok().entity(ProductSoap.productToSoap(productManager.addItem(product))).build();
    }

    @Override
    public Response updateProduct(UUID id, Product product) {
        return Response.ok().entity(ProductSoap.productToSoap(productManager.updateProduct(id, product))).build();
    }

    @Override
    public Response deleteProduct(@PathParam("id") UUID id) {
        productManager.removeItem(id);
        return Response.ok().build();
    }
}
