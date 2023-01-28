package com.pas.restClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pas.controller.Auth.JwtTokenHolderBean;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.User.Cart;
import com.pas.model.User.CartItem;
import com.pas.model.User.User;
import com.pas.model.dto.ChangePasswordDTO;
import com.pas.model.dto.RegisterDTO;
import com.pas.model.dto.UserDTO;
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
        super();
    }

    public List<UserDTO> getAllUsers(){
        WebTarget webTarget = client.path("/users");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get();
        return response.readEntity(new GenericType<List<UserDTO>>(){});
    }

    public void addUser(RegisterDTO user){
        WebTarget webTarget = client.path("/users");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .post(Entity.json(user));
    }

    public void updateUser(UUID id, UserDTO user, String ifMatch){
        WebTarget webTarget = client.path("/users/" + id.toString());
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .header("If-Match", ifMatch)
                .put(Entity.json(user));
    }

    public void addToCart(UUID userId, UUID productId, long quantity){
        WebTarget webTarget = client.path("/users/" + userId.toString() + "/cart").queryParam("productId", productId.toString()).queryParam("quantity", quantity);
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .put(Entity.json(""));
    }

    public Response findOneByLogin(String login){
        WebTarget webTarget = client.path("/users").queryParam("oneByLogin", login);
        return webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get();
    }

    public UserDTO getUserById(UUID id) {
        WebTarget webTarget = client.path("/users/" + id.toString());
         return webTarget.request(MediaType.APPLICATION_JSON)
                 .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get(UserDTO.class);
    }

    public void suspendOrResumeUser(UUID id){
        WebTarget webTarget = client.path("/users/" + id.toString() + "/suspendOrResume");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .put(Entity.json(""));
    }

    public List<UserDTO> getSearchedUsers(Optional<String> searchInput) {
        WebTarget webTarget = client.path("/users").queryParam("allMatchingByLogin", searchInput.orElse(""));
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get();
        return response.readEntity(new GenericType<List<UserDTO>>(){});
    }

    public void removeFromCart(UUID userId, UUID productId) {
        WebTarget webTarget = client.path("/users/" + userId.toString() + "/cart").queryParam("productId", productId.toString());
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .delete();
    }

    public void register(RegisterDTO currentUser) {
        WebTarget webTarget = client.path("/users/register");
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .post(Entity.json(currentUser));
    }

    public void changePassword(UUID id, ChangePasswordDTO changePasswordDTO) {
        WebTarget webTarget = client.path("/users/" + id.toString() + "/changePassword");
        webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .put(Entity.json(changePasswordDTO));
    }

    public List<Order> getUserOrders(UUID id) {
        WebTarget webTarget = client.path("/users/" + id.toString() + "/allOrders");
        return webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get(new GenericType<List<Order>>(){});
    }

    public List<Order> getUserOrdersAdmin(UUID id) {
        WebTarget webTarget = client.path("/users/" + id.toString() + "/orders");
        return webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get(new GenericType<List<Order>>(){});
    }

    public Cart getUserCart(UUID id) {
        WebTarget webTarget = client.path("/users/" + id.toString() + "/cart");
        return webTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtTokenHolderBean.getJwtToken())
                .get(Cart.class);
    }
}
