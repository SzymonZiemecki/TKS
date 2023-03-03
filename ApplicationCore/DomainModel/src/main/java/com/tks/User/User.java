package com.tks.User;


import com.tks.model.Address;
import com.tks.model.Cart;
import com.tks.IdTrait;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

public abstract class  User extends IdTrait implements Cloneable {
    @Size(min = 2, max = 20)
    private String firstName;
    @Size(min = 2, max = 20)
    private String lastName;
    @Size(min = 2, max = 20)
    private String login;
    @Size(min = 2, max = 20)
    private String password;
    private Address address;
    private Cart cart;
    private boolean suspended;
    private Double accountBalance;

    public User(String firstName, String lastName, String login, String password, Address address, Cart cart, boolean suspended, Double accountBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
        this.cart = cart;
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
        this.cart = new Cart();
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
        this.cart = user.cart;
        this.suspended = user.suspended;
        this.accountBalance = user.accountBalance;
    }
}
