package com.payconiq.stock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StockNotFoundException extends Exception {
    public StockNotFoundException(final String msg) {
        super(msg);
    }
}
