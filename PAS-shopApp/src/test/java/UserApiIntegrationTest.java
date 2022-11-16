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
public class UserApiIntegrationTest extends TestContainerInitializer {

    @Test
    public void getUserTest() throws InterruptedException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(baseUri).build();
        UserDTO obj = new UserDTO("Szymon", "Ziemecki", "login", "password", 120d);
        String json = objectMapper.writeValueAsString(obj);

        //register the user
        UserDTO res = given(req)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/users/register")
                .then()
                .extract().body().as(UserDTO.class);
        Assertions.assertEquals(res.getFirstName(), "Szymon");

        //get the user
        UserDTO response = given(req)
                .header("Content-Type", "application/json")
                .log().all()
                .get("/users/{id}", res.getId())
                .then()
                .extract().body().as(UserDTO.class);

        assertEquals(response.getFirstName(), "Szymon");
    }

    @Test
    public void updateUserTest() throws InterruptedException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(baseUri).build();
        UserDTO obj = new UserDTO("Szymon", "Ziemecki", "login", "password", 120d);
        String json = objectMapper.writeValueAsString(obj);

        //register the user
        UserDTO res = given(req)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/users/register")
                .then()
                .extract().body().as(UserDTO.class);
        Assertions.assertEquals(res.getFirstName(), "Szymon");

        UserDTO obj2 = new UserDTO("Szymonek", "Ziemecki", "login", "password", 120d);
        String body2 = objectMapper.writeValueAsString(obj2);
        //update the user
        UserDTO res2 = given(req)
                .header("Content-Type", "application/json")
                .body(body2)
                .log().all()
                .put("/users/{id}", res.getId())
                .then()
                .extract().body().as(UserDTO.class);
        Assertions.assertEquals(res2.getFirstName(), "Szymonek");
    }

    @Test
    public void registerUserWithLongerThanRequiredNameTest() throws InterruptedException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(baseUri).build();
        UserDTO obj = new UserDTO("aaaaaaaaaaaaaaaaaaaandrzej", "Ziemecki", "login", "password", 120d);
        String json = objectMapper.writeValueAsString(obj);

        //registering user with exact same login should fail
        String messageWithExpectedError = given(req)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/users/register")
                .then()
                .statusCode(411)//not sure if correct code
                .extract().body().asString();
        assertTrue(messageWithExpectedError.contains("Length Required"));
    }

    @Test
    public void registerTwiceWithSameUUIDThrowsErrorTest() throws InterruptedException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(baseUri).build();
        UserDTO obj = new UserDTO("Szymon", "Ziemecki", "login", "password", 120d);
        String json = objectMapper.writeValueAsString(obj);

        //register once
        UserDTO res = given(req)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/users/register")
                .then()
                .extract().body().as(UserDTO.class);
        Assertions.assertEquals(res.getFirstName(), "Szymon");

        obj.setId(res.getId());
        json = objectMapper.writeValueAsString(obj);

        //registering user with exact same UUID should fail
        String messageWithExpectedError = given(req)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/users/register")
                .then()
                .statusCode(500) //dont know error code and message
                .extract().body().asString();
        assertTrue(messageWithExpectedError.contains("Login already taken"));
    }

}