package com.tks.filter;

import com.tks.data.utils.ErrorInfoEnt;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;


import java.io.IOException;

import static com.tks.data.utils.ErrorInfoEnt.BAD_REQUEST;
import static com.tks.data.utils.ErrorInfoEnt.UNAUTHORIZED;


@Provider
public class PermissionsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        if(containerResponseContext.getStatus() == 401 || containerResponseContext.getStatus() == 403){
            containerResponseContext.setEntity(UNAUTHORIZED.getValue());
        }
        if(containerResponseContext.getStatus() == 400 || containerResponseContext.getStatus() == 403){
            containerResponseContext.setEntity(BAD_REQUEST.getValue());
        }
    }
}