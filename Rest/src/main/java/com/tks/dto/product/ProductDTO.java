package com.tks.dto.product;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tks.IdTrait;
import com.tks.Product.Laptop;
import com.tks.Product.MobilePhone;
import com.tks.Product.Product;
import com.tks.Product.Tv;
import com.tks.data.product.LaptopEnt;
import com.tks.data.product.MobilePhoneEnt;
import com.tks.data.product.ProductEnt;
import com.tks.data.product.TvEnt;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.NotSupportedException;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.beanutils.BeanUtils;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TvDTO.class, name = "Tv"),
        @JsonSubTypes.Type(value = MobilePhoneDTO.class, name = "MobilePhone"),
        @JsonSubTypes.Type(value = LaptopDTO.class, name = "Laptop"),
})
public abstract class ProductDTO extends IdTrait {
    private int availableAmount;
    private Double price;
    @Size(min = 2, max = 20)
    private String name;
    @Size(min = 2, max = 20)
    private String producer;
    private String productDescription;


    @SneakyThrows
    public static ProductDTO productToDTO(Product product) {
        ProductDTO productDTO;
        if(product instanceof  Tv){
            productDTO = new TvDTO();
        }
        else if(product instanceof  MobilePhone){
            productDTO = new TvDTO();
        }
        else if(product instanceof Laptop){
            productDTO = new LaptopDTO();
        }
        else {
            throw new NotSupportedException();
        }
        BeanUtils.copyProperties(productDTO, product);

        return productDTO;
    }

    @SneakyThrows
    public static Product productDTOToDomainModel(ProductDTO productDTO) {
        Product product;
        if(productDTO instanceof TvDTO){
            product = new Tv();

        }
        else if(productDTO instanceof  MobilePhoneDTO){
            product = new Tv();
        }
        else if(productDTO instanceof LaptopDTO){
            product = new Laptop();
        }
        else {
            throw new NotSupportedException();
        }
        BeanUtils.copyProperties(product, productDTO);
        return product;
    }

}
