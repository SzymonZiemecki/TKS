package com.tks.microservices.orderservice.rest.dto;

import java.util.UUID;

import com.tks.microservices.orderservice.core.model.CartItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {

    UUID productId;
    Long quantity;

    @SneakyThrows
    public static CartItemDTO cartItemToDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setProductId(cartItem.getProductId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        return cartItemDTO;
    }

    @SneakyThrows
    public static CartItem cartItemDTOToDomainModel(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setProductId(cartItemDTO.getProductId());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        return cartItem;
    }

}
