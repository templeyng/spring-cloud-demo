package com.wisteria.saleServerCenter.service.impl;

import com.wisteria.common.entity.base.DicConstant;
import com.wisteria.common.entity.base.Res;
import com.wisteria.common.utils.SaleRedisTemplate;
import com.wisteria.saleCenterBase.entity.SaleOrderItem;
import com.wisteria.saleCenterBase.entity.SaleOrderLd;
import com.wisteria.saleCenterBase.utils.CommonUtils;
import com.wisteria.saleServerCenter.message.send.SaleOrderDbAddPublisher;
import com.wisteria.saleServerCenter.service.SaleOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleOrderServiceImpl implements SaleOrderService {

    @Autowired
    private SaleOrderDbAddPublisher saleOrderDbAddPublisher;

    @Autowired
    private SaleRedisTemplate saleRedisTemplate;

    @Override
    public Res postSecKillOrder(String skuCode) {
        final Object o = saleRedisTemplate.get(DicConstant.STOCK_SKU_SEC_KILL_INVENTORY_AVAILABLE + skuCode);
        if (o != null) {
            int quantity = (int) o;
            if (quantity >= 1) {
                SaleOrderLd saleOrderLd = new SaleOrderLd();
                SaleOrderItem saleOrderItem = new SaleOrderItem();
                saleOrderItem.setSkuCode(skuCode);
                saleOrderItem.setQuantity(1);
                saleOrderItem.setStatus(0);
                saleOrderLd.setSaleOrderItem(saleOrderItem);
                saleOrderStockOutSendMqMessage(skuCode, quantity, saleOrderLd);
                return Res.success("订购成功");
            }
        }
        return Res.error("商品已售完");
    }

    private void saleOrderStockOutSendMqMessage(String skuCode, int quantity, SaleOrderLd saleOrderLd) {
        String uuid = CommonUtils.loadIdBySys() + "_" + CommonUtils.loadIdBySys() + "_" + CommonUtils.loadIdBySys();
        final Object ot = saleRedisTemplate.get(DicConstant.STOCK_INOUT_REDIS_REQUEST + uuid);
        if (ot == null) {
            saleRedisTemplate.set(DicConstant.STOCK_INOUT_REDIS_REQUEST + uuid, 0);
            saleOrderLd.setSaleOrderId(uuid);
            saleRedisTemplate.set(DicConstant.STOCK_SKU_SEC_KILL_INVENTORY_AVAILABLE + skuCode, quantity - saleOrderLd.getSaleOrderItem().getQuantity());
            saleOrderDbAddPublisher.sendMsg(saleOrderLd);
        } else {
            saleOrderStockOutSendMqMessage(skuCode, quantity, saleOrderLd);
        }
    }

}
