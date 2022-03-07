package com.wisteria.saleCenter.message;

import com.alibaba.fastjson.JSON;
import com.wisteria.common.entity.base.DicConstant;
import com.wisteria.common.entity.product.SkuInventoryInOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SaleOrderPublisher {
    //定义RabbitMQ消息操作组件RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param sio 待发送的消息
     */
    public void sendMsg(SkuInventoryInOut sio) {
        try {
            //指定消息模型中的交换机
            rabbitTemplate.setExchange(DicConstant.PURCHASE_ORDER_EXCHANGE);
            //指定消息模型中的路由
            rabbitTemplate.setRoutingKey(DicConstant.PURCHASE_ORDER_ROUTING_KEY);
            //转化并发送消息
            rabbitTemplate.convertAndSend(JSON.toJSONString(sio));
            log.info("rabbitmq demo-生产者-发送消息：{} ", JSON.toJSONString(sio));
        } catch (Exception e) {
            log.error("rabbitmq demo-生产者-发送消息发生异常：{} ", sio, e.fillInStackTrace());
        }
    }
}
