package org.example.finalproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.finalproject.dao.StockRepository;
import org.example.finalproject.dao.TickerRepository;
import org.example.finalproject.dao.UserRepository;
import org.example.finalproject.dao.UserStockRepository;
import org.example.finalproject.service.stock.StockFacade;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("component-test")
@AutoConfigureMockMvc()
@ContextConfiguration(initializers = BaseForComponentTests.Initializer.class)
public class BaseForComponentTests {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected StockRepository stockRepository;

    @Autowired
    protected UserStockRepository userStockRepository;

    @Autowired
    protected TickerRepository tickerRepository;

    @Autowired
    protected ObjectMapper objectMapper;

    protected static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.0");

    static {
        postgres.start();
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        configurableApplicationContext.getBeanFactory().registerSingleton("filter-chain-for-test", SecurityFilterChain.class);
        }
    }


    @AfterAll
    static void stopContainers() {
        postgres.stop();
    }

    @SneakyThrows
    String objectToJsonString(Object object) {
        return objectMapper.writeValueAsString(object);
    }
}
