package com.tks.data.user;

import java.util.ArrayList;
import java.util.List;

import lombok.*;


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
}
