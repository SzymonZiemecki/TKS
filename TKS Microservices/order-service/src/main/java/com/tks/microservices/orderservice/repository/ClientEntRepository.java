package com.tks.microservices.orderservice.repository;

import org.springframework.stereotype.Component;

import com.tks.microservices.orderservice.repository.api.ClientRepository;
import com.tks.microservices.orderservice.repository.model.ClientEnt;

@Component
public class ClientEntRepository extends RepositoryImpl<ClientEnt> implements ClientRepository {
}
