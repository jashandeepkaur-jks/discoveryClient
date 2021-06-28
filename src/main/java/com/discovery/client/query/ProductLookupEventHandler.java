package com.discovery.client.query;

import com.discovery.client.entity.ProductLookUpEntity;
import com.discovery.client.event.ProductCreatedEvent;
import com.discovery.client.repository.ProductLookUpRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("products-group")
public class ProductLookupEventHandler {

    @Autowired
    private ProductLookUpRepository productLookUpRepository;

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        ProductLookUpEntity lookUpEntity = new ProductLookUpEntity(productCreatedEvent.getProductId(),productCreatedEvent.getTitle());
        productLookUpRepository.save(lookUpEntity);


    }
}
