package com.tks.soap.model;

import com.tks.Product.Laptop;
import com.tks.Product.MobilePhone;
import com.tks.Product.Product;
import com.tks.Product.Tv;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.NotSupportedException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clientSoap", propOrder = {
        "availableAmount",
        "price",
        "name",
        "producer",
        "productDescription"
})
public class ProductSoap {
    private int availableAmount;
    private Double price;
    @Size(min = 2, max = 20)
    private String name;
    @Size(min = 2, max = 20)
    private String producer;
    private String productDescription;


    @SneakyThrows
    public static ProductSoap productToSoap(Product product) {
        ProductSoap productSoap;
        if (product instanceof Tv) {
            productSoap = new TvSoap();
        } else if (product instanceof MobilePhone) {
            productSoap = new MobilePhoneSoap();
        } else if (product instanceof Laptop) {
            productSoap = new LaptopSoap();
        } else {
            throw new NotSupportedException();
        }
        BeanUtils.copyProperties(productSoap, product);

        return productSoap;
    }

    @SneakyThrows
    public static Product productSoapToDomainModel(ProductSoap productSoap) {
        Product product;
        if (productSoap instanceof TvSoap) {
            product = new Tv();

        } else if (productSoap instanceof MobilePhoneSoap) {
            product = new Tv();
        } else if (productSoap instanceof LaptopSoap) {
            product = new Laptop();
        } else {
            throw new NotSupportedException();
        }
        BeanUtils.copyProperties(product, productSoap);
        return product;
    }
}
