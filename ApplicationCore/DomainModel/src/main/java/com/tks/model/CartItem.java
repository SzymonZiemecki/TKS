package com.tks.model;

import com.tks.Product.Product;

import java.util.ArrayList;
import java.util.List;

import data.user.CartItemEnt;
import lombok.*;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    Product product;
    Long quantity;

    public static CartItem toCartItemDomainModel(CartItemEnt cartItemEnt) {
        return CartItem.builder()
                .product(toProductDomainModel(cartItemEnt.getProduct()))
                .quantity(cartItemEnt.getQuantity())
                .build();
    }

    public static List<CartItem> toListCartItemDomainModel(List<CartItemEnt> cartItemEntList) {
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemEntList
                .forEach(ent -> cartItemList.add(toCartItemDomainModel(ent)));
        return cartItemList;
    }
}
