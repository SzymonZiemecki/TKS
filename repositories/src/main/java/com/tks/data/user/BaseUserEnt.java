package com.tks.data.user;

import java.util.UUID;

import com.tks.data.model.AddressEnt;
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
public class BaseUserEnt extends UserEnt {
    public BaseUserEnt(String firstName, String lastName, String login, String password, AddressEnt address, CartEnt cartEnt, boolean suspended, Double accountBalance) {
        super(firstName, lastName, login, password, address, cartEnt, suspended, accountBalance);
    }

    public BaseUserEnt(UUID id, String firstName, String lastName, String login, String password, AddressEnt address, boolean suspended, Double accountBalance) {
        super(id, firstName, lastName, login, password, address, suspended, accountBalance);
    }

    public BaseUserEnt(UserEnt user) {
        super(user);
    }
}
