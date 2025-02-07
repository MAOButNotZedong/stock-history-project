package org.example.finalproject;

import org.example.finalproject.service.polygon.IntegrationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public class StockRestControllerTests extends PostgresDatabaseContainerForTests {

    @Autowired
    @MockitoBean
    protected IntegrationService integrationService;

    BeforeAll
    public void beforeAll() {

    }

}
