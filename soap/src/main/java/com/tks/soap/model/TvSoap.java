package com.tks.soap.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
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
}
