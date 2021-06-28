package com.discovery.client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document("productlookup")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductLookUpEntity implements Serializable {

    @Id
    private  String productId;
    private  String title;
}
