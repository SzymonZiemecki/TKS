package com.tks.microservices.productservice.rest.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
public class TvDTO extends ProductDTO {
    @Size(min = 4, max = 10)
    private String screenSize;
    private String resolution;
    private String refreshRate;
    private String panelType;

    public TvDTO(final ProductDTO dto, final String screenSize, final String resolution, final String refreshRate, final String panelType) {
        super(dto);
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.panelType = panelType;
    }
}
