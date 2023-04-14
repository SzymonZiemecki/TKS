package com.tks.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/healthCheck")
@ApplicationScoped
public class Healthcheck {

    @GET
    public Response healthCheck(){
        return Response.ok().entity("Application is healthy").build();
    }
}
