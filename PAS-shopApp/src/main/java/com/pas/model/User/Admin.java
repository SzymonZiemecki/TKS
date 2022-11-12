package com.pas.model.User;

import com.pas.model.Address;
import com.pas.model.Cart;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Admin extends User{
    public Admin(String firstName, String lastName, String login, String password, Boolean active, Address address, Cart cart, boolean suspended, Double accountBalance) {
        super(firstName, lastName, login, password, active, address, cart, suspended, accountBalance);
    }
}
