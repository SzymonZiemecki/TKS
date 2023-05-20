package com.tks.microservices.productservice.rest.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MobilePhoneDTO extends ProductDTO {
    private String cpu;
    private int ramAmount;
    private String screenSize;
    private String resolution;
    private int batterySize;
    private int memorySize;
    private String panelType;
    private String operatingSystem;
    private boolean nfcPresent;
    private boolean audioJackPresent;

    public MobilePhoneDTO(final ProductDTO dto, final String cpu, final int ramAmount,
                          final String screenSize, final String resolution, final int batterySize, final int memorySize, final String panelType,
                          final String operatingSystem, final boolean nfcPresent,
                          final boolean audioJackPresent) {
        super(dto);
        this.cpu = cpu;
        this.ramAmount = ramAmount;
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.batterySize = batterySize;
        this.memorySize = memorySize;
        this.panelType = panelType;
        this.operatingSystem = operatingSystem;
        this.nfcPresent = nfcPresent;
        this.audioJackPresent = audioJackPresent;
    }
}
