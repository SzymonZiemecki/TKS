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
        @JsonSubTypes.Type(BaseUser.class) }
)
public abstract class User extends IdTrait {
    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;
    @NotNull
    @Size(min = 2, max = 20)
    private String lastName;
    @NotNull
    @Size(min = 2, max = 20)
    private String login;
    @NotNull
    @Size(min = 2, max = 20)
    private String password;
    @NotNull
    private Address address;
    private Cart cart;
    private boolean suspended;
    @NotNull
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
}
