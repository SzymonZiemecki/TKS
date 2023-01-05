package com.pas.controller;

import com.pas.manager.ProductManager;
import com.pas.manager.UserManager;
import com.pas.model.Address;
import com.pas.model.Product.Product;
import com.pas.model.User.Admin;
import com.pas.model.User.BaseUser;
import com.pas.model.User.Manager;
import com.pas.model.User.User;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Named
@ConversationScoped
public class UsersController extends Conversational implements Serializable {
    @Inject
    UserManager userManager;
    List<User> currentUsers;

    User copyOfUser;

    String userType;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public User getCopyOfUser() {
        return copyOfUser;
    }

    public void setCopyOfUser(User copyOfUser) {
        this.copyOfUser = copyOfUser;
    }

    String searchInput = "";

    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    public List<User> getCurrentUsers() {
        return currentUsers;
    }

    public void setCurrentUsers(List<User> currentUsers) {
        this.currentUsers = currentUsers;
    }

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

    public String addressToUiView(Address address){
        StringBuilder sb = new StringBuilder();
        sb = address.getCountry() != null ? sb.append(address.getCountry() + ",") : sb.append("");
        sb = address.getCountry() != null ? sb.append(address.getCity() + ",") : sb.append("");
        sb = address.getStreet() != null ? sb.append(address.getStreet() + ",") : sb.append("");
        sb = address.getHouseNumber() != null ? sb.append(address.getHouseNumber() + ",") : sb.append("");
        sb = address.getZipCode() != null ? sb.append(address.getZipCode() + ",") : sb.append("");
        if(sb.charAt(sb.length()-1) == ','){
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    public String getClassName(User user){
        return user.getClass().getSimpleName();
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
        if(userType.equals("BaseUser")){
            BaseUser changedType = new BaseUser(user);
            userType = getClassName(changedType);
            userManager.updateUser(user.getId(), changedType);
        } else if(userType.equals("Manager")){
            Manager changedType = new Manager(user);
            userType = getClassName(changedType);
            userManager.updateUser(user.getId(), changedType);
        } else {
            Admin changedType = new Admin(user);
            userType = getClassName(changedType);
            userManager.updateUser(user.getId(), changedType);
        }
        refreshView();
        return "ListAllUsers";
    }

    private void refreshView(){
        currentUsers = userManager.findAllUsers();
    }
}
