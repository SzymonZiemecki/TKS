package com.tks.soap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.MountableFile;

import java.nio.file.Paths;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestContainerInitializer {
    static MountableFile warFile = MountableFile.forHostPath(
            Paths.get("target/soap-1.0-SNAPSHOT.war").toAbsolutePath(), 0777);

    public static String baseUri;

    public RequestSpecification requestSpecification;

    public static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void init() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = microContainer.getHost();
        RestAssured.port = microContainer.getMappedPort(8080);
        baseUri = "http://" + microContainer.getHost() + ":" + microContainer.getMappedPort(8080) + "/app";
        requestSpecification = new RequestSpecBuilder().setBaseUri(baseUri).build();
    }

    @Container
    static GenericContainer microContainer = new GenericContainer("payara/micro:5.2021.9-jdk11")
            .withExposedPorts(8080)
            .withCopyFileToContainer(warFile, "/opt/payara/deployments/app.war")
            .withCommand()
            .waitingFor(Wait.forHttp("/soap-1.0-SNAPSHOT/productSoapApi?wsdl"));
}
