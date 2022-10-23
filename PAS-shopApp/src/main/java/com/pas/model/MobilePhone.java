package com.pas.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MobilePhone extends Product {
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

    public MobilePhone(int availableAmount, Money price, String name, String producer, String productDescription, String cpu, int ramAmount, String screenSize, String resolution, int batterySize, int memorySize, String panelType, String operatingSystem, boolean nfcPresent, boolean audioJackPresent) {
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
