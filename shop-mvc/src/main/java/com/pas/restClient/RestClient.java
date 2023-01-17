package com.pas.restClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.UriBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class RestClient<T> {

    protected static final String API_URL = "https://localhost:8181/shop-rest-1.0-SNAPSHOT/";
    protected String endpoint;
    TypeReference<List<T>> allTypeReference;
    TypeReference<T> singleTypeReference;

    GenericType<T> genericType;
    Class<T> clazz;

    HttpClient client;

    public RestClient(Class<T> clazz , GenericType<T> genericType, String endpoint, TypeReference<List<T>> allTypeReference, TypeReference<T> singleTypeReference) {
        this.endpoint = endpoint;
        this.allTypeReference = allTypeReference;
        this.singleTypeReference = singleTypeReference;
        this.genericType = genericType;
        this.clazz= clazz;
        this.client = HttpClient.newBuilder().build();
    }

    public List<T> getAllRequest(){
        return readList((String) get().body());
    }

    public T addRequest(T entity){
        return readOne((String) add(entity).body());
    }

    public T getByIdRequest(UUID id){
        return readOne((String) get("/" + id.toString()).body());
    }

    public T updateRequest(UUID id, T entity){
        return readOne((String) update(id, entity).body());
    }

    public HttpResponse deleteRequest(UUID id){
        String callUrl = API_URL + endpoint + "/" +  id.toString();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(callUrl))
                .DELETE().build();
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected static ObjectMapper mapper = new ObjectMapper();
    private HttpResponse get(String customUrl){
        String custom = customUrl!=null ? customUrl : "";
        String callUrl = API_URL + endpoint + custom;
        HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(callUrl))
                        .GET().build();
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpResponse customGet(String customUrl){
        String custom = customUrl!=null ? customUrl : "";
        String callUrl = API_URL + endpoint + custom;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(callUrl))
                .GET().build();
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse customPut(String customUrl, Map<?,?> params){
        String custom = customUrl!=null ? customUrl : "";
        String callUrl = API_URL + endpoint + custom;
        UriBuilder uri = UriBuilder.fromUri(callUrl);
        params.entrySet().stream().forEach( entry -> {
            uri.queryParam((String) entry.getKey(), entry.getValue());
        });
        String url = String.valueOf(uri.build());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.noBody()).build();
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse customPut(String customUrl){
        String custom = customUrl!=null ? customUrl : "";
        String callUrl = API_URL + endpoint + custom;
        UriBuilder uri = UriBuilder.fromUri(callUrl);
        String url = String.valueOf(uri.build());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.noBody()).build();
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse customPatch(String customUrl, Map<?,?> params){
        String custom = customUrl!=null ? customUrl : "";
        String callUrl = API_URL + endpoint + custom;
        UriBuilder uri = UriBuilder.fromUri(callUrl);
        params.entrySet().stream().forEach( entry -> {
            uri.queryParam((String) entry.getKey(), entry.getValue());
        });
        String url = String.valueOf(uri.build());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("PATCH", HttpRequest.BodyPublishers.noBody()).build();
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse customPatch(String customUrl){
        String custom = customUrl!=null ? customUrl : "";
        String callUrl = API_URL + endpoint + custom;
        UriBuilder uri = UriBuilder.fromUri(callUrl);
        String url = String.valueOf(uri.build());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("PATCH", HttpRequest.BodyPublishers.noBody()).build();
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<?> customGet(String customUrl, TypeReference<?> customType){
        return readCustomType((String) customGet(customUrl).body(), customType);
    }

    private HttpResponse get(){
        return get(null);
    }

    private HttpResponse add(T entity){
        try {
            String jsonBody = mapper.writeValueAsString(entity);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + endpoint))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpResponse customPostRequest(String url, Object entity){
        try {
            String custom = url!=null ? url : "";
            String callUrl = API_URL + endpoint + custom;
            String jsonBody = mapper.writeValueAsString(entity);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(callUrl))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Object customPost(String url, Object entity, TypeReference<?> typeReference){
        return customPostRequest(url, entity).body();
    }

    private HttpResponse update(UUID id, T entity){
        try {
            String jsonBody = mapper.writeValueAsString(entity);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + endpoint + "/" + id.toString()))
                    .headers("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<T> readList(String json){
        try {
            if(json.equals("[]")){
                return List.of();
            }
            return mapper.readValue(json,allTypeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private T readOne(String json){
        try {
            return mapper.readValue(json,singleTypeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<T> readCustomType(String json, TypeReference<?> customType){
        try {
            if(json.equals("[]")){
                return List.of();
            }
            return (List<T>) mapper.readValue(json,customType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Object readOneCustomType(String json, TypeReference<?> customType){
        try {
            return mapper.readValue(json,customType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
