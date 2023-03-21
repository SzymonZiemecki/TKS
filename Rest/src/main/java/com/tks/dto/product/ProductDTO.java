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
import lombok.*;
import org.apache.commons.beanutils.BeanUtils;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
        switch (product){
            case Tv tv -> productDTO = new TvDTO();
            case MobilePhone mobilePhone -> productDTO = new MobilePhoneDTO();
            case Laptop laptop -> productDTO = new LaptopDTO();
            default -> throw new IllegalStateException("Unexpected value: " + product);
        }
        BeanUtils.copyProperties(productDTO, product);

        return productDTO;
    }

    @SneakyThrows
    public static Product productDTOToDomainModel(ProductDTO productDTO) {
        Product product;
        switch (productDTO){
            case TvDTO tvDTO -> product = new Tv();
            case MobilePhoneDTO mobilePhoneDTO -> product = new MobilePhone();
            case LaptopDTO laptopDTO -> product = new Laptop();
            default -> throw new IllegalStateException("Unexpected value: " + productDTO);
        }
        BeanUtils.copyProperties(product, productDTO);
        return product;
    }

}
