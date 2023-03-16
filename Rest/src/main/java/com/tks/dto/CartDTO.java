package com.tks.dto;

import java.util.List;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {

    List<CartItemDTO> cartItems;
}
