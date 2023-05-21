package com.tks.microservices.orderservice.rest.adapter;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tks.microservices.orderservice.core.model.Order;
import com.tks.microservices.orderservice.port.ui.OrderService;
import com.tks.microservices.orderservice.rest.dto.AddressDTO;
import com.tks.microservices.orderservice.rest.dto.OrderDTO;

import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

@RestController
public class OrderApiImpl {

    private OrderService orderManager;

    public OrderApiImpl(final OrderService orderManager) {
        this.orderManager = orderManager;
    }

    @GetMapping("/all")
    public ResponseEntity getAllOrders() {
        return ResponseEntity.ok().body(orderManager.getAllOrders());
    }

    @GET
    @Path("/{id}")
    public ResponseEntity getOrderById(@PathParam("id") UUID id) {
        OrderDTO order = new OrderDTO();
        try {
            orderManager.getOrderById(id);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok().body(order);
    }

    @GET
    @Path("/ongoing")
    public ResponseEntity getOngoingOrders() {
        return ResponseEntity.ok().body(orderManager.getOngoingOrders());
    }

    @GET
    @Path("/finished")
    public ResponseEntity getFinishedOrders() {
        return ResponseEntity.ok().body(orderManager.getFinishedOrders());
    }

    @POST
    @Path("/create")
    public ResponseEntity createOrder(@QueryParam("userId") UUID clientId, @Valid AddressDTO shippingAddress) {
        Order order = new Order();
        try {
            order = orderManager.createOrder(clientId, AddressDTO.addressDTOToDomainModel(shippingAddress));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok(OrderDTO.orderToDTO(order));
    }

    @PUT
    @Path("/{id}/deliver")
    public ResponseEntity deliverOrder(@PathParam("id") UUID orderId) {
        try {
            orderManager.deliverOrder(orderId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public ResponseEntity deleteOrder(@PathParam("id") UUID orderId) {
        try {
            orderManager.deleteOrder(orderId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }


}
