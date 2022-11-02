package com.pas.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Laptop extends Product {
    private String cpu;
    private int ramAmount;
    private String screenSize;
    private String resolution;
    private int batterySize;
    private int diskSize;
    private String panelType;
}
