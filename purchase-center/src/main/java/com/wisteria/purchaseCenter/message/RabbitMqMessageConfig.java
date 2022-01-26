package com.wisteria.purchaseCenter.message;

import com.wisteria.common.entity.base.DicConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMqMessageConfig {
    //创建队列
    @Bean
    public Queue basicQueue() {
        return new Queue(DicConstant.PURCHASE_ORDER_STOCK_IN_QUEUE, true);
    }

    //创建交换机：在这里以DirectExchange为例
    @Bean
    public DirectExchange basicExchange() {
        return new DirectExchange(DicConstant.PURCHASE_ORDER_EXCHANGE, true, false);
    }

    //创建绑定
    @Bean
    public Binding basicBinding() {
        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(DicConstant.PURCHASE_ORDER_ROUTING_KEY);
    }

}
