package br.com.estudo.microservice.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApiApplication.class, args);
    }

}
