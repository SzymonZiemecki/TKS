package com.tks.microservices.userservice.repository.model;

import java.util.UUID;

import com.tks.microservices.userservice.core.model.Admin;
import com.tks.microservices.userservice.core.model.BaseUser;
import com.tks.microservices.userservice.core.model.Manager;
import com.tks.microservices.userservice.core.model.User;

import jakarta.validation.constraints.Size;
import jakarta.ws.rs.NotSupportedException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
public class UserEnt extends IdTraitEnt {
    @Size(min = 2, max = 20)
    private String firstName;
    @Size(min = 2, max = 20)
    private String lastName;
    @Size(min = 2, max = 20)
    private String login;
    @Size(min = 2, max = 20)
    private String password;
    private AddressEnt address;
    private boolean suspended;
    private Double accountBalance;

    public UserEnt(String firstName, String lastName, String login, String password, AddressEnt address, boolean suspended, Double accountBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
        this.suspended = suspended;
        this.accountBalance = accountBalance;
    }

    public UserEnt(UUID id, String firstName, String lastName, String login, String password, AddressEnt address, boolean suspended, Double accountBalance) {
        this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
        this.suspended = suspended;
        this.accountBalance = accountBalance;
    }

    public UserEnt(String firstName, String lastName, String login, String password, AddressEnt address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
    }

    public UserEnt() {
        this.address=new AddressEnt();
    }

    @Override
    public UserEnt clone() throws CloneNotSupportedException {
        return (UserEnt) super.clone();
    }

    public UserEnt(UserEnt user) {
        this.setId(user.getId());
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.login = user.login;
        this.password = user.password;
        this.address = user.address;
        this.suspended = user.suspended;
        this.accountBalance = user.accountBalance;
    }

    @SneakyThrows
    public static UserEnt userToEnt(User user) {
        UserEnt userEnt;
        if(user instanceof BaseUser){
            userEnt = new BaseUserEnt();
        }
        else if(user instanceof Manager){
            userEnt = new ManagerEnt();
        }
        else if(user instanceof Admin){
            userEnt = new AdminEnt();
        }
        else {
            throw new NotSupportedException();
        }
        copyUserProperties(user, userEnt);
        return userEnt;
    }

    @SneakyThrows
    public static User userEntToDomainModel(UserEnt userEnt) {
        User user;
        if(userEnt instanceof BaseUserEnt){
            user = new BaseUser();
        }
        else if(userEnt instanceof ManagerEnt){
            user = new Manager();
        }
        else if(userEnt instanceof AdminEnt){
            user = new Admin();
        }
        else {
            throw new NotSupportedException();
        }
        copyUserEntProperties(user, userEnt);
        return user;
    }

    private static void copyUserEntProperties(User user, UserEnt userEnt){
        user.setId(userEnt.getId());
        user.setSuspended(userEnt.isSuspended());
        user.setFirstName(userEnt.getFirstName());
        user.setLastName(userEnt.getLastName());
        user.setPassword(userEnt.getPassword());
        user.setLogin(userEnt.getLogin());
        user.setAddress(AddressEnt.addressEntToDomainModel(userEnt.getAddress()));
        user.setAccountBalance(userEnt.getAccountBalance());
    }

    private static void copyUserProperties(User user, UserEnt userEnt){
        userEnt.setId(user.getId());
        userEnt.setSuspended(user.isSuspended());
        userEnt.setFirstName(user.getFirstName());
        userEnt.setLastName(user.getLastName());
        userEnt.setPassword(user.getPassword());
        userEnt.setLogin(user.getLogin());
        userEnt.setAddress(AddressEnt.addressToEntModel(user.getAddress()));
        userEnt.setAccountBalance(user.getAccountBalance());
    }
}