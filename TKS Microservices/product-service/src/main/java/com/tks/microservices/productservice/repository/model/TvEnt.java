package com.tks.microservices.productservice.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TvEnt extends ProductEnt {
    private String screenSize;
    private String resolution;
    private String refreshRate;
    private String panelType;

    public TvEnt(int availableAmount, Double price, String name, String producer, String productDescription, String screenSize, String resolution, String refreshRate, String panelType) {
        super(availableAmount, price, name, producer, productDescription);
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.panelType = panelType;
    }
}
