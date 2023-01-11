package com.pas.controller.User;

import com.pas.controller.Conversational;
import com.pas.manager.OrderManager;
import com.pas.manager.UserManager;
import com.pas.model.Cart;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.Admin;
import com.pas.model.User.BaseUser;
import com.pas.model.User.Manager;
import com.pas.model.User.User;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.pas.controller.Utils.ViewUtils.getClassName;

@Named
@ViewScoped
@Getter
@Setter
public class ListAllUsersController extends Conversational implements Serializable {
    @Inject
    UserManager userManager;
    @Inject
    OrderManager orderManager;
    @Inject
    EditUserController editUserController;
    @Inject
    AddUserController addUserController;
    @Inject
    UserCartController userCartController;
    List<User> currentUsers;
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
        editUserController.setCurrentUser(user);
        editUserController.setUserType(user.getClass().getSimpleName());
        return "EditUser";
    }

    public String getDetails(User user){
        beginNewConversation();
        editUserController.setCurrentUser(user);
        editUserController.setUserType(user.getClass().getSimpleName());
        editUserController.setCurrentUserOrders(userManager.findUserOrders(user.getId()));
        return "UserDetails";
    }

    public String getCart(User user){
        beginNewConversation();
        userCartController.setCurrentUser(user);
        return "UserCart";
    }
    public void loginValidator(FacesContext context, UIComponent component, Object value){
        String login = (String) value;
        if (!userManager.findOneByLogin(login).isEmpty()) {
            throw new ValidatorException(new FacesMessage(resourceBundle.getString("validatorMessageLoginUsed")));
        }
    }
}
