package com.pas.model;

import com.pas.model.Product.Product;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class Cart{
    private Map<Product, Long> items;

    public Cart() {
        this.items = new HashMap<>();
    }
}
