package com.pas.service;

import com.pas.manager.UserManager;
import com.pas.model.Product;
import com.pas.model.Role;
import com.pas.model.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/users")
@Consumes("application/json")
public class UserService {

    @Inject
    UserManager userManager;

    @GET
    @Path("/admin")
    @Produces("application/json")
    public String hello(){
        return "Hello admin!";
    }

    @GET
    @Path("/manager")
    @Produces("application/json")
    public String hello2(){
        return "Hello moderator!";
    }

    @GET
    @Path("/user")
    @Produces("application/json")
    public String hello3(){
        return "Hello user!";
    }

    @GET
    @Path("/guest")
    @Produces("application/json")
    public String hello4(){
        return "Hello guest!";
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public List<User> getAllUsers(){
        return userManager.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Optional<User> getUserById(@PathParam("id") UUID id){
        return userManager.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Produces("application/json")
    public void editUser(@PathParam("id") UUID id, User updatedUser){
        userManager.updateUser(updatedUser);
    }

    @PUT
    @Path("/{id}/cart")
    @Produces("application/json")
    public void addToCart(@PathParam("id") UUID id, Product item){
        userManager.updateUser(updatedUser);
    }

    @POST
    @Path("/register")
    @Produces("application/json")
    public User register(User user){
        User toRegister = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .login(user.getLogin())
                .active(true)
                .roles(Arrays.asList(Role.USER)).build();
        return userManager.register(toRegister);
    }
}
