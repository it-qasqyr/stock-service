package com.payconiq.stock.controller;

import com.payconiq.stock.controller.request.CreateStock;
import com.payconiq.stock.controller.request.UpdateStock;
import com.payconiq.stock.model.Stock;
import com.payconiq.stock.service.StockService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StockController {

    @Autowired
    StockService stockService;

    /**
     * adds a stock
     *
     * @return
     */
    @PostMapping(value = "/api/stocks")
    public Stock addStock(@Valid @RequestBody CreateStock request) {
        return stockService.addStock(request);
    }

    /**
     * update price for a single stock
     *
     * @return
     */
    @SneakyThrows
    @PutMapping(value = "/api/stocks/{id}")
    public Stock updateStock(@PathVariable Long id, @Valid @RequestBody UpdateStock request) {
        return stockService.updateStock(id, request);
    }

    /**
     * returns the list of stocks
     *
     * @return
     */
    @GetMapping("/api/stocks")
    public List<Stock> showStocks() {
        return stockService.getStocks();
    }

    /**
     * returns the single stock
     *
     * @return
     */
    @GetMapping(value = "/api/stocks/{id}")
    public Stock showStock(@PathVariable Long id) {
        return stockService.getStock(id);
    }
}
