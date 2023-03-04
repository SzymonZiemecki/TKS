package data.user;

import data.product.ProductEnt;
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
