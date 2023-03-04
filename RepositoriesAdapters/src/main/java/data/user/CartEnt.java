package data.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.*;


@Getter
@Setter
@Data
@AllArgsConstructor
@Builder
public class CartEnt {

    private List<CartItemEnt> cartItems;

    public CartEnt() {
        this.cartItems = new ArrayList<>();
    }
}
