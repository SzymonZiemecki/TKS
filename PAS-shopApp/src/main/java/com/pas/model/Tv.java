package com.pas.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Tv extends Product {
    private String screenSize;
    private String resolution;
    private String refreshRate;
    private String panelType;

    public Tv(int availableAmount, Money price, String name, String producer, String productDescription, String screenSize, String resolution, String refreshRate, String panelType) {
        super(availableAmount, price, name, producer, productDescription);
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.panelType = panelType;
    }

}
