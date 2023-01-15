package com.pas.controller.User;

import com.pas.controller.Utils.ClientFactory;
import com.pas.endpoint.UserAPI;
import com.pas.model.Order;
import com.pas.model.User.User;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Named
@ConversationScoped
@Getter
@Setter
public class EditUserController implements Serializable {
    UserAPI userAPI = ClientFactory.userAPIClient();
    @Inject CommonUserController commonUserController;
    List<Order> currentUserOrders;
    User currentUser;
    String userType;

   public String update(){
       currentUser = commonUserController.createUserOfType(currentUser, userType);
       userAPI.updateUser(currentUser.getId(), currentUser);
       return "ListAllUsers";
   }
}
