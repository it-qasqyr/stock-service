package com.payconiq.stock.service;

import com.payconiq.stock.controller.request.CreateStock;
import com.payconiq.stock.controller.request.UpdateStock;
import com.payconiq.stock.model.Stock;
import com.payconiq.stock.repository.StockRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public synchronized Stock addStock(CreateStock request) {
        return stockRepository.addStock(request);
    }

    @SneakyThrows
    public synchronized Stock updateStock(Long id, UpdateStock request) {
        return stockRepository.updateStock(id, request);
    }

    public synchronized List<Stock> getStocks() {
        return stockRepository.getStocks();
    }

    public synchronized Stock getStock(Long id) {
        return stockRepository.getStock(id);
    }
}
