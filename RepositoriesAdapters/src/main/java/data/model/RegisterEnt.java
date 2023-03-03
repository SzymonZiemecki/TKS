package data.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterEnt {
    @Size(min = 2, max = 20)
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private AddressEnt address;
    private Double accountBalance;
    private boolean suspended;
    private String role;

    public RegisterEnt() {
        this.address = new AddressEnt();
    }
}
