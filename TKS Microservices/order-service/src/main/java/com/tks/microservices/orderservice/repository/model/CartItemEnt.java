package com.tks.microservices.orderservice.repository.model;

import java.util.UUID;

import com.tks.microservices.orderservice.core.model.CartItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class CartItemEnt {
    UUID productId;
    Long quantity;

    @SneakyThrows
    public static CartItemEnt cartItemToEnt(CartItem cartItem) {
        CartItemEnt cartItemEnt = new CartItemEnt();
        cartItemEnt.setQuantity(cartItem.getQuantity());
        return cartItemEnt;
    }

    @SneakyThrows
    public static CartItem cartItemEntToDomainModel(CartItemEnt cartItemEnt) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartItemEnt.getQuantity());
        return cartItem;
    }

}
