package com.tks.User;


import com.tks.model.Address;
import com.tks.model.Cart;

import data.user.AdminEnt;
import data.user.ManagerEnt;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static com.tks.model.Address.toAddressDomainModel;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {
    public Admin(String firstName, String lastName, String login, String password, Address address, Cart cart, boolean suspended, Double accountBalance) {
        super(firstName, lastName, login, password, address, cart, suspended, accountBalance);
    }

    public Admin(UUID id, String firstName, String lastName, String login, String password, Address address, boolean suspended, Double accountBalance) {
        super(id, firstName, lastName, login, password, address, suspended, accountBalance);
    }
    public Admin(User user) {
        super(user);
    }

    public static Admin toAdminDomainModel(AdminEnt adminEnt) {
        return Admin.builder()
                .address(toAddressDomainModel(adminEnt.getAddress()))
                .accountBalance(adminEnt.getAccountBalance())
                .cart(Cart.toCartDomainModel(adminEnt.getCart()))
                .firstName(adminEnt.getFirstName())
                .lastName(adminEnt.getLastName())
                .login(adminEnt.getLogin())
                .suspended(adminEnt.isSuspended())
                .password(adminEnt.getPassword())
                .build();
    }
}
