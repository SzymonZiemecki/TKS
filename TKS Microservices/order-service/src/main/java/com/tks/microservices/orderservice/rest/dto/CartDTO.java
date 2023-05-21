package com.tks.microservices.orderservice.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.tks.microservices.orderservice.core.model.Cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

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
