package com.tks.soap.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@XmlRootElement
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TvSoap extends ProductSoap {
    private String screenSize;
    private String resolution;
    private String refreshRate;
    private String panelType;
}
