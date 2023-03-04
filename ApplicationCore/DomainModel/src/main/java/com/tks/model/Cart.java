package com.tks.model;

import data.user.CartEnt;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

    private List<CartItem> cartItems;
}
