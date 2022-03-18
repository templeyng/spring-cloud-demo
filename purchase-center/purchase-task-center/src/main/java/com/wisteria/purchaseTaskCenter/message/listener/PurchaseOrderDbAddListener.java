package com.wisteria.purchaseTaskCenter.message.listener;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.wisteria.common.entity.base.DicConstant;
import com.wisteria.purchaseCenterBase.entity.PurchaseOrder;
import com.wisteria.purchaseTaskCenter.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class PurchaseOrderDbAddListener {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    /**
     * 监听并接收消费队列中的消息-在这里采用单一容器工厂实例即可
     */
    @RabbitListener(queues = DicConstant.PURCHASE_ORDER_DB_ADD_QUEUE, containerFactory = "singleListenerContainer")
    public void consumeMsg(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
        try {
            log.info("PurchaseOrderDbAddListener-消费者监听消费消息:{},消息投递标签：{}", message, tag);
            final PurchaseOrder purchaseOrder = JSON.parseObject(message, PurchaseOrder.class);
            purchaseOrderService.insertPurchaseOrder(purchaseOrder);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            purchaseOrderService.insertPurchaseMqError(message);
            channel.basicAck(tag, false);
            log.error("PurchaseOrderDbAddListener-消费者监听消费消息:{},消息投递标签：{},发生异常：", message, tag, e);
        }
    }
}
