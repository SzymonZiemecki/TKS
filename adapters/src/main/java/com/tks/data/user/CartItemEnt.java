package com.tks.data.user;

import com.tks.data.product.ProductEnt;
import lombok.*;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemEnt {
    ProductEnt product;
    Long quantity;

}
