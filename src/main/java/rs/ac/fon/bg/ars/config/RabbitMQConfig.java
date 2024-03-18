package rs.ac.fon.bg.ars.config;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@Getter
public class RabbitMQConfig {

    private final ConnectionFactory connectionFactory;

    public RabbitTemplate rabbitTemplate(){
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public DirectExchange directExchange(
            @Value("${rabbitmq.crud-operations.direct-exchange.name}")
            String DirectExchangeRoute){
        return new DirectExchange(DirectExchangeRoute);
    }


    @Bean
    public MessageConverter jacksonConverter(){
        return new Jackson2JsonMessageConverter();
    }


}
