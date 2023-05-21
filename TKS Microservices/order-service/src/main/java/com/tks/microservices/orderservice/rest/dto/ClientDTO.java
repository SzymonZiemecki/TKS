package com.tks.microservices.orderservice.rest.dto;

import java.util.UUID;

import com.tks.microservices.orderservice.core.model.Cart;
import com.tks.microservices.orderservice.core.model.Client;
import com.tks.microservices.orderservice.repository.model.IdTraitEnt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO extends IdTrait {
    private UUID accountId;
    private Double accountBalance;
    private Cart cart;

    @SneakyThrows
    public static ClientDTO clientToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setCart(client.getCart());
        clientDTO.setAccountId(client.getAccountId());
        clientDTO.setAccountBalance(client.getAccountBalance());
        return clientDTO;
    }

    @SneakyThrows
    public static Client clientDtoToDomainModel(ClientDTO clientDTO) {
        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setCart(clientDTO.getCart());
        client.setAccountId(clientDTO.getAccountId());
        client.setAccountBalance(clientDTO.getAccountBalance());
        return client;
    }
}
