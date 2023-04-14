import com.fasterxml.jackson.core.JsonProcessingException;
import com.tks.dto.AddressDTO;
import com.tks.dto.user.UserDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class UserApiIntegrationTest extends TestContainerInitializer {

    @Test
    public void registerAndGetUserTest() throws InterruptedException, JsonProcessingException {
        //register the user
        UserDTO res = registerUser("Szymon", "Ziemecki", "first");
        Assertions.assertEquals(res.getFirstName(), "Szymon");

        //get the user
        UserDTO response = getUserById(res.getId());

        assertEquals(response.getFirstName(), "Szymon");
        assertEquals(response.getLastName(), "Ziemecki");
    }

    @Test
    public void updateUserTest() throws InterruptedException, JsonProcessingException {
        //register the user
        UserDTO res = registerUser("Jakub", "Wardyn", "second");
        Assertions.assertEquals(res.getFirstName(), "Jakub");
        Assertions.assertEquals(res.getLastName(), "Wardyn");

        //modify user
        res.setLastName("changed");
        String body2 = objectMapper.writeValueAsString(res);

        //update the user
        UserDTO res2 = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(body2)
                .log().all()
                .put("/users/{id}", res.getId())
                .then()
                .extract().body().as(UserDTO.class);
        Assertions.assertEquals(res2.getFirstName(), "Jakub");
        Assertions.assertEquals(res2.getLastName(), "changed");
    }

    @Test
    public void registerUserWithLongerThanRequiredNameTest() throws InterruptedException, JsonProcessingException {
        UserDTO obj = UserDTO.builder()
                .firstName("aaaaaaaaaaaaaaaaaaaaaasasaaaaaaaaaaaaaaaaaaaaa")
                .lastName("sa")
                .login("dgeee")
                .password("password")
                .accountBalance(120d)
                .build();
        String json = objectMapper.writeValueAsString(obj);

        //registering user with exact same login should fail
        String messageWithExpectedError = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/users/register")
                .then()
                .statusCode(400)//not sure if correct code
                .extract().body().asString();
        assertTrue(messageWithExpectedError.contains("Bad request"));
    }

    @Test
    public void registerTwiceWithSameLoginThrowsErrorTest() throws InterruptedException, JsonProcessingException {
        //register once
        UserDTO res = registerUser("Name", "LastName", "sameLogin");
        String json = objectMapper.writeValueAsString(res);

        //registering user with exact same UUID should fail
        String messageWithExpectedError = RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(json)
                .log().all()
                .post("/users/register")
                .then()
                .statusCode(500)
                .extract().body().asString();
        assertTrue(messageWithExpectedError.contains("Login already taken"));
    }

    private UserDTO registerUser(String name, String surname, String login) throws JsonProcessingException {
        UserDTO obj = UserDTO.builder()
                .firstName(name)
                .lastName(surname)
                .login(login)
                .password("password")
                .accountBalance(120d)
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

    private UserDTO getUserById(UUID id) {
        return RestAssured.given(requestSpecification)
                .header("Content-Type", "application/json")
                .log().all()
                .get("/users/{id}", id)
                .then()
                .extract().body().as(UserDTO.class);
    }
}
