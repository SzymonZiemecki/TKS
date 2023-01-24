package com.pas.controller.Product;

import com.pas.controller.Conversational;
import com.pas.controller.Utils.ClientFactory;
import com.pas.controller.Utils.ViewUtils;
import com.pas.model.IdTrait;
import com.pas.model.Order;
import com.pas.model.Product.Laptop;
import com.pas.model.Product.MobilePhone;
import com.pas.model.Product.Product;
import com.pas.model.Product.Tv;
import com.pas.model.User.User;
import com.pas.model.dto.UserDTO;
import com.pas.restClient.ProductApiClient;
import com.pas.restClient.UserApiClient;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.net.http.HttpResponse;
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
    ProductApiClient productApiClient;
    @Inject
    UserApiClient userApiClient;

    @Inject
    EditProductController editProductController;
    List<Product> currentProducts;
    @Getter
    @Setter
    String currentUserId;

    boolean isCreatingNewProduct = true;


    @PostConstruct
    public void initCurrentProducts()  {
        currentProducts = productApiClient.getAllProducts();
    }
    public String delete(Product product) {
        productApiClient.deleteProductById(product.getId());
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
        editProductController.setCurrentUsers(userApiClient.getAllUsers().stream().collect(Collectors.toMap(UserDTO::getId, UserDTO::getLogin)));
        editProductController.setCurrentProductOrders(productApiClient.ordersContainingProduct(product.getId()));
        return "ProductDetails";
    }
}
