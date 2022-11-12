package com.pas.endpoint;


import com.pas.manager.AddressManager;
import com.pas.model.Address;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;

import java.util.List;
import java.util.UUID;

@Path("/addresses")
@Consumes("application/json")
@Produces("application/json")
@ApplicationScoped
public class AddressAPI {
    @Inject
    private AddressManager addressManager;

    @GET
    public List<Address> getAllAddresses(){
        return addressManager.findAllAddresses();
    }

    @GET
    @Path("/{id}")
    public Address getAddressById(@PathParam("id") UUID id){
        return addressManager.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity with given id doesnt exist"));
    }

    @POST
    public Address addAddress(Address address){
        return addressManager.addAddress(address);
    }

    @PATCH
    @Path("/{id}")
    public Address updateAddress(@PathParam("id") UUID addressId, Address updatedAddress){
        return addressManager.updateAddress(addressId, updatedAddress);
    }

    @DELETE
    @Path("/{id}")
    public void deleteAddress(@PathParam("id") UUID id){
        addressManager.deleteAddress(id);
    }
}
