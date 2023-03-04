package data.user;

import java.util.UUID;

import data.model.AddressEnt;
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
public class AdminEnt extends UserEnt {
    public AdminEnt(String firstName, String lastName, String login, String password, AddressEnt address, CartEnt cartEnt, boolean suspended, Double accountBalance) {
        super(firstName, lastName, login, password, address, cartEnt, suspended, accountBalance);
    }

    public AdminEnt(UUID id, String firstName, String lastName, String login, String password, AddressEnt address, boolean suspended, Double accountBalance) {
        super(id, firstName, lastName, login, password, address, suspended, accountBalance);
    }
    public AdminEnt(UserEnt user) {
        super(user);
    }
}
