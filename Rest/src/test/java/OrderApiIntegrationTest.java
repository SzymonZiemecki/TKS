import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tks.Product.Product;
import com.tks.Product.Tv;
import com.tks.dto.AddressDTO;
import com.tks.dto.product.ProductDTO;
import com.tks.dto.product.TvDTO;
import com.tks.dto.user.UserDTO;
import com.tks.model.Address;
import com.tks.model.CartItem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;

@Testcontainers
public class OrderApiIntegrationTest extends TestContainerInitializer {
    @Test
    public void createOrderTest() throws InterruptedException, JsonProcessingException {
        UserDTO res = registerUser("Jakub", "Wardyn", "first", 120d);

        ProductDTO obj = TvDTO.builder()
                .name("name")
                .producer("producer")
                .productDescription("description")
                .price(20.2)
                .availableAmount(20)
                .refreshRate("fast")
                .resolution("big")
                .panelType("eesss")
                .screenSize("koxxxx").build();
        String json2 = objectMapper.writeValueAsString(obj);

        //add product
        ProductDTO res2 = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json2)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(ProductDTO.class);
        Assertions.assertEquals(res2.getProducer(), "producer");

        //add product to user's cart
        List<CartItem> items = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json2)
                .queryParam("productId", res2.getId())
                .queryParam("quantity", 1)
                .log().all()
                .put("/users/{id}/cart", res.getId())
                .then()
                .extract().jsonPath().getList("cartItems");
        Assertions.assertFalse(items.isEmpty());

        //create order - allocation
        Address shippingAddress = new Address("polska", "lowicz", "lowicka", "12", "21-377");
        String jsonAddress = objectMapper.writeValueAsString(shippingAddress);

        RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(jsonAddress)
                .queryParam("userId", res.getId())
                .log().all()
                .post("/orders/create")
                .then()
                .statusCode(200);
    }

    @Test
        public void createOrderItemsOutOfStock() throws InterruptedException, JsonProcessingException {
        UserDTO res = registerUser("Jakub", "Wardyn", "second", 120000d);

        ProductDTO obj = TvDTO.builder()
                .name("name")
                .producer("producer")
                .productDescription("description")
                .price(20.2)
                .availableAmount(20)
                .refreshRate("fast")
                .resolution("big")
                .panelType("eesss")
                .screenSize("koxxxx").build();
        String json2 = objectMapper.writeValueAsString(obj);

        //add product
        ProductDTO res2 = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json2)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(ProductDTO.class);
        Assertions.assertEquals(res2.getProducer(), "producer");

        //add product to user's cart
        List<CartItem> items = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json2)
                .queryParam("productId", res2.getId())
                .queryParam("quantity", 100)
                .log().all()
                .put("/users/{id}/cart", res.getId())
                .then()
                .extract().jsonPath().getList("cartItems");
        Assertions.assertFalse(items.isEmpty());

        //create order - allocation
        Address shippingAddress = new Address("polska", "lowicz", "lowicka", "12", "21-377");
        String jsonAddress = objectMapper.writeValueAsString(shippingAddress);

        String messageWithExpectedError = RestAssured.given(requestSpecification)
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

    private UserDTO registerUser(String name, String surname, String login, double money) throws JsonProcessingException {
        UserDTO obj = UserDTO.builder()
                .firstName(name)
                .lastName(surname)
                .login(login)
                .password("password")
                .accountBalance(money)
                .address(new AddressDTO("polska", "bialo", "czerwoni", "na", "essie"))
                .build();
        String json = objectMapper.writeValueAsString(obj);

        UserDTO res = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/users/register")
                .then()
                .extract().body().as(UserDTO.class);
        return res;
    }

    @Test
    public void createOrderNotEnoughMoney() throws InterruptedException, JsonProcessingException {
        UserDTO res = registerUser("Jakub", "Wardyn", "third", 1d);

        ProductDTO obj = TvDTO.builder()
                .name("name")
                .producer("producer")
                .productDescription("description")
                .price(20.2)
                .availableAmount(20)
                .refreshRate("fast")
                .resolution("big")
                .panelType("eesss")
                .screenSize("koxxxx").build();
        String json2 = objectMapper.writeValueAsString(obj);

        //add product
        ProductDTO res2 = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json2)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(ProductDTO.class);
        Assertions.assertEquals(res2.getProducer(), "producer");

        //add product to user's cart
        List<CartItem> items = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json2)
                .queryParam("productId", res2.getId())
                .queryParam("quantity", 1)
                .log().all()
                .put("/users/{id}/cart", res.getId())
                .then()
                .extract().jsonPath().getList("cartItems");
        Assertions.assertFalse(items.isEmpty());

        //create order - allocation
        Address shippingAddress = new Address("polska", "lowicz", "lowicka", "12", "21-377");
        String jsonAddress = objectMapper.writeValueAsString(shippingAddress);

        String messageWithExpectedError = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(jsonAddress)
                .queryParam("userId", res.getId())
                .log().all()
                .post("/orders/create")
                .then()
                .statusCode(500)
                .extract().body().asString();
        assertTrue(messageWithExpectedError.contains("Can't create order, user doesn't have enough money"));
    }

}
