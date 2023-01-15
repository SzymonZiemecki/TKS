package com.pas.controller.Product;

import com.pas.controller.Conversational;
import com.pas.controller.Utils.ClientFactory;
import com.pas.controller.Utils.ViewUtils;
import com.pas.endpoint.ProductAPI;
import com.pas.endpoint.UserAPI;
import com.pas.model.IdTrait;
import com.pas.model.Product.Product;
import com.pas.model.User.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
@Getter
@Setter
public class ListAllProductsController extends Conversational implements Serializable {
    ProductAPI productAPI = ClientFactory.productAPIClient();
    UserAPI userAPI = ClientFactory.userAPIClient();
    @Inject
    AddProductController addProductController;
    @Inject
    EditProductController editProductController;
    List<Product> currentProducts;
    @Getter
    @Setter
    String currentUserId;

    boolean isCreatingNewProduct = true;


    @PostConstruct
    public void initCurrentProducts() {
        currentProducts = productAPI.getProducts(null,null);
    }

    public String delete(Product product) {
        productAPI.deleteProduct(product.getId());
        return "ListAllProducts";
    }

    public String editProduct(Product product) {
        beginNewConversation();
       editProductController.setCurrentProduct(product);
       editProductController.setProductType(ViewUtils.getClassName(product));
        return "EditProduct";
    }

    public String getDetails(Product product) {
        beginNewConversation();
        editProductController.setCurrentProduct(product);
        editProductController.setProductType(ViewUtils.getClassName(product));
        editProductController.setCurrentUsers(userAPI.getUsers(null,null).stream().collect(Collectors.toMap(IdTrait::getId, User::getLogin)));
        editProductController.setCurrentProductOrders(new ArrayList<>());
        return "ProductDetails";
    }
}
