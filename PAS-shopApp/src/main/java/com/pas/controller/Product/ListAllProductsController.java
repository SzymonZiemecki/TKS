package com.pas.controller.Product;

import com.pas.controller.Conversational;
import com.pas.controller.Utils.ViewUtils;
import com.pas.manager.ProductManager;
import com.pas.manager.UserManager;
import com.pas.model.IdTrait;
import com.pas.model.Order;
import com.pas.model.Product.Laptop;
import com.pas.model.Product.MobilePhone;
import com.pas.model.Product.Product;
import com.pas.model.Product.Tv;
import com.pas.model.User.User;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.pas.controller.Utils.ViewUtils.getClassName;

@Named
@ViewScoped
@Getter
@Setter
public class ListAllProductsController extends Conversational implements Serializable {
    @Inject
    ProductManager productManager;
    @Inject
    UserManager userManager;
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
        currentProducts = productManager.findAllProducts();
    }

    public String delete(Product product) {
        productManager.removeItem(product.getId());
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
        editProductController.setCurrentUsers(userManager.findAllUsers().stream().collect(Collectors.toMap(IdTrait::getId, User::getLogin)));
        editProductController.setCurrentProductOrders(productManager.ordersContainingProduct(product));
        return "ProductDetails";
    }
}
