import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;
import com.pas.model.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class UserApiIntegrationTest extends TestContainerInitializer {
    @Test
    public void registerTwiceWithSameLoginThrowsErrorTest() throws InterruptedException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(baseUri).build();
        UserDTO obj = new UserDTO("Szymon", "Ziemecki", "login", "password", 120d);
        String json = objectMapper.writeValueAsString(obj);

        String res = given(req)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/users/register")
                .then()
                .extract().body().asString();
        UserDTO user = objectMapper.readValue(json, UserDTO.class);
        Assertions.assertEquals(user.getFirstName(), "Szymon");

        String message = given(req)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/users/register")
                .then()
                .statusCode(500)
                .extract().body().asString();
        assertTrue(message.contains("Login already taken"));
    }
}