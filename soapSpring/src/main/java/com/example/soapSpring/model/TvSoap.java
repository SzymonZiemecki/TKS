package com.example.soapSpring.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tvSoap", propOrder = {
        "screenSize",
        "resolution",
        "refreshRate",
        "panelType"
})
public class TvSoap extends ProductSoap {
    private String screenSize;
    private String resolution;
    private String refreshRate;
    private String panelType;

    public TvSoap(final int availableAmount, final Double price, final String name, final String producer, final String productDescription,
                  final String screenSize,
                  final String resolution, final String refreshRate, final String panelType) {
        super(availableAmount, price, name, producer, productDescription);
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.panelType = panelType;
    }
}
