package com.pas.model.User;

import com.pas.model.Product.Product;
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
