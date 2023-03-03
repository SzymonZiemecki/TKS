package com.tks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
public class Cart {

    private List<CartItem> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }
}
