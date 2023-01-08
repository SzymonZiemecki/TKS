package com.pas.controller;

import com.pas.manager.ProductManager;
import com.pas.manager.UserManager;
import com.pas.model.IdTrait;
import com.pas.model.Order;
import com.pas.model.Product.Laptop;
import com.pas.model.Product.MobilePhone;
import com.pas.model.Product.Product;
import com.pas.model.Product.Tv;
import com.pas.model.User.Admin;
import com.pas.model.User.BaseUser;
import com.pas.model.User.Manager;
import com.pas.model.User.User;
import jakarta.enterprise.context.ConversationScoped;
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

import static com.pas.controller.ViewUtils.getClassName;

@Named
@ConversationScoped
@Getter
@Setter
public class ProductsController extends Conversational implements Serializable {
    @Inject
    ProductManager productManager;
    @Inject
    UserManager userManager;
    List<Product> currentProducts;

    Product copyOfProduct = new Tv();
    String productType ="";

    List<Order> currentProductOrders;
    Map<UUID, String> currentUsers;
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
        isCreatingNewProduct = false;
        try {
            copyOfProduct = product.clone();
            productType = getClassName(product);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return "EditProduct";
    }

    public String getDetails(Product product) {
        beginNewConversation();
        productType = getClassName(product);
        try {
            copyOfProduct = product.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        currentProductOrders = productManager.ordersContainingProduct(product);
        currentUsers = userManager.findAllUsers().stream().collect(Collectors.toMap(IdTrait::getId, User::getLogin));
        return "ProductDetails";
    }

    public String saveUpdate() throws CloneNotSupportedException {
        productManager.updateProduct(copyOfProduct.getId(), copyOfProduct);
        currentProducts = productManager.findAllProducts();
        return "ListAllProducts";
    }

    public String addToCart() {
        userManager.addToCart(UUID.fromString(currentUserId), copyOfProduct.getId(), 1l);
        return "ListAllProducts";
    }

    public String addProduct() {
        productManager.addItem(copyOfProduct);
        return "ListAllProducts";
    }
    public void changeProductType(){
        if(productType.equals("MobilePhone")){
            copyOfProduct = new MobilePhone();
        } else if (productType.equals("Laptop")){
            copyOfProduct = new Laptop();
        } else {
            copyOfProduct = new Tv();
        }
    }
}
