package com.payconiq.stock.controller;

import com.payconiq.stock.controller.request.CreateStock;
import com.payconiq.stock.controller.request.UpdateStock;
import com.payconiq.stock.service.StockService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = StockController.class)
public class StockControllerTest {

    private static final String STOCK_URL_TEMPLATE = "/api/stocks";
    private final Long testId = 1L;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StockService stockService;

    @Test
    public void testAddStock() throws Exception {
        mvc.perform(
                post(STOCK_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"test add stock\", \"currentPrice\":\"1\"}"))
                .andExpect(status().isOk());

        ArgumentCaptor<CreateStock> argCap = ArgumentCaptor.forClass(CreateStock.class);
        verify(stockService, times(1)).addStock(argCap.capture());

        CreateStock request = argCap.getValue();
        assertThat(request, Matchers.allOf(
                Matchers.hasProperty("name", Matchers.is("test add stock")),
                Matchers.hasProperty("currentPrice", Matchers.is(1D))));
    }

    @Test
    public void testUpdateStock() throws Exception {
        mvc.perform(
                put(STOCK_URL_TEMPLATE + "/" + testId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"currentPrice\":\"2\"}"))
                .andExpect(status().isOk());

        ArgumentCaptor<UpdateStock> argCap = ArgumentCaptor.forClass(UpdateStock.class);
        verify(stockService, times(1)).updateStock(eq(testId), argCap.capture());

        UpdateStock request = argCap.getValue();
        assertEquals(2, request.getCurrentPrice(), 0);
    }

    @Test
    public void testShowStocks() throws Exception {
        mvc.perform(
                get(STOCK_URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(stockService, times(1)).getStocks();
    }

    @Test
    public void testShowStock() throws Exception {
        mvc.perform(
                get(STOCK_URL_TEMPLATE + "/" + testId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(stockService, times(1)).getStock(eq(testId));
    }
}
