package com.pas.manager;

import com.pas.model.Address;
import com.pas.repository.AddressRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AddressManager {

    @Inject
    private AddressRepository addressRepository;

    public Optional<Address> findById(UUID id) {
        return addressRepository.findById(id);
    }

    public Address addAddress(Address address) {
        return addressRepository.add(address);
    }
}