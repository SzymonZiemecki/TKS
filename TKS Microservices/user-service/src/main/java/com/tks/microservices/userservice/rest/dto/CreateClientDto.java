package com.tks.microservices.userservice.rest.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CreateClientDto {
    private UUID accountId;
    private Double accountBalance;
}
