package com.wisteria.saleCenter.message;

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

    //创建交换机：在这里以DirectExchange为例
    @Bean
    public DirectExchange saleOrderExchange() {
        return new DirectExchange(DicConstant.SALE_ORDER_EXCHANGE, true, false);
    }

    //创建队列 --销售出库
    @Bean
    public Queue saleOrderStockOutQueue() {
        return new Queue(DicConstant.SALE_ORDER_STOCK_OUT_QUEUE, true);
    }

    //创建绑定 --销售出库
    @Bean
    public Binding saleOrderStockOutBinding() {
        return BindingBuilder.bind(saleOrderStockOutQueue()).to(saleOrderExchange()).with(DicConstant.SALE_ORDER_STOCK_OUT_ROUTING_KEY);
    }

    //创建队列 --增加销售订单
    @Bean
    public Queue saleOrderDbAddQueue() {
        return new Queue(DicConstant.SALE_ORDER_DB_ADD_QUEUE, true);
    }

    //创建绑定 --增加销售订单
    @Bean
    public Binding saleOrderDbAddBinding() {
        return BindingBuilder.bind(saleOrderDbAddQueue()).to(saleOrderExchange()).with(DicConstant.SALE_ORDER_DB_ADD_ROUTING_KEY);
    }

    //创建队列 -- 秒杀队列
    @Bean
    public Queue secKillInitializeQueue() {
        return new Queue(DicConstant.SEC_KILL_INITIALIZE_QUEUE, true);
    }

    //创建绑定 --秒杀队列
    @Bean
    public Binding secKillInitializeBinding() {
        return BindingBuilder.bind(secKillInitializeQueue()).to(saleOrderExchange()).with(DicConstant.SEC_KILL_INITIALIZE_ROUTING_KEY);
    }

}
