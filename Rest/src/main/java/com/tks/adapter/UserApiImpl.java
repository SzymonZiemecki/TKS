package com.tks.adapter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.tks.User.User;
import com.tks.api.UserRestApi;
import com.tks.dto.ChangePasswordDTO;
import com.tks.dto.RegisterDTO;
import com.tks.dto.UserDTO;
import com.tks.security.JWTAuthTokenUtils;
import com.tks.userinterface.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.security.Principal;
import java.text.ParseException;
import java.util.*;

public class UserApiImpl implements UserRestApi {

    @Inject
    UserService userManager;

    @Inject
    Principal principal;

    @Override
    public Response getUsers(Optional<String> allMatchingByLogin, Optional<String> oneByLogin) {
        return Response.status(Response.Status.OK).entity(userManager.findUserByLogin(String.valueOf(allMatchingByLogin))).build();
    }
    @Override
    public Response getUserById(UUID id) throws JOSEException {
        User user = userManager.getUserById(id);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", String.valueOf(id));
        jsonObject.addProperty("login", user.getLogin());
        String ifMatch = JWTAuthTokenUtils.sign(jsonObject.toString());
        return Response.status(Response.Status.OK).entity(user).tag(ifMatch).build();
    }
    @Override
    public Response getOngoingUserOrders(UUID userId) {
        return Response.ok().entity(userManager.getOngoingUserOrders(userId)).build();
    }

    @Override
    public Response getFinishedUserOrders(UUID userId) {
        return Response.ok().entity(userManager.getFinishedUserOrders(userId)).build();
    }
    @Override
    public Response getAllUserOrders(UUID userId) {
        return Response.ok().entity(userManager.getAllUserOrders(userId)).build();
    }

    @Override
    public Response updateUser(UUID id, UserDTO updatedUser, @HeaderParam("If-Match") String ifMatch) throws ParseException, JOSEException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", updatedUser.getId().toString());
        jsonObject.addProperty("login", updatedUser.getLogin());
        if(!JWTAuthTokenUtils.verify(ifMatch, jsonObject.toString())){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        userManager.updateUser(id, updatedUser);
        return Response.ok().build();
    }
    @Override
    public Response changePassword(UUID id, ChangePasswordDTO changePasswordDTO) {
       userManager.changeUserPassword(id, changePasswordDTO);
       return Response.ok().build();
    }
    @Override
    public Response getCart(UUID userId) {
        return Response.ok().entity(userManager.getUserCart(userId)).build();
    }
    @Override
    public Response getUserOrders(UUID userId) {
        return Response.ok().entity(userManager.getAllUserOrders(userId)).build();
    }

    public Response addToCart(UUID userId, @QueryParam("productId") UUID productId, @QueryParam("quantity") Long quantity) {
        return Response.ok().entity(userManager.addToCart(userId, productId, quantity)).build();
    }


    public Response removeFromCart(UUID userId, @QueryParam("productId") UUID productId) {
        return Response.ok().entity(userManager.removeFromCart(userId, productId)).build();
    }

    public Response register(RegisterDTO user) {
        return userManager.addUser(user);
    }

    public Response addUser(RegisterDTO user) {
        return userManager.addUser(user);
    }

    public Response suspendOrResumeUser(UUID userId) {
        userManager.suspendOrResumeUser(userId);
        return Response.ok().build();
    }
}
