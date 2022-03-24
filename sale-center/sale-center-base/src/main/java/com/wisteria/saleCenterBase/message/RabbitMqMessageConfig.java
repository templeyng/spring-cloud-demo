package com.wisteria.saleCenterBase.message;

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
    @Bean
    public DirectExchange saleOrderExchange() {
        //创建交换机：在这里以DirectExchange为例
        return new DirectExchange(DicConstant.SALE_ORDER_EXCHANGE, true, false);
    }

    //创建队列
    @Bean
    public Queue saleOrderStockOutQueue() {
        return new Queue(DicConstant.SALE_ORDER_STOCK_OUT_QUEUE, true);
    }

    //创建绑定
    @Bean
    public Binding saleOrderStockOutBinding() {
        return BindingBuilder.bind(saleOrderStockOutQueue()).to(saleOrderExchange()).with(DicConstant.SALE_ORDER_STOCK_OUT_ROUTING_KEY);
    }

    //创建队列
    @Bean
    public Queue saleOrderDbAddQueue() {
        return new Queue(DicConstant.SALE_ORDER_DB_ADD_QUEUE, true);
    }

    //创建绑定
    @Bean
    public Binding saleOrderDbAddBinding() {
        return BindingBuilder.bind(saleOrderDbAddQueue()).to(saleOrderExchange()).with(DicConstant.SALE_ORDER_DB_ADD_ROUTING_KEY);
    }

}
