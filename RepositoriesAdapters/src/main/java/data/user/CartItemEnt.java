package data.user;

import com.tks.model.CartItem;

import data.product.ProductEnt;
import lombok.*;

import static data.product.ProductEnt.ToProductEnt;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemEnt {
    ProductEnt product;
    Long quantity;

    public static CartItemEnt toCartItemEnt(CartItem cartItem) {
        return CartItemEnt.builder()
                .quantity(cartItem.getQuantity())
                .product(ToProductEnt(cartItem.getProduct()))
                .build();
    }
}
