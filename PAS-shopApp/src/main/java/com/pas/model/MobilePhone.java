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
}
