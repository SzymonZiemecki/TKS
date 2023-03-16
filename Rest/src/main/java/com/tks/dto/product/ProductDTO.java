package com.tks.dto.product;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tks.IdTrait;
import jakarta.validation.constraints.Size;
import lombok.*;

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


}
