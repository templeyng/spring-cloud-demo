package com.wisteria.saleTaskCenter.service.impl;

import com.wisteria.common.entity.product.SkuInventoryInOut;
import com.wisteria.common.entity.product.SkuInventoryInOutType;
import com.wisteria.saleCenterBase.entity.SaleOrderItem;
import com.wisteria.saleCenterBase.entity.SaleOrderLd;
import com.wisteria.saleServerCenter.message.SaleOrderStockOutPublisher;
import com.wisteria.saleTaskCenter.mapper.SaleOrderMapper;
import com.wisteria.saleTaskCenter.service.SaleOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SaleOrderServiceImpl implements SaleOrderService {

    @Autowired
    private SaleOrderMapper saleOrderMapper;

    @Autowired
    private SaleOrderStockOutPublisher saleOrderStockOutPublisher;

    @Override
    public void insertSaleOrderLd(SaleOrderLd saleOrderLd) {
        //插入数据库
        SaleOrderItem saleOrderItem = saleOrderLd.getSaleOrderItem();
        saleOrderMapper.insertSaleOrder(saleOrderLd);
        saleOrderItem.setParentId(Integer.parseInt(String.valueOf(saleOrderLd.getId())));
        List<SaleOrderItem> saleOrderItems = new ArrayList<>();
        saleOrderItems.add(saleOrderItem);
        saleOrderMapper.insertSaleOrderItems(saleOrderItems);
        //发送mq,进入行实际扣减库存
        saleOrderItems.forEach(e->{
            SkuInventoryInOut skuInventoryInOut = new SkuInventoryInOut();
            skuInventoryInOut.setSkuCode(e.getSkuCode());
            skuInventoryInOut.setParentId(e.getParentId());
            skuInventoryInOut.setCount(e.getQuantity());
            skuInventoryInOut.setItemId(e.getItemId());
            skuInventoryInOut.setType(SkuInventoryInOutType.SALE_OUT);
            saleOrderStockOutPublisher.sendMsg(skuInventoryInOut);
        });
    }

    @Override
    public void insertSaleMqError(String message) {
        saleOrderMapper.insertSaleMqError(message);
    }
}
