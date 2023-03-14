/*
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;
import com.pas.model.Product.Product;
import com.pas.model.Product.Tv;
import com.pas.model.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class ProductApiIntegrationTest extends TestContainerInitializer {
    @Test
    public void addProductTest() throws InterruptedException, JsonProcessingException {
        Product obj = new Tv(2, 200.0, "name", "producer", "desc", "40", "8", "3", "IPS");
        String json = objectMapper.writeValueAsString(obj);

        //add product
        Product res = given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(Product.class);
        Assertions.assertEquals(res.getProducer(), "producer");
    }

    @Test
    public void readProductTest() throws InterruptedException, JsonProcessingException {
        Product obj = new Tv(2, 200.0, "name", "producer", "desc", "40", "8", "3", "IPS");
        String json = objectMapper.writeValueAsString(obj);

        //add product
        Product res = given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(Product.class);
        Assertions.assertEquals(res.getProducer(), "producer");


        //get product
        Product res2 = given(requestSpecification)
                .header("Content-Type", "application/json")
                .log().all()
                .get("/products/{id}", res.getId())
                .then()
                .extract().body().as(Product.class);
        Assertions.assertEquals(res2.getProducer(), "producer");
    }

    @Test
    public void updateProductTest() throws InterruptedException, JsonProcessingException {
        Product obj = new Tv(2, 200.0, "name", "producer", "desc", "40", "8", "3", "IPS");
        String json = objectMapper.writeValueAsString(obj);

        //add product
        Product res = given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(Product.class);
        Assertions.assertEquals(res.getProducer(), "producer");

        //modify product
        res.setProducer("changed");

        String json2 = objectMapper.writeValueAsString(res);

        //update product
        Product res2 = given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json2)
                .log().all()
                .patch("/products/{id}", res.getId())
                .then()
                .extract().body().as(Product.class);
        Assertions.assertEquals(res2.getProducer(), "changed");
    }

    @Test
    public void deleteProductTest() throws InterruptedException, JsonProcessingException {
        Product obj = new Tv(2, 200.0, "name", "producer", "desc", "40", "8", "3", "IPS");
        String json = objectMapper.writeValueAsString(obj);

        //add product
        Product res = given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(Product.class);
        Assertions.assertEquals(res.getProducer(), "producer");

        //delete product
        given(requestSpecification)
                .header("Content-Type", "application/json")
                .log().all()
                .delete("/products/{id}", res.getId());

        //findById should return error
        String messageWithError = given(requestSpecification)
                .header("Content-Type", "application/json")
                .log().all()
                .get("/products/{id}", res.getId())
                .then()
                .statusCode(404)
                .extract().body().asString();
        assertTrue(messageWithError.contains("Entity with given ID doesn't exist"));
    }

    @Test
    public void addProductWithLongerThanRequiredScreensizeParameterValueTest() throws InterruptedException, JsonProcessingException {
        Product obj = new Tv(2, 200.0, "name", "producer", "desc", "ogroooooooomnyooooo", "8", "3", "IPS");
        String json = objectMapper.writeValueAsString(obj);

        //add product
        String messageWithExpectedError = given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/products")
                .then()
                .statusCode(400)
                .extract().body().asString();
        assertTrue(messageWithExpectedError.contains("The request sent by the client was syntactically incorrect."));
    }
    private Product getProductById(UUID id) {
        return given(requestSpecification)
                .header("Content-Type", "application/json")
                .log().all()
                .get("/products/{id}", id)
                .then()
                .extract().body().as(Product.class);
    }
}*/
