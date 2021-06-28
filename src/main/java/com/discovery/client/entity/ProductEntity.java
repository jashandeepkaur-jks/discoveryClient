package com.discovery.client.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;

@Document("products")
@Data
public class ProductEntity implements Serializable {

    @Id
    private  String productId;
    private  String title;
    private BigDecimal price;
    private  Integer quantity;
}
