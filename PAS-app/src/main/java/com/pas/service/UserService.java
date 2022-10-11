package com.pas.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/user")
public class UserService {

    @GET
    public String hello(){
        return "Hello World!";
    }
}
