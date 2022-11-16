import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.pas.model.Address;
import com.pas.model.dto.UserDTO;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.nio.file.Paths;

import static com.jayway.restassured.RestAssured.given;
import static org.testcontainers.shaded.org.apache.commons.lang.BooleanUtils.isTrue;

@Testcontainers
public class integrationTest extends TestContainerInitializer {
    @Test
    public void checkEndpoint() throws InterruptedException, JsonProcessingException {
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
        UserDTO user = objectMapper.readValue(res, UserDTO.class);
        Assertions.assertEquals(user.getFirstName(), "Szymon");


/*        Response res = given(req)
                .when()
                .log().all()
                .header("Content-Type", "application/json")
                .body(json)
                .post("/users/register");*/
    }
}