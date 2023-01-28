package com.pas.controller.User;

import com.pas.model.Order;
import com.pas.model.User.Admin;
import com.pas.model.User.BaseUser;
import com.pas.model.User.Manager;
import com.pas.model.User.User;
import com.pas.model.dto.UserDTO;
import com.pas.restClient.UserApiClient;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import static com.pas.controller.User.ChangePasswordController.context;

@Named
@ConversationScoped
@Getter
@Setter
public class EditUserController implements Serializable {
    @Inject
    UserApiClient userApiClient;
    @Inject CommonUserController commonUserController;
    List<Order> currentUserOrders;
    UserDTO currentUser;
    String userType;
    String ifMatch ="";

    @PostConstruct()
    public void init(){
        Response res = userApiClient.findOneByLogin(context().getUserPrincipal().getName());
        currentUser = res.readEntity(UserDTO.class);
        ifMatch = res.getHeaderString("ETag");
        currentUserOrders = userApiClient.getUserOrdersAdmin(currentUser.getId());

    }

   public String update(){
       currentUser = commonUserController.createUserOfType(currentUser, userType);
       userApiClient.updateUser(currentUser.getId(), currentUser, ifMatch);
       return "ListAllUsers";
   }
}
