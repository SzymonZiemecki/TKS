package com.pas.controller.User;

import com.pas.controller.Utils.ClientFactory;
import com.pas.endpoint.OrderAPI;
import com.pas.endpoint.UserAPI;
import com.pas.model.User.BaseUser;
import com.pas.model.User.User;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Named
@ViewScoped
@Getter
@Setter
public class AddUserController implements Serializable {
    UserAPI userAPI = ClientFactory.userAPIClient();
    @Inject CommonUserController commonUserController;
    User currentUser = new BaseUser();
    String userType = "BaseUser";

    public String add(){
        currentUser = commonUserController.createUserOfType(currentUser, userType);
        userAPI.register(currentUser);
        return "ListAllUsers";
    }
}
