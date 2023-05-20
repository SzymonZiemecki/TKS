package com.tks.microservices.userservice.core.model;


import java.util.UUID;

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
public class Manager extends User {
    public Manager(String firstName, String lastName, String login, String password, Address address, boolean suspended, Double accountBalance) {
        super(firstName, lastName, login, password, address, suspended, accountBalance);
    }

    public Manager(UUID id, String firstName, String lastName, String login, String password, Address address,boolean suspended, Double accountBalance) {
        super(id, firstName, lastName, login, password, address,suspended, accountBalance);
    }

    public Manager(User user) {
        super(user);
    }

}
