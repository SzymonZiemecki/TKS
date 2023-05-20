package com.tks.microservices.productservice.repository.model;

import org.springframework.beans.BeanUtils;

import com.tks.microservices.productservice.core.model.Laptop;
import com.tks.microservices.productservice.core.model.MobilePhone;
import com.tks.microservices.productservice.core.model.Product;
import com.tks.microservices.productservice.core.model.Tv;
import com.tks.microservices.productservice.rest.dto.LaptopDTO;
import com.tks.microservices.productservice.rest.dto.MobilePhoneDTO;
import com.tks.microservices.productservice.rest.dto.ProductDTO;
import com.tks.microservices.productservice.rest.dto.TvDTO;

import jakarta.validation.constraints.Size;
import jakarta.ws.rs.NotSupportedException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
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

    @Override
    public ProductEnt clone() throws CloneNotSupportedException {
        return (ProductEnt) super.clone();
    }

    @SneakyThrows
    public static ProductEnt productToEnt(Product product) {
        ProductEnt productEnt;
        if(product instanceof Tv){
            productEnt = new TvEnt();
            copyTvProperties((Tv) product, (TvEnt) productEnt);
        }
        else if(product instanceof MobilePhone){
            productEnt = new MobilePhoneEnt();
            copyMobilePhoneProperties((MobilePhone) product, (MobilePhoneEnt) productEnt);
        }
        else if(product instanceof Laptop){
            productEnt = new LaptopEnt();
            copyLaptopProperties((Laptop) product, (LaptopEnt) productEnt);
        }
        else {
            throw new NotSupportedException();
        }
        copyProductProperties(product, productEnt);
        return productEnt;
    }

    @SneakyThrows
    public static Product productEntToDomainModel(ProductEnt productEnt) {
        Product product;
        if(productEnt instanceof  TvEnt){
            product = new Tv();
            copyTvEntProperties((Tv) product, (TvEnt) productEnt);
        }
        else if(productEnt instanceof MobilePhoneEnt){
            product = new MobilePhone();
            copyMobilePhoneDTOProperties((MobilePhone) product, (MobilePhoneEnt) productEnt);
        }
        else if(productEnt instanceof LaptopEnt){
            product = new Laptop();
            copyLaptopDTOProperties((Laptop) product, (LaptopEnt) productEnt);
        }
        else {
            throw new NotSupportedException();
        }
        copyProductDTOProperties(product, productEnt);
        return product;
    }

    private static void copyTvEntProperties(Tv product, TvEnt tvEnt) {
        product.setResolution(tvEnt.getResolution());
        product.setScreenSize(tvEnt.getScreenSize());
        product.setRefreshRate(tvEnt.getRefreshRate());
        product.setPanelType(tvEnt.getPanelType());
    }

    private static void copyTvProperties(Tv product, TvEnt tvEnt) {
        tvEnt.setResolution(product.getResolution());
        tvEnt.setScreenSize(product.getScreenSize());
        tvEnt.setRefreshRate(product.getRefreshRate());
        tvEnt.setPanelType(product.getPanelType());
    }

    private static void copyMobilePhoneDTOProperties(MobilePhone product, MobilePhoneEnt productDTO) {
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

    private static void copyMobilePhoneProperties(MobilePhone product, MobilePhoneEnt productDTO) {
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

    private static void copyLaptopDTOProperties(Laptop product, LaptopEnt productDTO) {
        product.setResolution(productDTO.getResolution());
        product.setScreenSize(productDTO.getScreenSize());
        product.setPanelType(productDTO.getPanelType());
        product.setCpu(productDTO.getCpu());
        product.setRamAmount(productDTO.getRamAmount());
        product.setBatterySize(productDTO.getBatterySize());
        product.setDiskSize(productDTO.getDiskSize());
    }

    private static void copyLaptopProperties(Laptop product, LaptopEnt productDTO) {
        productDTO.setResolution(product.getResolution());
        productDTO.setScreenSize(product.getScreenSize());
        productDTO.setPanelType(product.getPanelType());
        productDTO.setCpu(product.getCpu());
        productDTO.setRamAmount(product.getRamAmount());
        productDTO.setBatterySize(product.getBatterySize());
        productDTO.setDiskSize(product.getDiskSize());
    }

    private static void copyProductDTOProperties(Product product, ProductEnt productEnt) {
        product.setId(productEnt.getId());
        product.setName(productEnt.getName());
        product.setProductDescription(productEnt.getProductDescription());
        product.setPrice(productEnt.getPrice());
        product.setProducer(productEnt.getProducer());
        product.setAvailableAmount(productEnt.getAvailableAmount());
    }

    private static void copyProductProperties(Product product, ProductEnt productEnt) {
        productEnt.setId(product.getId());
        productEnt.setProductDescription(product.getProductDescription());
        productEnt.setName(product.getName());
        productEnt.setPrice(product.getPrice());
        productEnt.setProducer(product.getProducer());
        productEnt.setAvailableAmount(product.getAvailableAmount());
    }
}