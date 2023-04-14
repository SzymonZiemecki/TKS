import com.fasterxml.jackson.core.JsonProcessingException;
import com.tks.dto.product.ProductDTO;
import com.tks.dto.product.TvDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.junit.Assert.assertTrue;


@Testcontainers
public class ProductApiIntegrationTest extends TestContainerInitializer {
    @Test
    public void addProductTest() throws InterruptedException, JsonProcessingException {
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
        String json = objectMapper.writeValueAsString(obj);

        //add product
        ProductDTO res = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(ProductDTO.class);
        Assertions.assertEquals(res.getProducer(), "producer");
    }

    @Test
    public void readProductDTOTest() throws InterruptedException, JsonProcessingException {
        ProductDTO obj = TvDTO.builder()
                .name("name")
                .producer("producer")
                .productDescription("description")
                .price(20.2)
                .availableAmount(20)
                .refreshRate("fast")
                .resolution("big")
                .panelType("eesss")
                .screenSize("koxxx").build();
        String json = objectMapper.writeValueAsString(obj);

        //add product
        ProductDTO res = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(ProductDTO.class);
        Assertions.assertEquals(res.getProducer(), "producer");


        //get product
        ProductDTO res2 = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .log().all()
                .get("/products/{id}", res.getId())
                .then()
                .extract().body().as(ProductDTO.class);
        Assertions.assertEquals(res2.getProducer(), "producer");
    }

    @Test
    public void updateProductDTOTest() throws InterruptedException, JsonProcessingException {
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
        String json = objectMapper.writeValueAsString(obj);

        //add product
        ProductDTO res = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(ProductDTO.class);
        Assertions.assertEquals(res.getProducer(), "producer");

        //modify product
        res.setProducer("changed");

        String json2 = objectMapper.writeValueAsString(res);

        //update product
        ProductDTO res2 = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json2)
                .log().all()
                .put("/products/{id}", res.getId())
                .then()
                .extract().body().as(ProductDTO.class);
        Assertions.assertEquals(res2.getProducer(), "changed");
    }

    @Test
    public void deleteProductDTOTest() throws InterruptedException, JsonProcessingException {
        ProductDTO obj = TvDTO.builder()
                .name("name")
                .producer("producer")
                .productDescription("description")
                .price(20.2)
                .availableAmount(20)
                .refreshRate("fast")
                .resolution("big")
                .panelType("eesss")
                .screenSize("koxxx").build();
        String json = objectMapper.writeValueAsString(obj);

        //add product
        ProductDTO res = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/products")
                .then()
                .extract().body().as(ProductDTO.class);
        Assertions.assertEquals(res.getProducer(), "producer");

        //delete product
        RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .log().all()
                .delete("/products/{id}", res.getId())
                .then().statusCode(200);

        //findById should return error
        String messageWithError = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .log().all()
                .get("/products/{id}", res.getId())
                .then()
                .statusCode(404)
                .extract().body().asString();
        assertTrue(messageWithError.contains("Entity doesn't exist"));
    }

    @Test
    public void addProductDTOWithLongerThanRequiredScreensizeParameterValueTest() throws InterruptedException, JsonProcessingException {
        ProductDTO obj = TvDTO.builder()
                .name("name")
                .producer("producer")
                .productDescription("description")
                .price(20.2)
                .availableAmount(20)
                .refreshRate("fast")
                .resolution("big")
                .panelType("eesss")
                .screenSize("kox").build();
        String json = objectMapper.writeValueAsString(obj);

        //add product
        String messageWithExpectedError = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/products")
                .then()
                .statusCode(400)
                .extract().body().asString();
        assertTrue(messageWithExpectedError.contains("Bad request"));
    }
    private ProductDTO getProductDTOById(UUID id) {
        return RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .log().all()
                .get("/products/{id}", id)
                .then()
                .extract().body().as(ProductDTO.class);
    }
}
