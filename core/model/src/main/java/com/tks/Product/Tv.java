package com.tks.Product;

import lombok.*;
import lombok.experimental.SuperBuilder;
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Tv extends Product {
    private String screenSize;
    private String resolution;
    private String refreshRate;
    private String panelType;
    public Tv(int availableAmount, Double price, String name, String producer, String productDescription, String screenSize, String resolution, String refreshRate, String panelType) {
        super(availableAmount, price, name, producer, productDescription);
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.panelType = panelType;
    }
}
