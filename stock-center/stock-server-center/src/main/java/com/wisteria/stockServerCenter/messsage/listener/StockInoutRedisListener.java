package com.wisteria.stockServerCenter.messsage.listener;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.wisteria.common.entity.base.DicConstant;
import com.wisteria.common.entity.product.SkuInventoryInOut;
import com.wisteria.common.utils.StockRedisTemplate;
import com.wisteria.stockCenterBase.exception.InsertStockRedisException;
import com.wisteria.stockServerCenter.messsage.send.StockFlowDbAddPublisher;
import com.wisteria.stockServerCenter.service.SkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class StockInoutRedisListener {

    @Autowired
    private SkuStockService skuStockService;

    @Autowired
    private StockRedisTemplate stockRedisTemplate;

    @Autowired
    private StockFlowDbAddPublisher stockFlowDbAddPublisher;

    @RabbitListener(queues = DicConstant.STOCK_INOUT_REDIS_QUEUE, containerFactory = "singleListenerContainer")
    public void consumeMsg(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
        try {
            log.info("库存缓存录入开始：{}", message);
            final SkuInventoryInOut sio = JSON.parseObject(message, SkuInventoryInOut.class);
            Object skuQuantityObject = stockRedisTemplate.get(DicConstant.SKU_STOCK_INVENTORY_AVAILABLE + sio.getSkuCode());
            if (skuQuantityObject == null) {
                log.info("新增SKU库存缓存：SKU：{},Count：{}", sio.getSkuCode(), sio.getCount());
                if (sio.getCount() > 0) {
                    log.info("新增成功SKU库存缓存：SKU：{},Count：{}", sio.getSkuCode(), sio.getCount());
                    stockRedisTemplate.set(DicConstant.SKU_STOCK_INVENTORY_AVAILABLE + sio.getSkuCode(), sio.getCount());
                } else {
                    throw new InsertStockRedisException();
                }
            } else {
                log.info("修改SKU库存缓存：SKU：{},Count：{}", sio.getSkuCode(), sio.getCount());
                int skuQuantity = (int) skuQuantityObject;
                int skuQuantityResult = skuQuantity + sio.getCount();
                if (skuQuantityResult >= 0) {
                    log.info("修改成功SKU库存缓存：SKU：{},Count：{}", sio.getSkuCode(), sio.getCount());
                    stockRedisTemplate.set(DicConstant.SKU_STOCK_INVENTORY_AVAILABLE + sio.getSkuCode(), skuQuantityResult);
                } else {
                    throw new InsertStockRedisException();
                }
            }
            stockFlowDbAddPublisher.sendMsg(sio);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            skuStockService.insertSkuStockMqError(message);
            channel.basicAck(tag, false);
            log.error("基于MANUAL的手工确认消费模式-消费者监听消费消息:{},消息投递标签：{},发生异常：", message, tag, e);
        }
    }
}
