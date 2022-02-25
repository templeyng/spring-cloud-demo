package com.wisteria.saleCenter.message.send;

import com.alibaba.fastjson.JSON;
import com.wisteria.common.entity.base.DicConstant;
import com.wisteria.saleCenter.entity.SaleOrderLd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SaleOrderDbAddPublisher {
    //定义RabbitMQ消息操作组件RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param saleOrderLd 待发送的消息
     */
    public void sendMsg(SaleOrderLd saleOrderLd) {
        try {
            //指定消息模型中的交换机
            rabbitTemplate.setExchange(DicConstant.SALE_ORDER_EXCHANGE);
            //指定消息模型中的路由
            rabbitTemplate.setRoutingKey(DicConstant.SALE_ORDER_DB_ADD_ROUTING_KEY);
            //转化并发送消息
            rabbitTemplate.convertAndSend(JSON.toJSONString(saleOrderLd));
            log.info("SaleOrderDbAddPublisher-发送消息：{} ", JSON.toJSONString(saleOrderLd));
        } catch (Exception e) {
            log.error("SaleOrderDbAddPublisher-发送消息发生异常：{} ", saleOrderLd, e.fillInStackTrace());
        }
    }
}
