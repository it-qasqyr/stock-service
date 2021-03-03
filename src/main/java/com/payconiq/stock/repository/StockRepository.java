package com.payconiq.stock.repository;

import com.payconiq.stock.controller.request.CreateStock;
import com.payconiq.stock.controller.request.UpdateStock;
import com.payconiq.stock.exception.StockNotFoundException;
import com.payconiq.stock.model.Stock;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Repository
// @ThreadSafe
public class StockRepository {

    private final ConcurrentHashMap<Long, Stock> stocks;
    private final Map<Long, Stock> unmodifiableMap;
    private long id = 0;

    public StockRepository() {
        stocks = new ConcurrentHashMap<>();
        this.unmodifiableMap = Collections.unmodifiableMap(stocks);
    }

    public StockRepository(Map<Long, Stock> stocks) {
        this.stocks = new ConcurrentHashMap<>(stocks);
        this.unmodifiableMap = Collections.unmodifiableMap(this.stocks);
    }

    public Stock addStock(CreateStock request) {
        Long id = increment();
        Stock stock = new Stock(id, request.getName(), request.getCurrentPrice(), new Timestamp(System.currentTimeMillis()));
        stocks.put(id, stock);

        log.info("Successfully added {} stock", stock);
        return stock;
    }

    public Stock updateStock(Long id, UpdateStock request) throws Exception {
        Stock stock = getStock(id);

        if (stock == null) {
            log.info("No stock is found for update {}", id);
            throw new StockNotFoundException("No stock is found for update " + id);
        }
        stocks.replace(id, new Stock(stock.getId(), stock.getName(), request.getCurrentPrice(), new Timestamp(System.currentTimeMillis())));

        log.info("Successfully updated {} stock", id);
        return getStock(id);
    }

    public List<Stock> getStocks() {
        List<Stock> stocks = new ArrayList<>(unmodifiableMap.values());

        log.info("Successfully fetched {} stocks", stocks);
        return stocks;
    }

    public Stock getStock(Long id) {
        return stocks.get(id);
    }

    public synchronized long increment() {
        return ++id;
    }
}
