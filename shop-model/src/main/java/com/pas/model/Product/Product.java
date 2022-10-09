package com.pas.model.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.pas.model.IdTrait;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Tv.class, name = "Tv"),
        @JsonSubTypes.Type(value = MobilePhone.class, name = "MobilePhone"),
        @JsonSubTypes.Type(value = Laptop.class, name = "Laptop"),
})
public abstract class Product extends IdTrait implements Cloneable{
    @JsonProperty
    private int availableAmount;
    @JsonProperty
    private Double price;
    @JsonProperty
    @Size(min = 2, max = 20)
    private String name;
    @JsonProperty
    @Size(min = 2, max = 20)
    private String producer;
    @JsonProperty
    private String productDescription;

    public Product(int availableAmount, Double price, String name, String producer, String productDescription) {
        this.availableAmount = availableAmount;
        this.price = price;
        this.name = name;
        this.producer = producer;
        this.productDescription = productDescription;
    }

    @Override
    public Product clone() throws CloneNotSupportedException {
        return (Product) super.clone();
    }

    public int getAvailableAmount() {
        return this.availableAmount;
    }

    public Double getPrice() {
        return this.price;
    }

    public @Size(min = 2, max = 20) String getName() {
        return this.name;
    }

    public @Size(min = 2, max = 20) String getProducer() {
        return this.producer;
    }

    public String getProductDescription() {
        return this.productDescription;
    }
}
