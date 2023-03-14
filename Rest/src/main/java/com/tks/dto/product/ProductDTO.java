package com.tks.dto.product;

import com.tks.IdTrait;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ProductDTO extends IdTrait {
    private int availableAmount;
    private Double price;
    @Size(min = 2, max = 20)
    private String name;
    @Size(min = 2, max = 20)
    private String producer;
    private String productDescription;
}
