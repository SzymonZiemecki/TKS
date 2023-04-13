package com.tks.soap.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@XmlRootElement
@Data
@Getter
@Setter
@AllArgsConstructor
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
}
