import com.jayway.restassured.RestAssured;
import org.junit.Before;
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
            Paths.get("target/shopApp-1.0-SNAPSHOT.war").toAbsolutePath(), 0777);

    public static String baseUri;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = microContainer.getHost();
        RestAssured.port = microContainer.getMappedPort(8080);
        baseUri = "http://" + microContainer.getHost() + ":" + microContainer.getMappedPort(8080) + "/app";
    }

    @Container
    static GenericContainer microContainer = new GenericContainer("payara/micro:5.2021.9-jdk11")
            .withExposedPorts(8080)
            .withCopyFileToContainer(warFile, "/opt/payara/deployments/app.war")
            .waitingFor(Wait.forLogMessage(".* Payara Micro .* ready in .*\\s", 1));
}
