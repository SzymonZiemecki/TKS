package com.tks.microservices.orderservice.rest.api;

import java.util.UUID;

import com.tks.microservices.orderservice.rest.dto.AddressDTO;

import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

public interface OrderRestApi {
    Response getAllOrders();

    Response getOrderById(@PathParam("id") UUID id);

    Response getOngoingOrders();

    Response getFinishedOrders();

    Response createOrder(@QueryParam("userId") UUID userId, @Valid AddressDTO shippingAddress);

    Response deliverOrder(@PathParam("id") UUID orderId);

    Response deleteOrder(@PathParam("id") UUID orderId);
}
