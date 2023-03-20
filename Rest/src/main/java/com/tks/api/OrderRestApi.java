package com.tks.api;

import com.tks.dto.AddressDTO;
import com.tks.dto.OrderDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

public interface OrderRestApi {
    Response getAllOrders();

    Response getOrderById(@PathParam("id") UUID id);

    Response getOngoingOrders();

    Response getFinishedOrders();

    Response createOrder(@QueryParam("userId") UUID userId, @Valid AddressDTO shippingAddress);

    Response deliverOrder(@PathParam("id") UUID orderId);

    Response deleteOrder(@PathParam("id") UUID orderId);
}
