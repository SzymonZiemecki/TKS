package com.pas.controller;

import com.pas.manager.OrderManager;
import com.pas.manager.ProductManager;
import com.pas.manager.UserManager;
import com.pas.model.Address;
import com.pas.model.Cart;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.Admin;
import com.pas.model.User.BaseUser;
import com.pas.model.User.Manager;
import com.pas.model.User.User;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.pas.controller.ViewUtils.getClassName;

@Named
@ConversationScoped
@Getter
@Setter
public class UsersController extends Conversational implements Serializable {
    @Inject
    UserManager userManager;
    @Inject
    OrderManager orderManager;
    List<User> currentUsers;

    User currentUser;

    Cart currentUserCart;
    User createdUser = new BaseUser();

    List<Order> currentUserOrders;
    User copyOfUser;

    String userType;

    String searchInput = "";

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(
            "messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @PostConstruct
    public void initCurrentProducts(){
        currentUsers = userManager.findAllUsers();
    }

    public String suspendOrResumeUser(User user){
        userManager.suspendOrResumeUser(user.getId(), !user.isSuspended());
        return "ListAllUsers";
    }

    public void getSearchedUsers(){
        currentUsers = userManager.findUsers(Optional.of(searchInput), null);
    }

    public String editUser(User user){
        beginNewConversation();
        try {
            copyOfUser = (User) user.clone();
            userType = getClassName(user);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return "EditUser";
    }

    public String saveUpdate(User user) throws CloneNotSupportedException {
        user = createUserOfType(user);
        userManager.updateUser(user.getId(), user);
        currentUsers = userManager.findAllUsers();
        return "ListAllUsers";
    }

    public String getDetails(User user){
        userType = getClassName(user);
        try {
            currentUser = user.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        currentUserOrders = userManager.findUserOrders(currentUser.getId());
        return "UserDetails";
    }
    public String addUser(){
        createdUser = createUserOfType(createdUser);
        userManager.register(createdUser);
        return "ListAllUsers";
    }
    private User createUserOfType(User user){
        if(userType.equals("BaseUser")){
            user = new BaseUser(user);
        } else if(userType.equals("Manager")){
            user = new Manager(user);
        } else {
            user = new Admin(user);
        }
        return user;
    }

    public String getCart(User user){
        beginNewConversation();
        try {
            currentUser = user.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return "UserCart";
    }

    public String createOrder(){
        orderManager.createOrder(currentUser.getId(), currentUser.getAddress());
        return "ListAllUsers";
    }

    public String removeFromCart(Product product){
        beginNewConversation();
        userManager.removeFromCart(currentUser.getId(), product.getId());
        currentUser = userManager.findById(currentUser.getId());
        return "UserCart";
    }

    public void loginValidator(FacesContext context, UIComponent component, Object value){
        String login = (String) value;
        if (!userManager.findOneByLogin(login).isEmpty()) {
            throw new ValidatorException(new FacesMessage(resourceBundle.getString("validatorMessageLoginUsed")));
        }
    }
}
