package com.discovery.client.aggregate;

import com.discovery.client.command.CreateProductCommand;
import com.discovery.client.command.MyCustomException;
import com.discovery.client.event.ProductCreatedEvent;
import com.service.core.command.CancelProductReservationCommand;
import com.service.core.command.ReserveProductCommand;
import com.service.core.event.ProductReservationCancelEvent;
import com.service.core.event.ProductReservedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {

    private  static  final Logger LOGGER = LoggerFactory.getLogger(ProductAggregate.class);


    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) throws MyCustomException{

        if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0)
        {
            throw  new RuntimeException(" Price can,t be Zero");
        }

        if(createProductCommand.getTitle()==null || createProductCommand.getTitle().trim().isEmpty())
        {
            throw new RuntimeException(" Title can,t be empty");
        }
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();

        BeanUtils.copyProperties(createProductCommand,productCreatedEvent);

        AggregateLifecycle.apply(productCreatedEvent);


    }

    @CommandHandler
    public void handle(ReserveProductCommand reserveProductCommand) throws MyCustomException{

        LOGGER.info("inside commandHandler : sending reserved event");

        if(quantity< reserveProductCommand.getQuantity())
        {
            throw  new RuntimeException("Insufficient quantity");
        }

       ProductReservedEvent productReservedEvent = ProductReservedEvent.builder().productId(reserveProductCommand.getProductId())
                .orderId(reserveProductCommand.getOrderId())
                .quantity(reserveProductCommand.getQuantity())
                .userId(reserveProductCommand.getUserId()).build();

        AggregateLifecycle.apply(productReservedEvent);


    }

    @CommandHandler
    public void handle(CancelProductReservationCommand cancelProductReservationCommand) throws MyCustomException{

        ProductReservationCancelEvent productReservationCancelEvent = new ProductReservationCancelEvent();
        BeanUtils.copyProperties(cancelProductReservationCommand, productReservationCancelEvent);
        AggregateLifecycle.apply(productReservationCancelEvent);

    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent)
    {
        this.productId = productCreatedEvent.getProductId();
        this.price = productCreatedEvent.getPrice();
        this.quantity = productCreatedEvent.getQuantity();
        this.title = productCreatedEvent.getTitle();
    }

    @EventSourcingHandler
    public void of(ProductReservedEvent productReservedEvent)
    {
        LOGGER.info(" subtracted quantity reserved event");
        this.quantity -= productReservedEvent.getQuantity();
    }

    @EventSourcingHandler
    public void of(ProductReservationCancelEvent productReservationCancelEvent)
    {
        LOGGER.info(" Added quantity reserved event");
        this.quantity += productReservationCancelEvent.getQuantity();
    }

}
