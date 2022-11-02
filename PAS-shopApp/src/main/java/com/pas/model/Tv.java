package com.pas.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Tv extends Product {
    private String screenSize;
    private String resolution;
    private String refreshRate;
    private String panelType;
}
