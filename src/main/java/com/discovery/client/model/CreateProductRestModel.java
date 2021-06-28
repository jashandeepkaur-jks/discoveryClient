package com.discovery.client.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateProductRestModel {

    @NotNull
    private String title;
    private BigDecimal price;
    private Integer quantity;

}
