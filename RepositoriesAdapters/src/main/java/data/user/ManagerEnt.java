package data.user;

import java.util.UUID;

import data.model.AddressEnt;
import data.model.UserEnt;
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
public class ManagerEnt extends UserEnt {
    public ManagerEnt(String firstName, String lastName, String login, String password, AddressEnt address, CartEnt cartEnt, boolean suspended, Double accountBalance) {
        super(firstName, lastName, login, password, address, cartEnt, suspended, accountBalance);
    }

    public ManagerEnt(UUID id, String firstName, String lastName, String login, String password, AddressEnt address, boolean suspended, Double accountBalance) {
        super(id, firstName, lastName, login, password, address,suspended, accountBalance);
    }

    public ManagerEnt(UserEnt user) {
        super(user);
    }
}
