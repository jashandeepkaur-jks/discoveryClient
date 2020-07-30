package com.discovery.client;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	//@Value("${service.instance.name}")
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
	}

}
