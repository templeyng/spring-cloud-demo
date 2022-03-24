package com.wisteria.stockTaskCenter.messsage.listener;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.wisteria.common.entity.base.DicConstant;
import com.wisteria.common.entity.product.SkuInventoryInOut;
import com.wisteria.stockCenterBase.entity.SkuStockFlow;
import com.wisteria.stockTaskCenter.service.SkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class StockFlowDbAddListener {

    @Autowired
    private SkuStockService skuStockService;

    @RabbitListener(queues = DicConstant.STOCK_FLOW_DB_ADD_QUEUE, containerFactory = "singleListenerContainer")
    public void consumeMsg(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
        try {
            log.info("StockFlowDbAddListener-消费者监听消费消息:{},消息投递标签：{}", message, tag);
            final SkuInventoryInOut skuInventoryInOut = JSON.parseObject(message, SkuInventoryInOut.class);
            SkuStockFlow skuStockFlow = new SkuStockFlow();
            skuStockFlow.setSkuCode(skuInventoryInOut.getSkuCode());
            skuStockFlow.setType(skuInventoryInOut.getType());
            skuStockFlow.setQuantity(skuInventoryInOut.getCount());
            skuStockFlow.setSerialNumber(skuInventoryInOut.getType() + "-" + skuInventoryInOut.getParentId() + "-" + skuInventoryInOut.getItemId());
            log.info("StockFlowDbAddListener-录入数据库:{}", skuStockFlow);
            skuStockService.insertSkuStockFlow(skuStockFlow);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            skuStockService.insertSkuStockMqError(message);
            channel.basicAck(tag, false);
            log.error("基于MANUAL的手工确认消费模式-消费者监听消费消息:{},消息投递标签：{},发生异常：", message, tag, e);
        }
    }
}
