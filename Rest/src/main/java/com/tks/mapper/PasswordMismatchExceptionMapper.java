package com.tks.mapper;

import com.tks.exception.PasswordMismatchExcpetion;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class PasswordMismatchExceptionMapper implements ExceptionMapper<PasswordMismatchExcpetion> {
    public Response toResponse(PasswordMismatchExcpetion exception) {
        log.info("An error occurred", exception);
        return Response.status(400).entity(exception.getMessage()).type("application/json").build();
    }
}
