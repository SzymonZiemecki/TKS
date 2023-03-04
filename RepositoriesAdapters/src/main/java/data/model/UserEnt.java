package data.model;

import com.tks.User.User;

import java.util.UUID;

import data.user.CartEnt;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class UserEnt extends IdTraitEnt {
    @Size(min = 2, max = 20)
    private String firstName;
    @Size(min = 2, max = 20)
    private String lastName;
    @Size(min = 2, max = 20)
    private String login;
    @Size(min = 2, max = 20)
    private String password;
    private AddressEnt address;
    private CartEnt cart;
    private boolean suspended;
    private Double accountBalance;

    public UserEnt(String firstName, String lastName, String login, String password, AddressEnt address, CartEnt cart, boolean suspended, Double accountBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
        this.cart = cart;
        this.suspended = suspended;
        this.accountBalance = accountBalance;
    }

    public UserEnt(UUID id, String firstName, String lastName, String login, String password, AddressEnt address, boolean suspended, Double accountBalance) {
        this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
        this.cart = new CartEnt();
        this.suspended = suspended;
        this.accountBalance = accountBalance;
    }

    public UserEnt(String firstName, String lastName, String login, String password, AddressEnt address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
    }

    public UserEnt() {
        this.address=new AddressEnt();
    }

    @Override
    public UserEnt clone() throws CloneNotSupportedException {
        return (UserEnt) super.clone();
    }

    public UserEnt(UserEnt user) {
        this.setId(user.getId());
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.login = user.login;
        this.password = user.password;
        this.address = user.address;
        this.cart = user.cart;
        this.suspended = user.suspended;
        this.accountBalance = user.accountBalance;
    }

    public static UserEnt toUserEnt(User user) {
        UserEnt.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .password(user.getPassword())
                .address(AddressEnt.toAddressEnt(user.getAddress()))
                .cart(user.getCart())
    }
}