package com.tks.dto;

import com.tks.dto.product.ProductDTO;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {

    ProductDTO product;
    long quantity;
}
