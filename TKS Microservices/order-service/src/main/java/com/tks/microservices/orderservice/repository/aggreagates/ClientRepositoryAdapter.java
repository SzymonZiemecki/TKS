package com.tks.microservices.orderservice.repository.aggreagates;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.tks.microservices.orderservice.core.model.Client;
import com.tks.microservices.orderservice.port.security.ClientRepositoryPort;
import com.tks.microservices.orderservice.repository.ClientEntRepository;
import com.tks.microservices.orderservice.repository.model.ClientEnt;

import jakarta.ws.rs.NotFoundException;

@Component
public class ClientRepositoryAdapter implements ClientRepositoryPort {
     private ClientEntRepository clientEntRepository;

    public ClientRepositoryAdapter(final ClientEntRepository clientEntRepository) {
        this.clientEntRepository = clientEntRepository;
    }

    @Override
    public Client add(final Client entity) {
        return ClientEnt.clientEntToDomainModel(clientEntRepository.add(ClientEnt.clientToEnt(entity)));
    }

    @Override
    public void delete(final UUID id) {
        clientEntRepository.delete(id);
    }

    @Override
    public void delete(final Client entity) {
        clientEntRepository.delete(ClientEnt.clientToEnt(entity));
    }

    @Override
    public Client update(final UUID id, final Client entity) {
        return ClientEnt.clientEntToDomainModel(clientEntRepository.update(id, ClientEnt.clientToEnt(entity)));
    }

    @Override
    public boolean exists(final String id) {
        try {
            clientEntRepository.findById(UUID.fromString(id));
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    @Override
    public Client findById(final UUID id) {
        return ClientEnt.clientEntToDomainModel(clientEntRepository.findById(id));
    }

    @Override
    public List<Client> findAll() {
        return clientEntRepository.findAll()
                .stream()
                .map(ClientEnt::clientEntToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return findAll().size();
    }
}
