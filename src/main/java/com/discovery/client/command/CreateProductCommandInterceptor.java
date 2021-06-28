package com.discovery.client.command;

import com.discovery.client.entity.ProductLookUpEntity;
import com.discovery.client.repository.ProductLookUpRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private  static  final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);

    @Autowired
    private ProductLookUpRepository productLookUpRepository;

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle
            (List<? extends CommandMessage<?>> list) {
        return (index, command) -> {
            LOGGER.info(" Command ", command.getPayloadType());
            if (CreateProductCommand.class.equals(command.getPayloadType())) {
                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();

                ProductLookUpEntity productLookUpEntity = productLookUpRepository.findByProductIdOrTitle(createProductCommand.getProductId(), createProductCommand.getTitle());
                if(productLookUpEntity!=null){
                    throw new RuntimeException("product already exists with title "+createProductCommand.getTitle());
                }


            }
            return command;
        };
    }
}
