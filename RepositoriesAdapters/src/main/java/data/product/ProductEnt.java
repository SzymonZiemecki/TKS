package data.product;

import com.tks.Product.Laptop;
import com.tks.Product.MobilePhone;
import com.tks.Product.Product;
import com.tks.Product.Tv;


import data.model.IdTraitEnt;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    public static Product ToProductDomainModel(ProductEnt product) {
        return switch (product) {
            case TvEnt tv -> TvEnt.toTvDomainModel((TvEnt) product);
            case LaptopEnt laptop -> LaptopEnt.toLaptopDomainModel((LaptopEnt) product);
            case MobilePhoneEnt mobilePhone -> MobilePhoneEnt.toMobilePhoneDomainModel((MobilePhoneEnt) product);
            default -> throw new Exception();
        };
    }

    @SneakyThrows
    public static ProductEnt ToProductEnt(Product product) {
        return switch (product) {
            case Tv tv -> TvEnt.toTvEnt((Tv) product);
            case Laptop laptop -> LaptopEnt.toLaptopEnt((Laptop) product);
            case MobilePhone mobilePhone -> MobilePhoneEnt.toMobilePhoneEnt((MobilePhone) product);
            default -> throw new Exception();
        };
    }
    @Override
    public ProductEnt clone() throws CloneNotSupportedException {
        return (ProductEnt) super.clone();
    }
}