package com.tks.data.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.tks.model.Cart;
import com.tks.model.CartItem;
import lombok.*;
import org.apache.commons.beanutils.BeanUtils;


@Getter
@Setter
@Data
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class CartEnt {

    private List<CartItemEnt> cartItems;

    public CartEnt() {
        this.cartItems = new ArrayList<>();
    }

    @SneakyThrows
    public static CartEnt cartToEnt(Cart cart) {
        CartEnt cartEnt = new CartEnt();
        cartEnt.setCartItems(cart.getCartItems().stream().map(CartItemEnt::cartItemToEnt).collect(Collectors.toList()));
        return cartEnt;
    }

    @SneakyThrows
    public static Cart cartEntToDomainModel(CartEnt cartEnt) {
        Cart cart = new Cart();
        cart.setCartItems(cartEnt.getCartItems().stream().map(CartItemEnt::cartItemEntToDomainModel).collect(Collectors.toList()));
        return cart;
    }
}
