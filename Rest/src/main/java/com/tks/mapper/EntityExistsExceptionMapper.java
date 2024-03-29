package com.tks.mapper;

import jakarta.persistence.EntityExistsException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class EntityExistsExceptionMapper implements ExceptionMapper<EntityExistsException> {

    public Response toResponse(EntityExistsException exception) {
        log.info("An error occurred", exception);
        return Response.status(500).entity(exception.getMessage()).type("application/json").build();
    }

}
