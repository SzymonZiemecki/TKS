package com.tks.soap.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@XmlRootElement
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaptopSoap extends ProductSoap {
    private String cpu;
    private int ramAmount;
    private String screenSize;
    private String resolution;
    private int batterySize;
    private int diskSize;
    private String panelType;

    public LaptopSoap(final int availableAmount, final Double price, final String name, final String producer, final String productDescription,
                      final String cpu,
                      final int ramAmount, final String screenSize, final String resolution, final int batterySize, final int diskSize,
                      final String panelType) {
        super(availableAmount, price, name, producer, productDescription);
        this.cpu = cpu;
        this.ramAmount = ramAmount;
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.batterySize = batterySize;
        this.diskSize = diskSize;
        this.panelType = panelType;
    }
}
