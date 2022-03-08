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
    public DirectExchange stockInoutExchange() {
        return new DirectExchange(DicConstant.STOCK_INOUT_EXCHANGE, true, false);
    }

    //创建队列
    @Bean
    public Queue stockInoutRedisQueue() {
        return new Queue(DicConstant.STOCK_INOUT_REDIS_QUEUE, true);
    }

    //创建绑定
    @Bean
    public Binding stockInoutRedisBinding() {
        return BindingBuilder.bind(stockInoutRedisQueue()).to(stockInoutExchange()).with(DicConstant.STOCK_INOUT_REDIS_ROUTING_KEY);
    }

    //创建队列
    @Bean
    public Queue stockFlowDbAddQueue() {
        return new Queue(DicConstant.STOCK_FLOW_DB_ADD_QUEUE, true);
    }

    //创建绑定
    @Bean
    public Binding stockFlowDbAddBinding() {
        return BindingBuilder.bind(stockFlowDbAddQueue()).to(stockInoutExchange()).with(DicConstant.STOCK_FLOW_DB_ADD_ROUTING_KEY);
    }

}
