package com.pas.model.User;

import com.pas.model.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseUser extends User {
    public BaseUser(String firstName, String lastName, String login, String password, Address address, Cart cart, boolean suspended, Double accountBalance) {
        super(firstName, lastName, login, password, address, cart, suspended, accountBalance);
    }

    public BaseUser(UUID id, String firstName, String lastName, String login, String password, Address address, boolean suspended, Double accountBalance) {
        super(id, firstName, lastName, login, password, address, suspended, accountBalance);
    }

    public BaseUser(User user) {
        super(user);
    }
}
