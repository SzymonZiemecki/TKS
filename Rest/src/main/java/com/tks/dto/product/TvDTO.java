package com.tks.dto.product;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TvDTO extends ProductDTO {
    private String screenSize;
    private String resolution;
    private String refreshRate;
    private String panelType;
}
