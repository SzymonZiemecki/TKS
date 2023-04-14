package com.tks.api;

import com.nimbusds.jose.JOSEException;
import com.tks.dto.*;
import com.tks.dto.user.UserDTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;

public interface UserRestApi {

    Response getUsers(@QueryParam("allMatchingByLogin") Optional<String> allMatchingByLogin, @QueryParam("oneByLogin") Optional<String> oneByLogin);

    Response getUserById(@PathParam("id") UUID id) throws JOSEException;

    Response getOngoingUserOrders(@PathParam("id") UUID userId);

    Response getFinishedUserOrders(@PathParam("id") UUID userId);

    Response getAllUserOrders(@PathParam("id") UUID userId);

    Response updateUser(@PathParam("id") UUID id, UserDTO updatedUser) throws ParseException, JOSEException;

    Response changePassword(@PathParam("id") UUID id, ChangePasswordDTO changePasswordDTO);

    Response getCart(@PathParam("id") UUID userId);

    Response getUserOrders(@PathParam("id") UUID userId);

    Response addToCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId, @QueryParam("quantity") Long quantity);

    Response removeFromCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId);

    Response register(@Valid UserDTO user);

    Response addUser(@Valid UserDTO user);

    Response suspendOrResumeUser(@PathParam("id") UUID userId);
}