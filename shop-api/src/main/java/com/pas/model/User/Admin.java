package com.pas.model.User;

import com.pas.model.Address;
import com.pas.model.Product.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Map;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {
    public Admin(String firstName, String lastName, String login, String password, Address address, Cart cart, boolean suspended, Double accountBalance) {
        super(firstName, lastName, login, password, address, cart, suspended, accountBalance);
    }
    public Admin(User user) {
        super(user);
    }
}
