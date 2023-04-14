import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.nio.file.Paths;

@Testcontainers
public class TestContainerInitializer {
    static MountableFile warFile = MountableFile.forHostPath(
            Paths.get("target/rest-1.0-SNAPSHOT.war").toAbsolutePath(), 0777);

    public static String baseUri;

    public RequestSpecification requestSpecification;

    public static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void init() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = shopApp.getHost();
        RestAssured.port = shopApp.getMappedPort(8080);
        baseUri = "http://" + shopApp.getHost() + ":" + shopApp.getMappedPort(8080) + "/rest-1.0-SNAPSHOT/api/v1";
        requestSpecification = new RequestSpecBuilder().setBaseUri(baseUri).build();
    }

    @Container
    static GenericContainer shopApp = new GenericContainer("payara/server-full:6.2023.3-jdk17")
            .withExposedPorts(8080)
            .withCopyFileToContainer(warFile, "/opt/payara/deployments/rest-1.0-SNAPSHOT.war")
            .withCommand()
            .waitingFor(Wait.forHttp("/health/ready").forPort(8080).forStatusCode(200));
}
