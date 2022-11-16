import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;
import com.pas.model.Product.Product;
import com.pas.model.Product.Tv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class ProductApiIntegrationTest extends TestContainerInitializer {
    @Test
    public void addProductTest() throws InterruptedException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(baseUri).build();
        Product obj = new Tv(2, 200.0,"name","producer", "desc","40","8","3","IPS");
        String json = objectMapper.writeValueAsString(obj);

        //add product
        Product res = given(req)
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
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(baseUri).build();
        Product obj = new Tv(2, 200.0,"name","producer", "desc","40","8","3","IPS");
        String json = objectMapper.writeValueAsString(obj);

        //add product
        Product res = given(req)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(Product.class);
        Assertions.assertEquals(res.getProducer(), "producer");


        //update product
        Product res2 = given(req)
                .header("Content-Type", "application/json")
                .log().all()
                .get("/products/{id}", res.getId())
                .then()
                .extract().body().as(Product.class);
        Assertions.assertEquals(res2.getProducer(), "producer");
    }

    @Test
    public void updateProductTest() throws InterruptedException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(baseUri).build();
        Product obj = new Tv(2, 200.0,"name","producer", "desc","40","8","3","IPS");
        String json = objectMapper.writeValueAsString(obj);

        //add product
        Product res = given(req)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(Product.class);
        Assertions.assertEquals(res.getProducer(), "producer");

        Product obj2 = new Tv(2, 200.0,"name","differentProducer", "desc","40","8","3","IPS");
        String json2 = objectMapper.writeValueAsString(obj);
        //update product
        Product res2 = given(req)
                .header("Content-Type", "application/json")
                .body(json2)
                .log().all()
                .put("/products/{id}", res.getId())
                .then()
                .extract().body().as(Product.class);
        Assertions.assertEquals(res2.getProducer(), "differentProducer");
    }

    @Test
    public void deleteProductTest() throws InterruptedException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(baseUri).build();
        Product obj = new Tv(2, 200.0,"name","producer", "desc","40","8","3","IPS");
        String json = objectMapper.writeValueAsString(obj);

        //add product
        Product res = given(req)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(Product.class);
        Assertions.assertEquals(res.getProducer(), "producer");

        //delete product
        given(req)
                .header("Content-Type", "application/json")
                .log().all()
                .delete("/products/{id}", res.getId());

        //update product
        List<Product> res2 = given(req)
                .header("Content-Type", "application/json")
                .log().all()
                .get("/products")
                .then()
                .extract().body().jsonPath().getList(".", Product.class);
        Assertions.assertTrue(res2.isEmpty());
    }

    @Test
    public void addProductWithLongerThanRequiredScreensizeParameterValueTest() throws InterruptedException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(baseUri).build();
        Product obj = new Tv(2, 200.0,"name","producer", "desc","ogroooooooomny","8","3","IPS");
        String json = objectMapper.writeValueAsString(obj);

        //add product
        String messageWithExpectedError = given(req)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/products")
                .then()
                .statusCode(411)
                .extract().body().asString();
        Assertions.assertTrue(messageWithExpectedError.contains("Length Required"));
    }
}