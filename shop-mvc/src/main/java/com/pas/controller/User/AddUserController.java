package com.pas.controller.User;

import com.pas.model.User.BaseUser;
import com.pas.model.User.User;
import com.pas.model.dto.UserDTO;
import com.pas.restClient.UserApiClient;
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
    @Inject
    UserApiClient userApiClient;
    @Inject CommonUserController commonUserController;
    UserDTO currentUser;
    String userType = "BaseUser";

    public String add(){
        currentUser = commonUserController.createUserOfType(currentUser, userType);
        userApiClient.addUser(currentUser);
        return "ListAllUsers";
    }

    public String register(){
        currentUser = commonUserController.createUserOfType(currentUser, userType);
        userApiClient.register(currentUser);
        return "Start";
    }
}
