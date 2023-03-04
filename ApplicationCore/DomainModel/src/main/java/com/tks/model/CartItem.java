package com.tks.model;

import com.tks.Product.Product;

import java.util.ArrayList;
import java.util.List;

import data.user.CartItemEnt;
import lombok.*;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    Product product;
    Long quantity;
}
