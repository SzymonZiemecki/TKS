package com.pas.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/user")
public class UserService {

    @GET
    public String hello(){
        return "Hello World!";
    }
}
