package com.tks.dto.product;

import com.tks.Product.Product;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MobilePhoneDTO extends ProductDTO {
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
