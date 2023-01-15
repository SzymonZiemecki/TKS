package com.pas.controller.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.pas.endpoint.OrderAPI;
import com.pas.endpoint.ProductAPI;
import com.pas.endpoint.UserAPI;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

import java.util.ArrayList;
import java.util.List;

public class ClientFactory {
    public static UserAPI userAPIClient(){
        UserAPI api = JAXRSClientFactory.create("http://localhost:8080/shopApp-1.0-SNAPSHOT", UserAPI.class, jsonProvider());
        return api;
    }

    public static ProductAPI productAPIClient(){
        ProductAPI api = JAXRSClientFactory.create("http://localhost:8080/shopApp-1.0-SNAPSHOT", ProductAPI.class,jsonProvider());
        return api;
    }

    public static OrderAPI orderAPIClient(){
        OrderAPI api = JAXRSClientFactory.create("http://localhost:8080/shopApp-1.0-SNAPSHOT", OrderAPI.class, jsonProvider());
        return api;
    }

    public static List<JacksonJaxbJsonProvider> jsonProvider(){
        List<JacksonJaxbJsonProvider> providers = new ArrayList<>();
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(new ObjectMapper());
        providers.add(provider);
        return providers;
    }

}
