package com.tks.configuration;

import com.tks.adapter.AuthEndpointImpl;
import com.tks.adapter.OrderApiImpl;
import com.tks.adapter.ProductApiImpl;
import com.tks.adapter.UserApiImpl;
import com.tks.api.OrderRestApi;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationPath("/api/v1")
public class JAXRsConfiguration extends Application {

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("jersey.config.jsonFeature", "JacksonFeature");
        return props;
    }

}