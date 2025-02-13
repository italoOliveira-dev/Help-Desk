package br.com.estudo.microservice.emailservice.config;

import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitMQConfig {

    @Bean
    public SimpleMessageConverter jsonMessageConverter() {
        SimpleMessageConverter messageConverter = new SimpleMessageConverter();
        messageConverter.setAllowedListPatterns(List.of("models.*", "java.util.*", "java.lang.*"));
        return messageConverter;
    }
}
