package com.pas.endpoint;


import com.pas.manager.AddressManager;
import com.pas.model.Address;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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

    private static final String ENTITY_NOT_FOUND_MESSAGE="Entity with given ID doesn't exist";

    @GET
    public List<Address> getAllAddresses(){
        return addressManager.findAllAddresses();
    }

    @GET
    @Path("/{id}")
    public Address getAddressById(@PathParam("id") UUID id){
        return addressManager.findById(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE));
    }

    @POST
    public Address addAddress(@Valid Address address){
        return addressManager.addAddress(address);
    }

    @PATCH
    @Path("/{id}")
    public Address updateAddress(@PathParam("id") UUID addressId, @Valid Address updatedAddress){
        return addressManager.updateAddress(addressId, updatedAddress);
    }

    @DELETE
    @Path("/{id}")
    public void deleteAddress(@PathParam("id") UUID id){
        addressManager.deleteAddress(id);
    }
}
