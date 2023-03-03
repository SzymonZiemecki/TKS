package data.user;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class CartEnt {

    private List<CartItemEnt> cartItemEnts;

    public CartEnt() {
        this.cartItemEnts = new ArrayList<>();
    }
}
