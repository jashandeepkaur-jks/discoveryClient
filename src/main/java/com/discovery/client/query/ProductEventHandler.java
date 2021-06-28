package com.discovery.client.query;

import com.discovery.client.aggregate.ProductAggregate;
import com.discovery.client.entity.ProductEntity;
import com.discovery.client.event.ProductCreatedEvent;
import com.discovery.client.repository.ProductsRepository;
import com.service.core.event.ProductReservationCancelEvent;
import com.service.core.event.ProductReservedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@ProcessingGroup("products-group")
public class ProductEventHandler {

    private  static  final Logger LOGGER = LoggerFactory.getLogger(ProductEventHandler.class);

    @Autowired
    private ProductsRepository productsRepository;

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException argumentException){
//
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception ex) throws Exception {
        throw ex;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) throws Exception {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productCreatedEvent, productEntity);
        try {
            productsRepository.save(productEntity);
        }
        catch (IllegalArgumentException exception){
            exception.getMessage();
        }

       /* if(true)
            throw new Exception("Error happened in eventHandler");*/


    }

    @EventHandler
    public void on(ProductReservedEvent productReservedEvent) throws Exception {
      Optional<ProductEntity> entity = productsRepository.findById(productReservedEvent.getProductId());
      ProductEntity productEntity = entity.get();
      productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());
      productsRepository.save(productEntity);
      LOGGER.info("updated product quantity");

    }

    @EventHandler
    public void on(ProductReservationCancelEvent productReservationCancelEvent) throws Exception {
        Optional<ProductEntity> entity = productsRepository.findById(productReservationCancelEvent.getProductId());
        ProductEntity productEntity = entity.get();
        productEntity.setQuantity(productEntity.getQuantity() + productReservationCancelEvent.getQuantity());
        productsRepository.save(productEntity);
        LOGGER.info("reverted product quantity");

    }

}
