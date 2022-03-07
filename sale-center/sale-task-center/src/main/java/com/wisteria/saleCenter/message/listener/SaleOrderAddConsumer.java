package com.wisteria.saleCenter.message.listener;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.wisteria.common.entity.base.DicConstant;
import com.wisteria.saleCenter.entity.SaleOrderLd;
import com.wisteria.saleCenter.service.SaleOrderService;
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

    /**
     * 监听并接收消费队列中的消息-在这里采用单一容器工厂实例即可
     */
    @RabbitListener(queues = DicConstant.SALE_ORDER_DB_ADD_QUEUE, containerFactory = "singleListenerContainer")
    public void consumeMsg(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
        try {
            log.info("基于MANUAL的手工确认消费模式-消费者监听消费消息,消息投递标记：{},内容为：{} ", tag, message);
            //抛异常,归入使得消息重新归入队列
            //执行完业务逻辑后，手动进行确认消费，其中第一个参数为：消息的分发标识(全局唯一);第二个参数：是否允许批量确认消费
            final SaleOrderLd saleOrderLd = JSON.parseObject(message, SaleOrderLd.class);
            saleOrderService.insertSaleOrderLd(saleOrderLd);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            //第二个参数reueue重新归入队列,true的话会重新归入队列,需要人为地处理此次异常消息,重新归入队列也会继续异常
            saleOrderService.insertSaleMqError(message);
            channel.basicAck(tag, false);
            log.error("基于MANUAL的手工确认消费模式-消费者监听消费消息:{},消息投递标签：{},发生异常：", message, tag, e);
        }
    }
}
