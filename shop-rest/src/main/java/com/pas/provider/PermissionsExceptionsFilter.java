package com.pas.provider;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;


import java.io.IOException;

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
