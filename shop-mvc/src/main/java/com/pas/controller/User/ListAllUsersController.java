package com.pas.controller.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pas.controller.Conversational;
import com.pas.model.Product.Product;
import com.pas.model.User.User;
import com.pas.model.dto.UserDTO;
import com.pas.restClient.UserApiClient;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import lombok.Getter;
import lombok.Setter;

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
    UserApiClient userApiClient;

    @Inject
    EditUserController editUserController;
    @Inject
    UserCartController userCartController;

    List<UserDTO> currentUsers;
    String searchInput = "";

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(
            "messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @PostConstruct
    public void initCurrentUsers(){
       currentUsers = userApiClient.getAllUsers();
    }
    public String suspendOrResumeUser(UserDTO user){
        userApiClient.suspendOrResumeUser(user.getId());
        return "ListAllUsers";
    }

    public void getSearchedUsers(){
        currentUsers = userApiClient.getSearchedUsers(Optional.of(searchInput));
    }

    public String editUser(UserDTO user){
        beginNewConversation();
        editUserController.setCurrentUser(user);
        editUserController.setUserType(user.getClass().getSimpleName());
        return "EditUser";
    }

    public String getDetails(UserDTO user){
        beginNewConversation();
        editUserController.setCurrentUser(user);
        editUserController.setUserType(user.getClass().getSimpleName());
 /*       editUserController.setCurrentUserOrders(userApiClient.findUserOrders(user.getId()));*/
        return "UserDetails";
    }

    public String getCart(User user){
        beginNewConversation();
        userCartController.setCart(userApiClient.getUserCart(user.getId()));
        return "UserCart";
    }
    public void loginValidator(FacesContext context, UIComponent component, Object value){
        String login = (String) value;
        if (!userApiClient.findOneByLogin(login).isEmpty()) {
            throw new ValidatorException(new FacesMessage(resourceBundle.getString("validatorMessageLoginUsed")));
        }
    }
}
