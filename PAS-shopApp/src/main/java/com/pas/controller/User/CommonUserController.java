package com.pas.controller.User;

import com.pas.model.User.Admin;
import com.pas.model.User.BaseUser;
import com.pas.model.User.Manager;
import com.pas.model.User.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ApplicationScoped
public class CommonUserController implements Serializable {
    public User createUserOfType(User user, String userType){
        if(userType.equals("BaseUser")){
            user = new BaseUser(user);
        } else if(userType.equals("Manager")){
            user = new Manager(user);
        } else {
            user = new Admin(user);
        }
        return user;
    }

}
