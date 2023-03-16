package com.tks.dto;

import com.tks.model.Cart;
import lombok.*;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;

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
        BeanUtils.copyProperties(cartDTO, cart);

        return cartDTO;
    }

    @SneakyThrows
    public static Cart cartDTOToDomainModel(CartDTO cartDTO) {
        Cart cart = new Cart();

        BeanUtils.copyProperties(cart, cartDTO);
        return cart;
    }

}
