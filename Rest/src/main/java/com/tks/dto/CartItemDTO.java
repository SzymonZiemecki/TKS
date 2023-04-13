package com.tks.dto;

import com.tks.data.product.ProductEnt;
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
        cartItemDTO.setProduct(ProductDTO.productToDTO(cartItem.getProduct()));
        cartItemDTO.setQuantity(cartItem.getQuantity());
        return cartItemDTO;
    }

    @SneakyThrows
    public static CartItem cartItemDTOToDomainModel(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(ProductDTO.productDTOToDomainModel(cartItemDTO.getProduct()));
        cartItem.setQuantity(cartItemDTO.getQuantity());
        return cartItem;
    }

}
