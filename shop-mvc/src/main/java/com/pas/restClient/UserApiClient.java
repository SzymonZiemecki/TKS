package com.pas.restClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pas.controller.Auth.JwtTokenHolderBean;
import com.pas.model.Product.Product;
import com.pas.model.User.Cart;
import com.pas.model.User.CartItem;
import com.pas.model.User.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;

import java.io.Serializable;
import java.util.*;

@RequestScoped
public class UserApiClient extends RestClient<User> implements Serializable {

    @Inject
    JwtTokenHolderBean jwtTokenHolderBean;

    public UserApiClient() {
        super(User.class, new GenericType<User>(){}, "users", new TypeReference<List<User>>(){}, new TypeReference<User>(){});
    }

    public List<User> getAllUsers(){
        WebTarget webTarget = client.path("/users");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get();
        return response.readEntity(new GenericType<List<User>>(){});
    }

    public void addUser(User user){
        WebTarget webTarget = client.path("/users");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .post(Entity.json(user));
    }

    public void updateUser(UUID id, User user){
        WebTarget webTarget = client.path("/users/" + id.toString());
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .put(Entity.json(user));
    }

    public void addToCart(UUID userId, UUID productId, long quantity){
        WebTarget webTarget = client.path("/users/" + userId.toString() + "/cart").queryParam("productId", productId.toString()).queryParam("quantity", quantity);
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .put(Entity.json(""));
    }

    public List<User> findOneByLogin(String login){
        WebTarget webTarget = client.path("/users").queryParam("oneByLogin", login);
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get();
        return response.readEntity(new GenericType<List<User>>(){});
    }

    public User getUserById(UUID id) {
        WebTarget webTarget = client.path("/users/" + id.toString());
         return webTarget.request(MediaType.APPLICATION_JSON)
                 .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get(User.class);
    }

    public void suspendOrResumeUser(UUID id){
        WebTarget webTarget = client.path("/users/" + id.toString() + "/suspendOrResume");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .put(Entity.json(""));
    }

    public List<User> getSearchedUsers(Optional<String> searchInput) {
        WebTarget webTarget = client.path("/users").queryParam("allMatchingByLogin", searchInput.orElse(""));
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get();
        return response.readEntity(new GenericType<List<User>>(){});
    }

    public void removeFromCart(UUID userId, UUID productId) {
        WebTarget webTarget = client.path("/users").queryParam("productId", productId);
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .method("PATCH",Entity.json(""));
    }

    public void register(User currentUser) {
        WebTarget webTarget = client.path("/users/register");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .post(Entity.json(currentUser));
    }
}
