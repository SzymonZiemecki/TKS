package com.pas.restClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pas.model.Product.Product;
import com.pas.model.User.CartItem;
import com.pas.model.User.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.GenericType;

import java.util.*;

@ApplicationScoped
public class UserApiClient extends RestClient<User>{
    public UserApiClient() {
        super(User.class, new GenericType<User>(){}, "users", new TypeReference<List<User>>(){}, new TypeReference<User>(){});
    }

    public List<User> getAllUsers(){
        return this.getAllRequest();
    }

    public User updateUser(UUID id, User user){
        return this.updateRequest(id, user);
    }

    public void addToCart(UUID userId, UUID productId, long quantity){
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("productId", productId.toString());
        paramsMap.put("quantity", String.valueOf(quantity));
        this.customPut("/"+ userId.toString()+"/cart", paramsMap);
    }

    public List<User> findOneByLogin(String login){
        return (List<User>) this.customGet("?oneByLogin=" + login, new TypeReference<List<User>>(){});
    }

    public User getProductById(UUID id) {
        return this.getByIdRequest(id);
    }

    public Map<Product, Long> getUserCart(UUID userId){
        return (Map<Product, Long>) this.customGet("/" + userId.toString() + "/cart", new TypeReference<List<CartItem>>(){});
    }

    public void suspendOrResumeUser(UUID userId){
        customPut("/" + userId.toString() + "/suspendOrResume");
    }

    public List<User> getSearchedUsers(Optional<String> searchInput, Object o) {
        return (List<User>) this.customGet("?allMatchingByLogin=" + searchInput.get(), new TypeReference<List<User>>(){});
    }

    public void removeFromCart(UUID userId, UUID productId) {
        this.customPatch("/" + userId.toString() + "/cart?productId=" + productId.toString());
    }
}
