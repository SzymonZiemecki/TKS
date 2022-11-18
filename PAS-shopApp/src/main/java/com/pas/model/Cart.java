package com.pas.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pas.model.Product.Product;
import com.pas.utils.ItemMapDeserializer;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class Cart{
    @JsonDeserialize(keyUsing = ItemMapDeserializer.class)
    private Map<Product, Long> items;

    public Cart() {
        this.items = new HashMap<>();
    }
}
