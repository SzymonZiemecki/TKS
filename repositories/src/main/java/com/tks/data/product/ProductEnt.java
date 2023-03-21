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
        switch (product){
            case Tv tv -> productEnt = new TvEnt();
            case MobilePhone mobilePhone -> productEnt = new MobilePhoneEnt();
            case Laptop laptop -> productEnt = new LaptopEnt();
            default -> throw new IllegalStateException("Unexpected value: " + product);
        }
        BeanUtils.copyProperties(productEnt, product);

        return productEnt;
    }

    @SneakyThrows
    public static Product productEntToDomainModel(ProductEnt productEnt) {
        Product product;
        switch (productEnt){
            case TvEnt tvEnt -> product = new Tv();
            case MobilePhoneEnt mobilePhoneEnt -> product = new MobilePhone();
            case LaptopEnt laptopEnt -> product = new Laptop();
            default -> throw new IllegalStateException("Unexpected value: " + productEnt);
        }
        BeanUtils.copyProperties(product, productEnt);
        return product;
    }
}