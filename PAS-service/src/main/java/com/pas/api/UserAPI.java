package com.pas.api;

import com.pas.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/hello-world")
public class UserAPI {

    UserService userService;

    @Inject
    public UserAPI(UserService userService) {
        this.userService = userService;
    }

     @GET
     @Produces("text/plain")
     public String hello() {
        return userService.hello();
     }
}
