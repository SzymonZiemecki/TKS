package com.tks.model;

import lombok.*;
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
