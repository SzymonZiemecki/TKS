package com.tks.microservices.userservice.repository.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class ApplicationExceptionMapper implements ExceptionMapper<LoginAlreadyTakenException> {
    public Response toResponse(LoginAlreadyTakenException exception) {
        log.info("An error occurred", exception);
        return Response.status(500).entity(exception.getMessage()).type("application/json").build();
    }
}