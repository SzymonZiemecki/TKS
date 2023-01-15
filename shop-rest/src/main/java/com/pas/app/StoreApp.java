package com.pas.app;

import com.pas.endpoint.OrderApiImpl;
import com.pas.endpoint.ProductApiImpl;
import com.pas.endpoint.UserApiImpl;
import com.pas.endpoint.auth.AuthEndpointImpl;
import jakarta.faces.annotation.FacesConfig;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ApplicationPath("")
public class StoreApp extends Application {
    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("jersey.config.jsonFeature", "JacksonFeature");
        return props;
    }
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(AuthEndpointImpl.class, OrderApiImpl.class, UserApiImpl.class, ProductApiImpl.class);
    }
}