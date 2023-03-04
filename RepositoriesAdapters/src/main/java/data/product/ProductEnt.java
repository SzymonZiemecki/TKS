package data.product;

import com.tks.Product.Laptop;
import com.tks.Product.MobilePhone;
import com.tks.Product.Product;
import com.tks.Product.Tv;


import data.model.IdTraitEnt;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static data.product.LaptopEnt.toLaptopDomainModel;
import static data.product.LaptopEnt.toLaptopEnt;
import static data.product.MobilePhoneEnt.toMobilePhoneDomainModel;
import static data.product.MobilePhoneEnt.toMobilePhoneEnt;
import static data.product.TvEnt.toTvDomainModel;
import static data.product.TvEnt.toTvEnt;

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

    @SneakyThrows
    public static Product toProductDomainModel(ProductEnt product) {
        return switch (product) {
            case TvEnt tv -> toTvDomainModel((TvEnt) product);
            case LaptopEnt laptop -> toLaptopDomainModel((LaptopEnt) product);
            case MobilePhoneEnt mobilePhone -> toMobilePhoneDomainModel((MobilePhoneEnt) product);
            default -> throw new Exception();
        };
    }

    @SneakyThrows
    public static ProductEnt toProductEnt(Product product) {
        return switch (product) {
            case Tv tv -> toTvEnt((Tv) product);
            case Laptop laptop -> toLaptopEnt((Laptop) product);
            case MobilePhone mobilePhone -> toMobilePhoneEnt((MobilePhone) product);
            default -> throw new Exception();
        };
    }

    @Override
    public ProductEnt clone() throws CloneNotSupportedException {
        return (ProductEnt) super.clone();
    }
}