package com.payconiq.stock.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStock {
    @NotNull
    private String name;
    @NotNull
    private Double currentPrice;
}
