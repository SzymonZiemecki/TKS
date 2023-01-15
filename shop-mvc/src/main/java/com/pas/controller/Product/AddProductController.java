package com.pas.controller.Product;

import com.pas.controller.Utils.ClientFactory;
import com.pas.endpoint.ProductAPI;
import com.pas.model.Product.Product;
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

    ProductAPI productAPI = ClientFactory.productAPIClient();
    @Inject
    CommonProductController commonProductController;

    Product currentProduct;
    String productType;

    public String add() throws CloneNotSupportedException {
        productAPI.addProduct(currentProduct);
        return "ListAllProducts";
    }

    public void updateProduct(){
        currentProduct = commonProductController.changeProductType(productType);
    }
}
