package controllers;

import com.intuit.karate.junit5.Karate;
import in.specmatic.stub.HttpStub;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.List;

import static in.specmatic.stub.API.createStubFromContracts;

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
        List<String> contracts = Arrays.asList("/home/runner/work/specmatic-order-ui/specmatic-order-ui/contracts/in/specmatic/examples/store/api_order_v1.yaml");
        List<String> data = Arrays.asList("/home/runner/work/specmatic-order-ui/specmatic-order-ui/contracts/in/specmatic/examples/store/api_order_v1_data");
        stub = createStubFromContracts(contracts, data, SPECMATIC_STUB_HOST, SPECMATIC_STUB_PORT);
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
