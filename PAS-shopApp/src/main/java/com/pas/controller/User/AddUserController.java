package com.pas.controller.User;

import com.pas.manager.UserManager;
import com.pas.model.User.BaseUser;
import com.pas.model.User.User;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
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
    UserManager userManager;
    @Inject CommonUserController commonUserController;
    User currentUser = new BaseUser();
    String userType = "BaseUser";

    public String add(){
        currentUser = commonUserController.createUserOfType(currentUser, userType);
        userManager.register(currentUser);
        return "ListAllUsers";
    }
}
