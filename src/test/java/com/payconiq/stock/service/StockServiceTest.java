package com.payconiq.stock.service;

import com.payconiq.stock.controller.request.CreateStock;
import com.payconiq.stock.controller.request.UpdateStock;
import com.payconiq.stock.repository.StockRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {

    private final Long testId = 1L;

    @Mock
    private StockRepository stockRepository;

    private StockService stockService;

    @Before
    public void init() {
        stockService = new StockService(stockRepository);
    }

    @Test
    public void testAddStock() {
        CreateStock request = new CreateStock("test add stock", 1D);
        stockService.addStock(request);

        ArgumentCaptor<CreateStock> argCap = ArgumentCaptor.forClass(CreateStock.class);
        verify(stockRepository).addStock(argCap.capture());

        CreateStock testRequest = argCap.getValue();
        assertEquals(request.getName(), testRequest.getName());
        assertEquals(request.getCurrentPrice(), testRequest.getCurrentPrice());
    }

    @Test
    public void testGetStocks() {
        stockService.getStocks();
        verify(stockRepository).getStocks();
    }

    @Test
    public void testUpdateStock() throws Exception {
        UpdateStock request = new UpdateStock(2D);
        stockService.updateStock(testId, request);

        ArgumentCaptor<UpdateStock> argCap = ArgumentCaptor.forClass(UpdateStock.class);
        verify(stockRepository).updateStock(eq(testId), argCap.capture());

        UpdateStock testRequest = argCap.getValue();
        assertEquals(request.getCurrentPrice(), testRequest.getCurrentPrice());
    }

    @Test
    public void testGetStock() {
        stockService.getStock(testId);
        verify(stockRepository).getStock(testId);
    }
}
