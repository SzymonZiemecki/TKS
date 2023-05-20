package com.tks.microservices.productservice.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class LaptopDTO extends ProductDTO {
    private String cpu;
    private int ramAmount;
    private String screenSize;
    private String resolution;
    private int batterySize;
    private int diskSize;
    private String panelType;

    public LaptopDTO(final ProductDTO productDTO, final String cpu, final int ramAmount, final String screenSize, final String resolution,
                     final int batterySize, final int diskSize, final String panelType) {
        super(productDTO);
        this.cpu = cpu;
        this.ramAmount = ramAmount;
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.batterySize = batterySize;
        this.diskSize = diskSize;
        this.panelType = panelType;
    }
}
