package com.tks.microservices.productservice.rest.dto;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tks.microservices.productservice.core.model.IdTrait;
import com.tks.microservices.productservice.core.model.Laptop;
import com.tks.microservices.productservice.core.model.MobilePhone;
import com.tks.microservices.productservice.core.model.Product;
import com.tks.microservices.productservice.core.model.Tv;

import jakarta.validation.constraints.Size;
import jakarta.ws.rs.NotSupportedException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TvDTO.class, name = "Tv"),
        @JsonSubTypes.Type(value = MobilePhoneDTO.class, name = "MobilePhone"),
        @JsonSubTypes.Type(value = LaptopDTO.class, name = "Laptop"),
})
public class ProductDTO extends IdTrait {
    private int availableAmount;
    private Double price;
    @Size(min = 2, max = 20)
    private String name;
    @Size(min = 2, max = 20)
    private String producer;
    private String productDescription;

    public ProductDTO(ProductDTO dto) {
        this.setId(dto.getId());
        this.availableAmount = dto.getAvailableAmount();
        this.price = dto.getPrice();
        this.name = dto.getName();
        this.producer = dto.getProducer();
        this.productDescription = dto.getProductDescription();
    }


    @SneakyThrows
    public static ProductDTO productToDTO(Product product) {
        ProductDTO productDTO;
        if(product instanceof Tv){
            productDTO = new TvDTO();
            copyTvProperties((Tv) product, (TvDTO) productDTO);
        }
        else if(product instanceof MobilePhone){
            productDTO = new MobilePhoneDTO();
            copyMobilePhoneProperties((MobilePhone) product, (MobilePhoneDTO) productDTO);
        }
        else if(product instanceof Laptop){
            productDTO = new LaptopDTO();
            copyLaptopProperties((Laptop) product, (LaptopDTO) productDTO);
        }
        else {
            throw new NotSupportedException();
        }
        copyProductProperties(product, productDTO);
        return productDTO;
    }

    @SneakyThrows
    public static Product productDTOToDomainModel(ProductDTO productDTO) {
        Product product;
        if(productDTO instanceof TvDTO){
            product = new Tv();
            copyTvDTOProperties((Tv) product, (TvDTO) productDTO);
        }
        else if(productDTO instanceof  MobilePhoneDTO){
            product = new Tv();
            copyMobilePhoneDTOProperties((MobilePhone) product, (MobilePhoneDTO) productDTO);
        }
        else if(productDTO instanceof LaptopDTO){
            product = new Laptop();
            copyLaptopDTOProperties((Laptop) product, (LaptopDTO) productDTO);
        }
        else {
            throw new NotSupportedException();
        }
        copyProductDTOProperties(product, productDTO);
        return product;
    }

    private static void copyTvDTOProperties(Tv product, TvDTO productDTO) {
        product.setResolution(productDTO.getResolution());
        product.setScreenSize(productDTO.getScreenSize());
        product.setRefreshRate(productDTO.getRefreshRate());
        product.setPanelType(productDTO.getPanelType());
    }

    private static void copyTvProperties(Tv product, TvDTO productDTO) {
        productDTO.setResolution(product.getResolution());
        productDTO.setScreenSize(product.getScreenSize());
        productDTO.setRefreshRate(product.getRefreshRate());
        productDTO.setPanelType(product.getPanelType());
    }

    private static void copyMobilePhoneDTOProperties(MobilePhone product, MobilePhoneDTO productDTO) {
        product.setResolution(productDTO.getResolution());
        product.setScreenSize(productDTO.getScreenSize());
        product.setPanelType(productDTO.getPanelType());
        product.setCpu(productDTO.getCpu());
        product.setRamAmount(productDTO.getRamAmount());
        product.setBatterySize(productDTO.getBatterySize());
        product.setMemorySize(productDTO.getMemorySize());
        product.setOperatingSystem(productDTO.getOperatingSystem());
        product.setNfcPresent(productDTO.isNfcPresent());
        product.setAudioJackPresent(productDTO.isAudioJackPresent());
    }

    private static void copyMobilePhoneProperties(MobilePhone product, MobilePhoneDTO productDTO) {
        productDTO.setResolution(product.getResolution());
        productDTO.setScreenSize(product.getScreenSize());
        productDTO.setPanelType(product.getPanelType());
        productDTO.setCpu(product.getCpu());
        productDTO.setRamAmount(product.getRamAmount());
        productDTO.setBatterySize(product.getBatterySize());
        productDTO.setMemorySize(product.getMemorySize());
        productDTO.setOperatingSystem(product.getOperatingSystem());
        productDTO.setNfcPresent(product.isNfcPresent());
        productDTO.setAudioJackPresent(product.isAudioJackPresent());
    }

    private static void copyLaptopDTOProperties(Laptop product, LaptopDTO productDTO) {
        product.setResolution(productDTO.getResolution());
        product.setScreenSize(productDTO.getScreenSize());
        product.setPanelType(productDTO.getPanelType());
        product.setCpu(productDTO.getCpu());
        product.setRamAmount(productDTO.getRamAmount());
        product.setBatterySize(productDTO.getBatterySize());
        product.setDiskSize(productDTO.getDiskSize());
    }

    private static void copyLaptopProperties(Laptop product, LaptopDTO productDTO) {
        productDTO.setResolution(product.getResolution());
        productDTO.setScreenSize(product.getScreenSize());
        productDTO.setPanelType(product.getPanelType());
        productDTO.setCpu(product.getCpu());
        productDTO.setRamAmount(product.getRamAmount());
        productDTO.setBatterySize(product.getBatterySize());
        productDTO.setDiskSize(product.getDiskSize());
    }

    private static void copyProductDTOProperties(Product product, ProductDTO productDTO) {
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setProductDescription(productDTO.getProductDescription());
        product.setPrice(productDTO.getPrice());
        product.setProducer(productDTO.getProducer());
        product.setAvailableAmount(productDTO.getAvailableAmount());
    }

    private static void copyProductProperties(Product product, ProductDTO productDTO) {
        productDTO.setId(product.getId());
        productDTO.setProductDescription(product.getProductDescription());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setProducer(product.getProducer());
        productDTO.setAvailableAmount(product.getAvailableAmount());
    }
}
