package com.pas.restClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class AuthApiClient extends RestClient{

    private static final String AUTH_HEADER_NAME="Authentication";

    public AuthApiClient() {}

    public String login(String login, String password){
        Credentials credentials = new Credentials(login, password);
        try {
            String jsonAuthBody = new ObjectMapper().writeValueAsString(credentials);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL+"auth/login"))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonAuthBody)).build();
            Map<String, List<String>> responseHeaders = client.send(request, HttpResponse.BodyHandlers.ofString()).headers().map();
            return responseHeaders.containsKey(AUTH_HEADER_NAME) ? responseHeaders.get(AUTH_HEADER_NAME).get(0) : "";
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Credentials {
        private String login;
        private String password;
    }
}
