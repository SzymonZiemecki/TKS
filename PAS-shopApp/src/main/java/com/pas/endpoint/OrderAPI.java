package com.pas.endpoint;

import com.pas.manager.OrderManager;
import com.pas.model.Address;
import com.pas.model.dto.OrderDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

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
    @RolesAllowed({"Manager"})
    public List<OrderDTO> getAllOrders(){
        return OrderDTO.entityListToDTO(orderManager.findAllOrders());
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    public OrderDTO getOrderById(@PathParam("id") UUID id){
        return OrderDTO.fromEntityToDTO(orderManager.findById(id));
    }
    @GET
    @Path("/ongoing")
    @RolesAllowed({"Manager"})
    public List<OrderDTO> getOngoingOrders(){
        return OrderDTO.entityListToDTO(orderManager.findOngoingOrders());
    }

    @GET
    @Path("/finished")
    @RolesAllowed({"Manager"})
    public List<OrderDTO> getFinishedOrders(){
        return OrderDTO.entityListToDTO(orderManager.findFinishedOrders());
    }

    @POST
    @Path("/create")
    @RolesAllowed({"BaseUser"})
    public OrderDTO createOrder(@QueryParam("userId") UUID userId, @Valid Address shippingAddress){
        return OrderDTO.fromEntityToDTO(orderManager.createOrder(userId, shippingAddress));
    }

    @PATCH
    @Path("/{id}/deliver")
    @RolesAllowed("Manager")
    public Response deliverOrder(@PathParam("id") UUID orderId){
        orderManager.deliverOrder(orderId);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Manager"})
    public Response deleteOrder(@PathParam("id") UUID orderId){
        orderManager.deleteOrder(orderId);
        return Response.ok().build();
    }


}
