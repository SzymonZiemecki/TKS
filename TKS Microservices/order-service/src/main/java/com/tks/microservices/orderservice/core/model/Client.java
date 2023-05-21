package com.tks.microservices.orderservice.core.model;

import java.util.UUID;

import com.tks.microservices.orderservice.rest.dto.IdTrait;

import lombok.Data;

@Data
public class Client extends IdTrait {
    private UUID accountId;
    private Double accountBalance;
    private Cart cart;
}
