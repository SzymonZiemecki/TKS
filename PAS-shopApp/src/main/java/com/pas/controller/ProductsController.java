package com.pas.controller;

import com.pas.manager.ProductManager;
import com.pas.model.Product.Product;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

@Named
@ConversationScoped
public class ProductsController extends Conversational implements Serializable {
    @Inject
    ProductManager productManager;

    List<Product> currentProducts;

    public void setCurrentProducts(List<Product> currentProducts) {
        this.currentProducts = currentProducts;
    }

    public List<Product> getCurrentProducts(){
        return productManager.findAllProducts();
    }

    @PostConstruct
    public void initCurrentProducts(){
        currentProducts = productManager.findAllProducts();
    }
}
