package com.pas.model.User;

import com.fasterxml.jackson.annotation.*;
import com.pas.model.Address;
import com.pas.model.Cart;
import com.pas.model.IdTrait;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
        @JsonSubTypes.Type(Admin.class),
        @JsonSubTypes.Type(BaseUser.class),
        @JsonSubTypes.Type(Manager.class)}
)
public abstract class User extends IdTrait implements Cloneable {
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

    public User(String firstName, String lastName, String login, String password, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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
