package com.pas.endpoint;

import com.pas.manager.OrderManager;
import com.pas.model.Address;
import com.pas.model.Order;
import com.pas.model.dto.OrderDTO;
import com.pas.utils.exceptionMessages.exMsg;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;

import java.util.List;
import java.util.UUID;

@Path("/orders")
@Consumes("application/json")
@Produces("application/json")
@ApplicationScoped
public class OrderAPI {

    @Inject
    OrderManager orderManager;

    @GET
    public List<OrderDTO> getAllOrders(){
        return OrderDTO.entityListToDTO(orderManager.findAllOrders());
    }

    @GET
    @Path("/{id}")
    public OrderDTO getOrderById(@PathParam("id") UUID id){
        return OrderDTO.fromEntityToDTO(orderManager.findById(id).orElseThrow(() -> new EntityNotFoundException(exMsg.ENTITY_NOT_FOUND_MESSAGE.toString())));
    }

    @GET
    @Path("/ongoing")
    public List<OrderDTO> getOngoingOrders(){
        return OrderDTO.entityListToDTO(orderManager.findOngoingOrders());
    }

    @GET
    @Path("/finished")
    public List<OrderDTO> getFinishedOrders(){
        return OrderDTO.entityListToDTO(orderManager.findFinishedOrders());
    }

    @POST
    @Path("/create")
    public OrderDTO createOrder(@QueryParam("userId") UUID userId, @Valid Address shippingAddress){
        return OrderDTO.fromEntityToDTO(orderManager.createOrder(userId, shippingAddress));
    }

    @PATCH
    @Path("/{id}/deliver")
    public void deliverOrder(@PathParam("id") UUID orderId){
        orderManager.deliverOrder(orderId);
    }

    @DELETE
    @Path("/{id}")
    public void deleteOrder(@PathParam("id") UUID orderId){
        orderManager.deleteOrder(orderId);
    }


}
