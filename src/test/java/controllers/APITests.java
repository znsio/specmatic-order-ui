package controllers;

import com.intuit.karate.junit5.Karate;
import in.specmatic.stub.HttpStub;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static in.specmatic.stub.API.createStub;

public class APITests {

    public static final String SPECMATIC_STUB_HOST = "localhost";
    public static final int SPECMATIC_STUB_PORT = 9000;
    private static ConfigurableApplicationContext context;
    private static HttpStub stub;

    @Karate.Test
    public Karate runTests() {
        return new Karate().path("classpath:controllers/apiTests.feature");
    }

    @BeforeAll
    public static void setUp() {
        context = SpringApplication.run(Application.class);
        stub = createStub(SPECMATIC_STUB_HOST, SPECMATIC_STUB_PORT);
    }

    @AfterAll
    public static void tearDown() {
        if (context != null) {
            context.close();
        }
        if (stub != null) {
            stub.close();
        }
    }
}
