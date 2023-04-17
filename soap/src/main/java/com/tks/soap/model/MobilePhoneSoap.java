package com.tks.soap.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@XmlRootElement
@Data
@Getter
@Setter
@NoArgsConstructor
public class MobilePhoneSoap extends ProductSoap {
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

    public MobilePhoneSoap(final int availableAmount, final Double price, final String name, final String producer, final String productDescription,
                           final String cpu,
                           final int ramAmount, final String screenSize, final String resolution, final int batterySize, final int memorySize,
                           final String panelType, final String operatingSystem,
                           final boolean nfcPresent, final boolean audioJackPresent) {
        super(availableAmount, price, name, producer, productDescription);
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
