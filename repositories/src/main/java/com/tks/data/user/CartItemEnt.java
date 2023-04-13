package com.tks.data.user;

import com.tks.User.Admin;
import com.tks.User.BaseUser;
import com.tks.User.Manager;
import com.tks.User.User;
import com.tks.data.product.ProductEnt;
import com.tks.model.Cart;
import com.tks.model.CartItem;
import lombok.*;
import org.apache.commons.beanutils.BeanUtils;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class CartItemEnt {
    ProductEnt product;
    Long quantity;

    @SneakyThrows
    public static CartItemEnt cartItemToEnt(CartItem cartItem) {
        CartItemEnt cartItemEnt = new CartItemEnt();
        cartItemEnt.setProduct(ProductEnt.productToEnt(cartItem.getProduct()));
        cartItemEnt.setQuantity(cartItem.getQuantity());
        return cartItemEnt;
    }

    @SneakyThrows
    public static CartItem cartItemEntToDomainModel(CartItemEnt cartItemEnt) {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(ProductEnt.productEntToDomainModel(cartItemEnt.getProduct()));
        cartItem.setQuantity(cartItemEnt.getQuantity());
        return cartItem;
    }

}
