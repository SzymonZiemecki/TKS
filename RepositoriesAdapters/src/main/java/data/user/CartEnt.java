package data.user;

import com.tks.model.Cart;
import com.tks.model.CartItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.*;

import static data.user.CartItemEnt.toCartItemEnt;

@Getter
@Setter
@Data
@AllArgsConstructor
@Builder
public class CartEnt {

    private List<CartItemEnt> cartItemEnts;

    public CartEnt() {
        this.cartItemEnts = new ArrayList<>();
    }

    public static CartEnt toCartEnt(Cart cart) {
        return CartEnt.builder()
                .cartItemEnts(toCartItemEntList(cart.getCartItems()))
                .build();
    }

    private static List<CartItemEnt> toCartItemEntList(List<CartItem> cartItemList) {
        return cartItemList.stream()
                .map(CartItemEnt::toCartItemEnt)
                .collect(Collectors.toList());
    }
}
