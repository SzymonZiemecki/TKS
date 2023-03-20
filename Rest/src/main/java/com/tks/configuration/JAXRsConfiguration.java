package com.tks.configuration;

import com.tks.adapter.AuthEndpointImpl;
import com.tks.adapter.OrderApiImpl;
import com.tks.adapter.ProductApiImpl;
import com.tks.adapter.UserApiImpl;
import com.tks.api.OrderRestApi;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api/v1")
public class JAXRsConfiguration extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(UserApiImpl.class);
        classes.add(ProductApiImpl.class);
        classes.add(AuthEndpointImpl.class);
        classes.add(OrderApiImpl.class);
        return classes;
    }

}