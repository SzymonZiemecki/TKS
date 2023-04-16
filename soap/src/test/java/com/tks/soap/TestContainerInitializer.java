package com.tks.soap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.nio.file.Paths;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestContainerInitializer {
    private static final DockerImageName PAYARA_IMAGE = DockerImageName
            .parse("payara/server-full")
            .withTag("6.2023.3-jdk17");
    private static final int PORT = 8080;
    private static final String PACKAGE_NAME = "soap-1.0-SNAPSHOT.war";
    private static final String CONTAINER_DEPLOYMENT_PATH = "/opt/payara/deployments/";

    public static String baseUri;

    public RequestSpecification requestSpecification;

    public static ObjectMapper objectMapper = new ObjectMapper();

    private static final Network network = Network.newNetwork();

    protected static GenericContainer microContainer = new GenericContainer(PAYARA_IMAGE)
            .withExposedPorts(PORT)
            .withCopyFileToContainer(
                    MountableFile.forHostPath("target/" + PACKAGE_NAME),
                    CONTAINER_DEPLOYMENT_PATH + PACKAGE_NAME)
            .withNetwork(network)
            .waitingFor(Wait.forHttp("/soap-1.0-SNAPSHOT/productSoapApi?wsdl").forPort(PORT).forStatusCode(200));

    @BeforeAll
    protected void setup() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        if (!microContainer.isRunning())
            microContainer.start();

        baseUri = "http://" +
                microContainer.getHost() + ":" +
                microContainer.getMappedPort(PORT);
    }
}
