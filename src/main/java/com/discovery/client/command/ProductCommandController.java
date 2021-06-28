package com.discovery.client.command;

import com.discovery.client.model.CreateProductRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductCommandController {


    private final Environment environment;

    private final CommandGateway commandGateway;

    @Autowired
    ProductCommandController(Environment environment, CommandGateway commandGateway) {
        this.environment = environment;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createProduct(@RequestBody CreateProductRestModel createProductRestModel) {
        //db persist
        CreateProductCommand createProductCommand = CreateProductCommand.builder().price(createProductRestModel.getPrice())
                .title(createProductRestModel.getTitle())
                .quantity(createProductRestModel.getQuantity())
                .productId(UUID.randomUUID().toString()).build();

        String value = commandGateway.sendAndWait(createProductCommand);
        return value;

    }
}
