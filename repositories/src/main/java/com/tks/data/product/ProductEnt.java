package com.tks.data.product;

import com.tks.Product.Laptop;
import com.tks.Product.MobilePhone;
import com.tks.Product.Product;
import com.tks.Product.Tv;
import com.tks.User.Admin;
import com.tks.User.BaseUser;
import com.tks.User.Manager;
import com.tks.User.User;
import com.tks.data.model.IdTraitEnt;
import com.tks.data.user.AdminEnt;
import com.tks.data.user.BaseUserEnt;
import com.tks.data.user.ManagerEnt;
import com.tks.data.user.UserEnt;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.NotSupportedException;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.beanutils.BeanUtils;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class ProductEnt extends IdTraitEnt implements Cloneable {
    private int availableAmount;
    private Double price;
    @Size(min = 2, max = 20)
    private String name;
    @Size(min = 2, max = 20)
    private String producer;
    private String productDescription;

    public ProductEnt(int availableAmount, Double price, String name, String producer, String productDescription) {
        this.availableAmount = availableAmount;
        this.price = price;
        this.name = name;
        this.producer = producer;
        this.productDescription = productDescription;
    }

    @Override
    public ProductEnt clone() throws CloneNotSupportedException {
        return (ProductEnt) super.clone();
    }

    @SneakyThrows
    public static ProductEnt productToEnt(Product product) {
        ProductEnt productEnt;
        if(product instanceof  Tv){
            productEnt = new TvEnt();

        }
        else if(product instanceof  MobilePhone){
            productEnt = new TvEnt();
        }
        else if(product instanceof Laptop){
            productEnt = new LaptopEnt();
        }
        else {
            throw new NotSupportedException();
        }
        BeanUtils.copyProperties(productEnt, product);

        return productEnt;
    }

    @SneakyThrows
    public static Product productEntToDomainModel(ProductEnt productEnt) {
        Product product;
        if(productEnt instanceof  TvEnt){
            product = new Tv();

        }
        else if(productEnt instanceof  MobilePhoneEnt){
            product = new Tv();
        }
        else if(productEnt instanceof LaptopEnt){
            product = new Laptop();
        }
        else {
            throw new NotSupportedException();
        }
        BeanUtils.copyProperties(product, productEnt);
        return product;
    }
}