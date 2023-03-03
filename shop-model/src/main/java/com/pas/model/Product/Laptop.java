package com.pas.model.Product;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Laptop extends Product {
    private String cpu;
    private int ramAmount;
    private String screenSize;
    private String resolution;
    private int batterySize;
    private int diskSize;
    private String panelType;
    public Laptop(int availableAmount, Double price, String name, String producer, String productDescription, String cpu, int ramAmount, String screenSize, String resolution, int batterySize, int diskSize, String panelType) {
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
