package com.pas.controller.Product;

import com.pas.model.Product.Laptop;
import com.pas.model.Product.MobilePhone;
import com.pas.model.Product.Product;
import com.pas.model.Product.Tv;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ApplicationScoped
public class CommonProductController implements Serializable {
    public Product changeProductType(String productType){
        if(productType.equals("MobilePhone")){
            MobilePhone prod = new MobilePhone();
            return prod;
        } else if (productType.equals("Laptop")){
            Laptop prod = new Laptop();
            return prod;
        } else {
            Tv prod = new Tv();
            return prod;
        }
    }
}
