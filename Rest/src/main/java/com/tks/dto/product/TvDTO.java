package com.tks.dto.product;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TvDTO extends ProductDTO {
    @Size(min = 4, max = 10)
    private String screenSize;
    private String resolution;
    private String refreshRate;
    private String panelType;
}
