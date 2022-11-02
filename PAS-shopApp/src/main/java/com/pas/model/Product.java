package com.pas.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import org.javamoney.moneta.Money;

@Getter
@Setter
@NoArgsConstructor
public abstract class Product extends IdTrait {
    private UUID id;
    private int availableAmount;
    private Money price;
    private String name;
    private String producer;
    private String productDescription;
}
