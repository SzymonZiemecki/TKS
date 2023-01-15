package com.pas.demo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("esa")
@ApplicationScoped
public class Resource {

    @GET
    public String getAll(){
        return "ESA";
    }
}
