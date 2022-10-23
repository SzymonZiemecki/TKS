package com.pas.service;

import com.pas.manager.UserManager;
import com.pas.model.Role;
import com.pas.model.User;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.Arrays;

@Path("/user")
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
