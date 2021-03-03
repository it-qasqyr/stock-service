package com.payconiq.stock.integration;

import com.payconiq.stock.controller.request.CreateStock;
import com.payconiq.stock.model.Stock;
import com.payconiq.stock.repository.StockRepository;
import com.payconiq.stock.service.StockService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceTest {

    private static final int STOCKS_COUNT = 100;

    @Autowired
    private StockService stockService;

    @Test
    public void concurrentTest() throws Exception {
        final CountDownLatch doneLatch = new CountDownLatch(STOCKS_COUNT);
        try {
            for (int stock = 0; stock < STOCKS_COUNT; stock++) {
                new Thread(() -> {
                    // adds a stock
                    stockService.addStock(new CreateStock("test add stock", 1d));
                    doneLatch.countDown();
                }).start();
            }
            doneLatch.await();

            // gets all stocks have been added
            List<Stock> stocks = stockService.getStocks();
            assertEquals(STOCKS_COUNT, stocks.size());
        } catch (Exception e) {
            Assertions.fail(e.toString());
        }
    }

    public static class TestConfiguration {
        @Bean
        public StockRepository repository() {
            return new StockRepository();
        }

        @Bean
        public StockService service() {
            return new StockService(repository());
        }
    }
}
