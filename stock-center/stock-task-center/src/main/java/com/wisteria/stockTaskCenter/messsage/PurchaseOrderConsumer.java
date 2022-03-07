package com.wisteria.stockTaskCenter.messsage;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.wisteria.common.entity.base.DicConstant;
import com.wisteria.common.entity.product.SkuInventoryInOut;
import com.wisteria.common.entity.product.SkuStock;
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
public class PurchaseOrderConsumer {

    @Autowired
    private SkuStockService skuStockService;

    /**
     * 监听并接收消费队列中的消息-在这里采用单一容器工厂实例即可
     */
    @RabbitListener(queues = DicConstant.PURCHASE_ORDER_STOCK_IN_ROUTING_KEY, containerFactory = "singleListenerContainer")
    public void consumeMsg(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
        try {
            log.info("基于MANUAL的手工确认消费模式-消费者监听消费消息,消息投递标记：{},内容为：{} ", tag, message);
            //抛异常,归入使得消息重新归入队列
            //执行完业务逻辑后，手动进行确认消费，其中第一个参数为：消息的分发标识(全局唯一);第二个参数：是否允许批量确认消费
            final SkuInventoryInOut skuInventoryInOut = JSON.parseObject(message, SkuInventoryInOut.class);
            SkuStockFlow skuStockFlow = new SkuStockFlow();
            skuStockFlow.setSkuCode(skuInventoryInOut.getSkuCode());
            skuStockFlow.setType(skuInventoryInOut.getType());
            skuStockFlow.setQuantity(skuInventoryInOut.getCount());
            skuStockFlow.setSerialNumber(skuInventoryInOut.getType() + "-" + skuInventoryInOut.getParentId() + "-" + skuInventoryInOut.getItemId());
            skuStockService.insertSkuStockFlow(skuStockFlow);
            SkuStock skuStock = skuStockService.loadSkuStockBySkuCode(skuInventoryInOut.getSkuCode());
            if (skuStock == null) {
                skuStock = new SkuStock();
                skuStock.setSkuCode(skuInventoryInOut.getSkuCode());
                skuStock.setInventoryAvailable(skuInventoryInOut.getCount());
                skuStock.setInventoryTotal(skuInventoryInOut.getCount());
                skuStockService.insertSkuStock(skuStock);
            } else {
                skuStock.setInventoryAvailable(skuStock.getInventoryAvailable() + skuInventoryInOut.getCount());
                skuStock.setInventoryTotal(skuStock.getInventoryTotal() + skuInventoryInOut.getCount());
                skuStockService.updateSkuStock(skuStock);
            }
            channel.basicAck(tag, false);
        } catch (Exception e) {
            //第二个参数reueue重新归入队列,true的话会重新归入队列,需要人为地处理此次异常消息,重新归入队列也会继续异常
            skuStockService.insertSkuStockMqError(message);
            channel.basicAck(tag, false);
//            channel.basicReject(tag, true);
            log.error("基于MANUAL的手工确认消费模式-消费者监听消费消息:{},消息投递标签：{},发生异常：", message, tag, e);
        }
    }
}
