package com.pas.provider;

import jakarta.ws.rs.ext.Provider;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

import static com.pas.utils.ErrorInfo.TOKEN_NOT_PROVIDED;
import static com.pas.utils.ErrorInfo.UNAUTHORIZED;

@Provider
public class PermissionsExceptionsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        if(containerResponseContext.getStatus() == 401 || containerResponseContext.getStatus() == 403){
            containerResponseContext.setEntity(UNAUTHORIZED.getValue());
        }
    }
}
