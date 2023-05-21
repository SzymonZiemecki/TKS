package com.tks.microservices.orderservice.core.model;

import com.tks.microservices.orderservice.rest.dto.IdTrait;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Product extends IdTrait {
    private Double price;
    private Long availableAmount;
}
