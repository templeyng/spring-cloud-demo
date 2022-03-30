package com.wisteria.saleTaskCenter.message.listener;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.wisteria.common.entity.base.DicConstant;
import com.wisteria.common.entity.product.Sku;
import com.wisteria.common.utils.ProductRedisTemplate;
import com.wisteria.common.utils.SaleRedisTemplate;
import com.wisteria.saleCenterBase.entity.SaleOrderLd;
import com.wisteria.saleTaskCenter.service.SaleOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class SaleOrderAddConsumer {

    @Autowired
    private SaleOrderService saleOrderService;

    @Autowired
    private ProductRedisTemplate productRedisTemplate;

    /**
     * 监听并接收消费队列中的消息-在这里采用单一容器工厂实例即可
     */
    @RabbitListener(queues = DicConstant.SALE_ORDER_DB_ADD_QUEUE, containerFactory = "singleListenerContainer")
    public void consumeMsg(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
        try {
            SaleOrderLd saleOrderLd = JSON.parseObject(message, SaleOrderLd.class);
            Object skuObject = productRedisTemplate.get(DicConstant.PRODUCT_SKU_DETAIL + saleOrderLd.getSaleOrderItem().getSkuCode());
            if (skuObject != null) {
                Sku sku = (Sku) skuObject;
                saleOrderLd.getSaleOrderItem().setPrice(sku.getPrice());
                saleOrderLd.getSaleOrderItem().setTax(sku.getTax());
                saleOrderLd.getSaleOrderItem().setPriceTotal(sku.getPriceTotal());
                saleOrderService.insertSaleOrderLd(saleOrderLd);
            }
            channel.basicAck(tag, false);
        } catch (Exception e) {
            //第二个参数reueue重新归入队列,true的话会重新归入队列,需要人为地处理此次异常消息,重新归入队列也会继续异常
            saleOrderService.insertSaleMqError(message);
            channel.basicAck(tag, false);
            log.error("基于MANUAL的手工确认消费模式-消费者监听消费消息:{},消息投递标签：{},发生异常：", message, tag, e);
        }
    }
}
