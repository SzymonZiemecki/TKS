import com.pas.model.Address;
import com.pas.model.Cart;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.Product.Tv;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;
import com.pas.model.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class OrderApiIntegrationTest extends TestContainerInitializer {
    @Test
    public void createOrderTest() throws InterruptedException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(baseUri).build();
        UserDTO obj = new UserDTO("Szymon", "Ziemecki", "login", "password", 120d);
        String json = objectMapper.writeValueAsString(obj);

        //register user
        UserDTO res = given(req)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/users/register")
                .then()
                .extract().body().as(UserDTO.class);
        Assertions.assertEquals(res.getFirstName(), "Szymon");

        Product product = new Tv(2, 200.0,"name","producer", "desc","40","8","3","IPS");
        String json2 = objectMapper.writeValueAsString(product);

        //add product
        Product res2 = given(req)
                .header("Content-Type", "application/json")
                .body(json2)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(Product.class);
        Assertions.assertEquals(res2.getProducer(), "producer");

        //add product to user's cart
        Cart cart = given(req)
                .header("Content-Type", "application/json")
                .body(json2)
                .queryParam("productId", res2.getId())
                .queryParam("quantity", 1)
                .log().all()
                .put("/users/{id}/cart", res.getId())
                .then()
                .extract().body().as(Cart.class);
        Assertions.assertFalse(cart.getItems().isEmpty());

        //create order - allocation
        Address shippingAddress = new Address("polska", "lowicz", "lowicka", "12", "21-37");
        String jsonAddress = objectMapper.writeValueAsString(shippingAddress);

        Order order = given(req)
                .header("Content-Type", "application/json")
                .body(jsonAddress)
                .queryParam("userId", res.getId())
                .log().all()
                .put("/orders/create", res.getId())
                .then()
                .extract().body().as(Order.class);
        Assertions.assertFalse(order.getItems().isEmpty());
    }

    @Test
    public void createBadOrderTest() throws InterruptedException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(baseUri).build();
        UserDTO obj = new UserDTO("Szymon", "Ziemecki", "login", "password", 120d);
        String json = objectMapper.writeValueAsString(obj);

        //register user
        UserDTO res = given(req)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/users/register")
                .then()
                .extract().body().as(UserDTO.class);
        Assertions.assertEquals(res.getFirstName(), "Szymon");

        Product product = new Tv(2, 200.0,"name","producer", "desc","40","8","3","IPS");
        String json2 = objectMapper.writeValueAsString(product);

        //add product
        Product res2 = given(req)
                .header("Content-Type", "application/json")
                .body(json2)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(Product.class);
        Assertions.assertEquals(res2.getProducer(), "producer");

        //add product to user's cart
        Cart cart = given(req)
                .header("Content-Type", "application/json")
                .body(json2)
                .queryParam("productId", res2.getId())
                .queryParam("quantity", 100)
                .log().all()
                .put("/users/{id}/cart", res.getId())
                .then()
                .extract().body().as(Cart.class);
        Assertions.assertFalse(cart.getItems().isEmpty());

        //create order - allocation
        Address shippingAddress = new Address("polska", "lowicz", "lowicka", "12", "21-37");
        String jsonAddress = objectMapper.writeValueAsString(shippingAddress);

        String messageWithExpectedError = given(req)
                .header("Content-Type", "application/json")
                .body(jsonAddress)
                .queryParam("userId", res.getId())
                .log().all()
                .put("/orders/create", res.getId())
                .then()
                .statusCode(500)
                .extract().body().asString();
        assertTrue(messageWithExpectedError.contains("Cant create order, items out of stock"));
    }

}