package com.pas.model.Product;

import com.pas.model.IdTrait;
import lombok.*;

import java.util.UUID;

import lombok.experimental.SuperBuilder;
import org.javamoney.moneta.Money;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class Product extends IdTrait {
    private int availableAmount;
    private Double price;
    private String name;
    private String producer;
    private String productDescription;

    public Product(int availableAmount, Double price, String name, String producer, String productDescription) {
        this.availableAmount = availableAmount;
        this.price = price;
        this.name = name;
        this.producer = producer;
        this.productDescription = productDescription;
    }
}
