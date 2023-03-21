package com.tks.data.user;

import java.util.ArrayList;
import java.util.List;

import com.tks.model.Cart;
import com.tks.model.CartItem;
import lombok.*;
import org.apache.commons.beanutils.BeanUtils;


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

    @SneakyThrows
    public static CartEnt cartToEnt(Cart cart) {
        CartEnt cartEnt = new CartEnt();
        BeanUtils.copyProperties(cartEnt, cart);

        return cartEnt;
    }

    @SneakyThrows
    public static Cart cartEntToDomainModel(CartEnt cartEnt) {
        Cart cart = new Cart();

        BeanUtils.copyProperties(cart, cartEnt);
        return cart;
    }
}
