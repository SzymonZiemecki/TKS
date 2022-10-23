package com.pas.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import org.javamoney.moneta.Money;


@Getter
@Setter
@NoArgsConstructor
public abstract class Product<Money> {
    private UUID id;
    private int availableAmount;
    private Money price;
    private String name;
    private String producer;
    private String productDescription;

    public Product(int availableAmount, Money price, String name, String producer, String productDescription) {
        this.id = UUID.randomUUID();
        this.availableAmount = availableAmount;
        this.price = price;
        this.name = name;
        this.producer = producer;
        this.productDescription = productDescription;
    }
}
