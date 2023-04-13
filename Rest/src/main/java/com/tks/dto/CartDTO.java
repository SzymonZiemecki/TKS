package com.tks.dto;

import com.tks.data.user.CartItemEnt;
import com.tks.model.Cart;
import lombok.*;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Data
@AllArgsConstructor
@Builder
public class CartDTO {

    private List<CartItemDTO> cartItems;

    public CartDTO() {
        this.cartItems = new ArrayList<>();
    }

    @SneakyThrows
    public static CartDTO cartToDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartItems(cart.getCartItems().stream().map(CartItemDTO::cartItemToDTO).collect(Collectors.toList()));
        return cartDTO;
    }

    @SneakyThrows
    public static Cart cartDTOToDomainModel(CartDTO cartDTO) {
        Cart cart = new Cart();
        cart.setCartItems(cartDTO.getCartItems().stream().map(CartItemDTO::cartItemDTOToDomainModel).collect(Collectors.toList()));

        return cart;
    }

}
