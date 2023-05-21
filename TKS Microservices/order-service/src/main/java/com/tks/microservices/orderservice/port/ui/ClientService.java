package com.tks.microservices.orderservice.port.ui;

import java.util.List;
import java.util.UUID;

import com.tks.microservices.orderservice.core.model.Address;
import com.tks.microservices.orderservice.core.model.CartItem;
import com.tks.microservices.orderservice.core.model.Client;
import com.tks.microservices.orderservice.core.model.Product;
import com.tks.microservices.orderservice.rest.dto.CreateClientDto;

public interface ClientService {

    List<Client> getAllClients();

    Client getClientById(UUID id);

    Client createClient(CreateClientDto dto);

    void addToClientCart(String clientId, String productId, int quantity);

    void removeFromClientCart(UUID clientId, UUID productID);
}
