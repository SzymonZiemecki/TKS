package com.tks.microservices.userservice.core.model;

import java.util.UUID;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class  User extends IdTrait implements Cloneable {
    @Size(min = 2, max = 20)
    private String firstName;
    @Size(min = 2, max = 20)
    private String lastName;
    @Size(min = 2, max = 20)
    private String login;
    @Size(min = 2, max = 20)
    private String password;
    private Address address;
    private boolean suspended;
    private Double accountBalance;

    public User(String firstName, String lastName, String login, String password, Address address, boolean suspended, Double accountBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
        this.suspended = suspended;
        this.accountBalance = accountBalance;
    }

    public User(UUID id, String firstName, String lastName, String login, String password, Address address, boolean suspended, Double accountBalance) {
        this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
        this.suspended = suspended;
        this.accountBalance = accountBalance;
    }

    public User(String firstName, String lastName, String login, String password, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
    }

    public User() {
        this.address=new Address();
    }

    @Override
    public User clone() throws CloneNotSupportedException {
        return (User) super.clone();
    }

    public User(User user) {
        this.setId(user.getId());
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.login = user.login;
        this.password = user.password;
        this.address = user.address;
        this.suspended = user.suspended;
        this.accountBalance = user.accountBalance;
    }
}
