package com.tks.dto;

import com.tks.data.user.CartItemEnt;
import com.tks.dto.product.ProductDTO;
import com.tks.model.CartItem;
import lombok.*;
import org.apache.commons.beanutils.BeanUtils;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {

    ProductDTO product;
    Long quantity;

    @SneakyThrows
    public static CartItemDTO cartItemToDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        BeanUtils.copyProperties(cartItemDTO, cartItem);

        return cartItemDTO;
    }

    @SneakyThrows
    public static CartItem cartItemDTOToDomainModel(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();

        BeanUtils.copyProperties(cartItem, cartItemDTO);
        return cartItem;
    }

}
