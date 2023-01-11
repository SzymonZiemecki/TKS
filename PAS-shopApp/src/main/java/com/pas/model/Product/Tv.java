package com.pas.model.Product;

import com.fasterxml.jackson.annotation.*;
import com.pas.model.Product.Product;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Tv extends Product {
    @JsonProperty
    private String screenSize;
    @JsonProperty
    private String resolution;
    @JsonProperty
    private String refreshRate;
    @JsonProperty
    private String panelType;
    public Tv(int availableAmount, Double price, String name, String producer, String productDescription, String screenSize, String resolution, String refreshRate, String panelType) {
        super(availableAmount, price, name, producer, productDescription);
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.panelType = panelType;
    }
}
