package com.wisteria.stockCenterBase.messsage;

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
    public DirectExchange purchaseOrderExchange() {
        return new DirectExchange(DicConstant.PURCHASE_ORDER_EXCHANGE, true, false);
    }

    //创建队列
    @Bean
    public Queue purchaseOrderDbAddQueue() {
        return new Queue(DicConstant.PURCHASE_ORDER_DB_ADD_QUEUE, true);
    }

    //创建绑定
    @Bean
    public Binding purchaseOrderDbAddBinding() {
        return BindingBuilder.bind(purchaseOrderDbAddQueue()).to(purchaseOrderExchange()).with(DicConstant.PURCHASE_ORDER_DB_ADD_ROUTING_KEY);
    }

    //创建队列
    @Bean
    public Queue purchaseOrderStockInQueue() {
        return new Queue(DicConstant.PURCHASE_ORDER_STOCK_IN_QUEUE, true);
    }

    //创建绑定
    @Bean
    public Binding purchaseOrderStockInBinding() {
        return BindingBuilder.bind(purchaseOrderStockInQueue()).to(purchaseOrderExchange()).with(DicConstant.PURCHASE_ORDER_STOCK_IN_ROUTING_KEY);
    }

}
