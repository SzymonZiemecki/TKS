package com.tks.model;

import data.user.CartEnt;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static com.tks.model.CartItem.toCartItemDomainModel;
import static com.tks.model.CartItem.toListCartItemDomainModel;

@Getter
@Setter
@Data
@AllArgsConstructor
@Builder
public class Cart {

    private List<CartItem> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public static Cart toCartDomainModel(CartEnt cartEnt) {
        return Cart.builder()
                .cartItems(toListCartItemDomainModel(cartEnt.getCartItemEnts()))
                .build();
    }
}
