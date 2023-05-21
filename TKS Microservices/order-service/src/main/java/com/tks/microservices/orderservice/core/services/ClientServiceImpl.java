package com.tks.microservices.orderservice.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.discovery.converters.Auto;
import com.tks.microservices.orderservice.core.model.Address;
import com.tks.microservices.orderservice.core.model.Cart;
import com.tks.microservices.orderservice.core.model.CartItem;
import com.tks.microservices.orderservice.core.model.Client;
import com.tks.microservices.orderservice.port.security.ClientRepositoryPort;
import com.tks.microservices.orderservice.port.ui.ClientService;
import com.tks.microservices.orderservice.rest.dto.CreateClientDto;

import jakarta.ws.rs.NotFoundException;

@Component
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepositoryPort clientRepositoryPort;

    @Override
    public List<Client> getAllClients() {
        return clientRepositoryPort.findAll();
    }

    @Override
    public Client getClientById(final UUID id) {
        return clientRepositoryPort.findById(id);
    }

    @Override
    public Client createClient(CreateClientDto dto) {
        Client client = new Client();
        client.setCart(new Cart(new ArrayList<>()));
        client.setAccountId(dto.getAccountId());
        client.setAccountBalance(dto.getAccountBalance());
        return clientRepositoryPort.add(client);
    }

    @Override
    public void addToClientCart(final String clientId, final String productId, final int quantity) {
        Client client = clientRepositoryPort.findById(UUID.fromString(clientId));
        Cart cart = client.getCart();
        List<CartItem> cartItems = cart.getCartItems();
        cartItems.add(new CartItem(UUID.fromString(productId), (long) quantity));
        clientRepositoryPort.update(client.getId(), client);
    }

    @Override
    public void removeFromClientCart(final UUID clientId, final UUID productID) {
        Client client = clientRepositoryPort.findById(clientId);
        Cart cart = client.getCart();
        List<CartItem> cartItems = cart.getCartItems();
        CartItem cartItemToRemove = cartItems.stream().filter(items -> items.getProductId().equals(productID)).findFirst().orElseThrow(NotFoundException::new);
        cartItems.remove(cartItemToRemove);
        clientRepositoryPort.update(client.getId(), client);
    }
}
