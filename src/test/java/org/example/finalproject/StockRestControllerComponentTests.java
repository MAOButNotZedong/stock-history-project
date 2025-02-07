package org.example.finalproject;

import org.example.finalproject.constants.EndPointPaths;
import org.example.finalproject.controller.StockRestController;
import org.example.finalproject.dto.stock.HistoryResponseDto;
import org.example.finalproject.dto.stock.PolygonResultDto;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.dto.user.UserRegistrationDto;
import org.example.finalproject.model.entity.Stock;
import org.example.finalproject.model.entity.User;
import org.example.finalproject.security.SecurityConfigForTests;
import org.example.finalproject.service.UserService;
import org.example.finalproject.service.polygon.IntegrationService;
import org.example.finalproject.service.polygon.getstrategy.AllStockGettingStrategy;
import org.example.finalproject.service.polygon.getstrategy.DefaultStockGettingStrategy;
import org.example.finalproject.service.stock.StockFacade;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Import(SecurityConfigForTests.class)
public class StockRestControllerComponentTests extends BaseForComponentTests {

    static final String USER_EMAIL = "al@gmail.com";
    static final String USER_PASSWORD = "1234";
    static final String TICKER_SYMBOL = "AL";
    static final String START_DATE = "2025-01-10";
    static final String END_DATE = "2025-01-30";
    static final String UNKNOWN_TICKER = "AFASDASFASD";
    @Autowired
    @MockitoBean
    protected IntegrationService integrationService;

    @Autowired
    @MockitoBean
    protected DefaultStockGettingStrategy defaultStockGettingStrategy;

    @Autowired
    @MockitoBean
    protected AllStockGettingStrategy allStockGettingStrategy;

    @Autowired
    protected UserService userService;

    @Autowired
    protected BCryptPasswordEncoder bCrypt;

    int userId;

    @BeforeEach
    void registerUserDb() throws Exception {

        UserRegistrationDto userDto = new UserRegistrationDto("al", USER_PASSWORD, USER_EMAIL);
        User newUser = User.builder()
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(bCrypt.encode(userDto.getPassword()))
                .build();
        userRepository.save(newUser);
        userId = newUser.getId();

        UserDetails userDetails = userService.loadUserByUsername(USER_EMAIL);
    }

    @AfterEach
    void deleteDb() throws Exception {
        userStockRepository.deleteAll();
        stockRepository.deleteAll();
        userRepository.deleteAll();
        tickerRepository.deleteAll();
    }

    @Test
    void saveStockWithoutAuthentication() throws Exception {
        StockSaveRequestDto stockSaveRequestDto = new StockSaveRequestDto(TICKER_SYMBOL, LocalDate.parse(START_DATE), LocalDate.parse(END_DATE));
        String s = objectToJsonString(stockSaveRequestDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(EndPointPaths.API_STOCK + EndPointPaths.SAVE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(s))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = USER_EMAIL, password = USER_PASSWORD)
    void saveStockWithUnknownTicker() throws Exception {
        StockSaveRequestDto stockSaveRequestDto = new StockSaveRequestDto(UNKNOWN_TICKER, LocalDate.parse(START_DATE), LocalDate.parse(END_DATE));
        String s = objectToJsonString(stockSaveRequestDto);

        Mockito.when(integrationService.isAvailableTicker(UNKNOWN_TICKER)).thenReturn(false);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(EndPointPaths.API_STOCK + EndPointPaths.SAVE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(s))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());

    }

    @Test
    @WithMockUser(username = USER_EMAIL, password = USER_PASSWORD)
    void saveStockWithEndBeforeStartDate() throws Exception {
        StockSaveRequestDto stockSaveRequestDto = new StockSaveRequestDto(TICKER_SYMBOL, LocalDate.parse(END_DATE), LocalDate.parse(START_DATE));
        String s = objectToJsonString(stockSaveRequestDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(EndPointPaths.API_STOCK + EndPointPaths.SAVE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(s))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = USER_EMAIL, password = USER_PASSWORD)
    void saveStockWithValidRequest() throws Exception {
        PolygonResultDto polygonResultDtoNum14 = objectMapper.readValue(new File("src/test/resources/polygon-14results.json"), PolygonResultDto.class);

        StockSaveRequestDto stockSaveRequestDto = new StockSaveRequestDto(TICKER_SYMBOL, LocalDate.parse(START_DATE), LocalDate.parse(END_DATE));
        String s = objectToJsonString(stockSaveRequestDto);

        Mockito.when(integrationService.isAvailableTicker(TICKER_SYMBOL)).thenReturn(true);
        Mockito.when(allStockGettingStrategy.getStock(Mockito.any(), Mockito.any())).thenReturn(polygonResultDtoNum14);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(EndPointPaths.API_STOCK + EndPointPaths.SAVE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(s))
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<Stock> stockHistory = stockRepository.getStockHistory(TICKER_SYMBOL, userId);
        Assertions.assertEquals(polygonResultDtoNum14.getStocks().size(), stockHistory.size());
    }

    @Test
    @WithMockUser(username = USER_EMAIL, password = USER_PASSWORD)
    void saveStockWithExistingAndNewDates() throws Exception {
        StockSaveRequestDto stockSaveRequestDto = new StockSaveRequestDto(TICKER_SYMBOL, LocalDate.parse("2025-01-15"), LocalDate.parse("2025-01-23"));
        String s = objectToJsonString(stockSaveRequestDto);
        PolygonResultDto polygonResultDtoNum14 = objectMapper.readValue(new File("src/test/resources/polygon-14results.json"), PolygonResultDto.class);
        PolygonResultDto polygonResultDtoNum6 = objectMapper.readValue(new File("src/test/resources/polygon-6results.json"), PolygonResultDto.class);


        Mockito.when(integrationService.isAvailableTicker(TICKER_SYMBOL)).thenReturn(true);
        Mockito.when(allStockGettingStrategy.getStock(Mockito.any(), Mockito.notNull())).thenReturn(polygonResultDtoNum6).thenReturn(polygonResultDtoNum14);
        Mockito.when(defaultStockGettingStrategy.getStock(Mockito.any(), Mockito.notNull())).thenReturn(polygonResultDtoNum14);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(EndPointPaths.API_STOCK + EndPointPaths.SAVE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(s))
                .andExpect(MockMvcResultMatchers.status().isOk());

        StockSaveRequestDto stockSaveRequestDto1= new StockSaveRequestDto(TICKER_SYMBOL, LocalDate.parse(START_DATE), LocalDate.parse(END_DATE));
        String s1 = objectToJsonString(stockSaveRequestDto1);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(EndPointPaths.API_STOCK + EndPointPaths.SAVE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(s1))
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<Stock> stockHistory = stockRepository.getStockHistory(TICKER_SYMBOL, userId);
        PolygonResultDto polygonResultDtoNumExpected = objectMapper.readValue(new File("src/test/resources/polygon-14results.json"), PolygonResultDto.class);
        Assertions.assertEquals(polygonResultDtoNumExpected.getStocks().size(), stockHistory.size());
    }

    @Test
    void getStockHistoryWithoutAuthentication() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get(EndPointPaths.API_STOCK + EndPointPaths.HISTORY + "/" + TICKER_SYMBOL))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = USER_EMAIL, password = USER_PASSWORD)
    void getStockHistoryAfterSaving() throws Exception {
        StockSaveRequestDto stockSaveRequestDto = new StockSaveRequestDto(TICKER_SYMBOL, LocalDate.parse(START_DATE), LocalDate.parse(END_DATE));
        String s = objectToJsonString(stockSaveRequestDto);

        PolygonResultDto polygonResultDtoNum14 = objectMapper.readValue(new File("src/test/resources/polygon-14results.json"), PolygonResultDto.class);
        Mockito.when(integrationService.isAvailableTicker(TICKER_SYMBOL)).thenReturn(true);
        Mockito.when(allStockGettingStrategy.getStock(Mockito.any(), Mockito.any())).thenReturn(polygonResultDtoNum14);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(EndPointPaths.API_STOCK + EndPointPaths.SAVE)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(s))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(
                        MockMvcRequestBuilders.get(EndPointPaths.API_STOCK + EndPointPaths.HISTORY + "/" + TICKER_SYMBOL))
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<Stock> stocks = stockRepository.getStockHistory(TICKER_SYMBOL, userId);

        Assertions.assertEquals(polygonResultDtoNum14.getStocks().size(), stocks.size());
    }


}

