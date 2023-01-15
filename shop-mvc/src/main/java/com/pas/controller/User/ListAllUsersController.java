package com.pas.controller.User;

import com.pas.controller.Conversational;
import com.pas.controller.Utils.ClientFactory;
import com.pas.endpoint.OrderAPI;
import com.pas.endpoint.UserAPI;
import com.pas.model.User.User;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Named
@ViewScoped
@Getter
@Setter
public class ListAllUsersController extends Conversational implements Serializable {
    UserAPI userAPI = ClientFactory.userAPIClient();
    OrderAPI orderAPI = ClientFactory.orderAPIClient();
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
        currentUsers = userAPI.getUsers(null, null);
    }

    public String suspendOrResumeUser(User user){
        userAPI.suspendOrResumeUser(user.getId(), !user.isSuspended());
        return "ListAllUsers";
    }

    public void getSearchedUsers(){
        currentUsers = userAPI.getUsers(Optional.of(searchInput), null);
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
        editUserController.setCurrentUserOrders(userAPI.getOngoingUserOrders(user.getId()));
        return "UserDetails";
    }

    public String getCart(User user){
        beginNewConversation();
        userCartController.setCurrentUser(user);
        return "UserCart";
    }
    public void loginValidator(FacesContext context, UIComponent component, Object value){
        String login = (String) value;
        if (!userAPI.getUsers(null, Optional.of(login)).isEmpty()) {
            throw new ValidatorException(new FacesMessage(resourceBundle.getString("validatorMessageLoginUsed")));
        }
    }
}
