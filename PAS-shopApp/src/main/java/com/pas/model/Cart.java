package com.pas.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Cart {
    private List<Product> items;
    public void removeItem(Product item){
        this.items.remove(item);
    }

}
