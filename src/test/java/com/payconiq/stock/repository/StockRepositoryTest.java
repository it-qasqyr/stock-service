package com.payconiq.stock.repository;

import com.payconiq.stock.controller.request.CreateStock;
import com.payconiq.stock.controller.request.UpdateStock;
import com.payconiq.stock.model.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockRepositoryTest.TestConfiguration.class})
public class StockRepositoryTest {

    public static final Long testId = 2L;
    public static final Map<Long, Stock> stocks = Map.of(testId, new Stock(testId, "test", 1D, new Timestamp(System.currentTimeMillis())));

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void testAddStock() throws Exception {
        String name = "test add stock";
        Double currentPrice = 1D;
        CreateStock request = new CreateStock(name, 1d);

        Stock stock = stockRepository.addStock(request);
        assertEquals(name, stock.getName());
        assertEquals(currentPrice, stock.getCurrentPrice());
    }

    @Test
    public void testGetStocks() throws Exception {
        List<Stock> stocks = stockRepository.getStocks();
        assertEquals(2, stocks.size());
        assertEquals("test add stock", stocks.get(0).getName());
        assertEquals("test", stocks.get(1).getName());
    }

    @Test
    public void testGetStock() throws Exception {
        Stock stock = stockRepository.getStock(testId);
        assertEquals("test", stock.getName());
    }

    @Test
    public void testUpdateStock() throws Exception {
        Double currentPrice = 2D;
        UpdateStock request = new UpdateStock(currentPrice);
        stockRepository.updateStock(testId, request);

        Stock stock = stockRepository.getStock(testId);
        assertEquals(currentPrice, stock.getCurrentPrice(), 0);
    }

    public static class TestConfiguration {
        @Bean
        public StockRepository repository() {
            return new StockRepository(stocks);
        }
    }
}
