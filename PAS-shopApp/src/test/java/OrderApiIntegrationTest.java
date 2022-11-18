import com.jayway.restassured.response.Response;
import com.pas.model.Address;
import com.pas.model.Cart;
import com.pas.model.Order;
import com.pas.model.Product.Product;
import com.pas.model.Product.Tv;
import com.pas.model.dto.OrderDTO;
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

import java.util.Map;
import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class OrderApiIntegrationTest extends TestContainerInitializer {
    @Test
    public void createOrderTest() throws InterruptedException, JsonProcessingException {
        UserDTO obj = new UserDTO("Szymon", "Ziemecki", "one", "password", 120d);
        String json = objectMapper.writeValueAsString(obj);

        //register user
        UserDTO res = given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/users/register")
                .then()
                .extract().body().as(UserDTO.class);
        Assertions.assertEquals(res.getFirstName(), "Szymon");

        Product product = new Tv(2, 20.0,"name","producer", "desc","40","8","3","IPS");
        String json2 = objectMapper.writeValueAsString(product);

        //add product
        Product res2 = given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json2)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(Product.class);
        Assertions.assertEquals(res2.getProducer(), "producer");

        //add product to user's cart
        Map<Product, Long> items = given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json2)
                .queryParam("productId", res2.getId())
                .queryParam("quantity", 1)
                .log().all()
                .put("/users/{id}/cart", res.getId())
                .then()
                .extract().jsonPath().getMap("items");
        Assertions.assertFalse(items.isEmpty());

        //create order - allocation
        Address shippingAddress = new Address("polska", "lowicz", "lowicka", "12", "21-377");
        String jsonAddress = objectMapper.writeValueAsString(shippingAddress);

        given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(jsonAddress)
                .queryParam("userId", res.getId())
                .log().all()
                .post("/orders/create")
                .then()
                .statusCode(200);
    }

    @Test
    public void createBadOrderTest() throws InterruptedException, JsonProcessingException {
        UserDTO obj = new UserDTO("Szymon", "Ziemecki", "two", "password", 120d);
        String json = objectMapper.writeValueAsString(obj);

        //register user
        UserDTO res = given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/users/register")
                .then()
                .extract().body().as(UserDTO.class);
        Assertions.assertEquals(res.getFirstName(), "Szymon");

        Product product = new Tv(2, 20.0,"name","producer", "desc","40","8","3","IPS");
        String json2 = objectMapper.writeValueAsString(product);

        //add product
        Product res2 = given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json2)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(Product.class);
        Assertions.assertEquals(res2.getProducer(), "producer");

        //add product to user's cart
        Map<Product, Long> items = given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json2)
                .queryParam("productId", res2.getId())
                .queryParam("quantity", 100)
                .log().all()
                .put("/users/{id}/cart", res.getId())
                .then()
                .extract().jsonPath().getMap("items");
        Assertions.assertFalse(items.isEmpty());

        //create order - allocation
        Address shippingAddress = new Address("polska", "lowicz", "lowicka", "12", "21-377");
        String jsonAddress = objectMapper.writeValueAsString(shippingAddress);

        String messageWithExpectedError = given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(jsonAddress)
                .queryParam("userId", res.getId())
                .log().all()
                .post("/orders/create")
                .then()
                .statusCode(500)
                .extract().body().asString();
        assertTrue(messageWithExpectedError.contains("Can't create order, items out of stock"));
    }

}