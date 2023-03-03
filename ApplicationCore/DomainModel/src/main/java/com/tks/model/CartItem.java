package com.tks.model;

import com.tks.Product.Product;
import lombok.*;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    Product product;
    Long quantity;
}
