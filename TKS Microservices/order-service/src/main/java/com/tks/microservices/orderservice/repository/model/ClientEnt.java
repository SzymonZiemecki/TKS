package com.tks.microservices.orderservice.repository.model;

import java.util.UUID;

import com.tks.microservices.orderservice.core.model.Cart;
import com.tks.microservices.orderservice.core.model.Client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ClientEnt extends IdTraitEnt {
    private UUID accountId;
    private Double accountBalance;
    private Cart cart;

    @SneakyThrows
    public static ClientEnt clientToEnt(Client client) {
        ClientEnt clientEnt = new ClientEnt();
        clientEnt.setId(client.getId());
        clientEnt.setCart(client.getCart());
        clientEnt.setAccountBalance(client.getAccountBalance());
        clientEnt.setAccountId(client.getAccountId());
        return clientEnt;
    }

    @SneakyThrows
    public static Client clientEntToDomainModel(ClientEnt clientEnt) {
        Client client = new Client();
        client.setAccountBalance(clientEnt.getAccountBalance());
        client.setCart(clientEnt.getCart());
        client.setAccountId(clientEnt.getAccountId());
        client.setId(clientEnt.getId());
        return client;
    }
}
