package com.discovery.client;

import java.util.concurrent.ThreadLocalRandom;

import com.discovery.client.command.CreateProductCommandInterceptor;
import com.discovery.client.query.ProductServiceEventsErrorHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

	/*//@Value("${service.instance.name}")
	private String instance;

	@RequestMapping("/")
	public String message()
	{
		return "Helloo from  "+instance;
	}

	@RequestMapping("/data")
	public String data()
	{
		return "Helloo : server " ;
	}
	
	private String[] weather = {"sunny","cloudy","rainy","windy"};

	@RequestMapping("/weather")
	public String getWeather()
	{
		int random=ThreadLocalRandom.current().nextInt(0,4);
		return weather[random] ;
	}*/

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CommandBus commandBus;

    @Bean
    public void registerCreateCommandInterceptor() {
        commandBus.registerDispatchInterceptor(applicationContext.getBean(CreateProductCommandInterceptor.class));
    }

    @Autowired
    public void configure(EventProcessingConfigurer configurer){
        configurer.registerListenerInvocationErrorHandler("products-group",config-> new ProductServiceEventsErrorHandler());

    }

}
