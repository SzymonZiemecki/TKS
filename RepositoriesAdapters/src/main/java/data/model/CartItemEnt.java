package data.model;

import data.product.ProductEnt;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemEnt {
    ProductEnt product;
    Long quantity;
}
