package com.tks.api;

import com.nimbusds.jose.JOSEException;
import com.tks.User.User;
import com.tks.dto.*;
import com.tks.model.Cart;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRestApi {

    Response getUsers(@QueryParam("allMatchingByLogin") Optional<String> allMatchingByLogin, @QueryParam("oneByLogin") Optional<String> oneByLogin);

    Response getUserById(@PathParam("id") UUID id) throws JOSEException;

    Response getOngoingUserOrders(@PathParam("id") UUID userId);

    Response getFinishedUserOrders(@PathParam("id") UUID userId);

    Response getAllUserOrders(@PathParam("id") UUID userId);

    Response updateUser(@PathParam("id") UUID id, UserDTO updatedUser, @HeaderParam("If-Match") String ifMatch) throws ParseException, JOSEException;

    Response changePassword(@PathParam("id") UUID id, ChangePasswordDTO changePasswordDTO);

    Response getCart(@PathParam("id") UUID userId);

    Response getUserOrders(@PathParam("id") UUID userId);

    Response addToCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId, @QueryParam("quantity") Long quantity);

    Response removeFromCart(@PathParam("id") UUID userId, @QueryParam("productId") UUID productId);

    Response register(@Valid RegisterDTO user);

    Response addUser(@Valid RegisterDTO user);

    Response suspendOrResumeUser(@PathParam("id") UUID userId);
}