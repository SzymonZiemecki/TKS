package com.tks.restapi;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/healthCheck")
public class Healthcheck {

    @GET
    public Response healthCheck(){
        return Response.ok().entity("Application is healthy").build();
    }
}
