package com.tks.model;

import lombok.*;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Cart {

    private List<CartItem> cartItems;
}
