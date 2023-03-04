package com.tks.User;


import com.tks.model.Address;
import com.tks.model.Cart;

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
public class Manager extends User {
    public Manager(String firstName, String lastName, String login, String password, Address address, Cart cart, boolean suspended, Double accountBalance) {
        super(firstName, lastName, login, password, address, cart, suspended, accountBalance);
    }

    public Manager(UUID id, String firstName, String lastName, String login, String password, Address address,boolean suspended, Double accountBalance) {
        super(id, firstName, lastName, login, password, address,suspended, accountBalance);
    }

    public Manager(User user) {
        super(user);
    }

    public static Manager toManagerDomainModel(ManagerEnt managerEnt) {
        return Manager.builder()
                .address(toAddressDomainModel(managerEnt.getAddress()))
                .accountBalance(managerEnt.getAccountBalance())
                .cart(Cart.toCartDomainModel(managerEnt.getCart()))
                .firstName(managerEnt.getFirstName())
                .lastName(managerEnt.getLastName())
                .login(managerEnt.getLogin())
                .suspended(managerEnt.isSuspended())
                .password(managerEnt.getPassword())
                .build();
    }
}
