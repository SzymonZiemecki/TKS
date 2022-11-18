package com.pas.manager;

import com.pas.model.Address;
import com.pas.repository.AddressRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.pas.utils.ErrorInfo.ENTITY_NOT_FOUND_MESSAGE;

@ApplicationScoped
public class AddressManager {

    @Inject
    private AddressRepository addressRepository;

    public Address findById(UUID id) {
        return addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE.getValue()));
    }

    public List<Address> findAllAddresses(){
        return addressRepository.findAll();
    }

    public Address addAddress(Address address) {
        return addressRepository.add(address);
    }

    public Address updateAddress(UUID id, Address updatedAddress){
        return addressRepository.update(id, updatedAddress);
    }
    public void deleteAddress(UUID addressId){
        addressRepository.delete(addressId);
    }
}