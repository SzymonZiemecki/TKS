package com.pas.controller.Product;

import com.pas.manager.ProductManager;
import com.pas.model.Product.Product;
import com.pas.model.Product.Tv;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Named
@ViewScoped
@Getter
@Setter
public class AddProductController implements Serializable {
    @Inject
    ProductManager productManager;
    @Inject
    CommonProductController commonProductController;

    Product currentProduct;
    String productType;

    public String add() throws CloneNotSupportedException {
        productManager.addItem(currentProduct);
        return "ListAllProducts";
    }

    public void updateProduct(){
        currentProduct = commonProductController.changeProductType(productType);
    }
}
