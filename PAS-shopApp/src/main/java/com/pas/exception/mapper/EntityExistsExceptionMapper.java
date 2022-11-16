package com.pas.exception.mapper;

import com.pas.exception.BusinessLogicException;
import jakarta.persistence.EntityExistsException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Provider
@Slf4j
public class EntityExistsExceptionMapper implements ExceptionMapper<EntityExistsException> {

    public Response toResponse(EntityExistsException exception) {
        log.info("An error occurred", exception);
        return Response.status(500).entity(exception.getMessage()).type("text/plain").build();
    }

}