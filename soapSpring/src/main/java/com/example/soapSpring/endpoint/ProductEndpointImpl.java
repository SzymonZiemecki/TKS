package com.example.soapSpring.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.soapSpring.model.ProductSoap;
import com.tks.Product.Product;
import com.tks.services.ProductServiceImpl;
import com.tks.userinterface.ProductService;

import jakarta.inject.Inject;
import jakarta.inject.Named;

@Controller
public class ProductEndpointImpl {

    @Inject
    ProductService productManager;

    public String getProducts(Optional<String> producer, Optional<String> name) {

        List<Product> productList = productManager.getProducts(producer, name);
        List<ProductSoap> productSoapList = new ArrayList<>();

        for (Product product : productList) {
            productSoapList.add(ProductSoap.productToSoap(product));
        }

        return "hello";
    }
//
//    public Response getProductById(UUID id) {
//        return Response.ok().entity(ProductSoap.productToSoap(productManager.findById(id))).build();
//    }
//
//    public Response addProduct(Product product) {
//        return Response.ok().entity(ProductSoap.productToSoap(productManager.addItem(product))).build();
//    }
//
//    public Response updateProduct(UUID id, Product product) {
//        return Response.ok().entity(ProductSoap.productToSoap(productManager.updateProduct(id, product))).build();
//    }
//
//    public Response deleteProduct(@PathParam("id") UUID id) {
//        productManager.removeItem(id);
//        return Response.ok().build();
//    }
}
