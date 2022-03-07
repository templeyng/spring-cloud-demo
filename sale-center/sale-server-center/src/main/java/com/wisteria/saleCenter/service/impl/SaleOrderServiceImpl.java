package com.wisteria.saleCenter.service.impl;

import com.wisteria.common.entity.product.SkuInventoryInOut;
import com.wisteria.common.entity.product.SkuInventoryInOutType;
import com.wisteria.saleCenter.entity.SaleOrder;
import com.wisteria.saleCenter.entity.SaleOrderItem;
import com.wisteria.saleCenter.mapper.SaleOrderMapper;
import com.wisteria.saleCenter.message.SaleOrderPublisher;
import com.wisteria.saleCenter.service.SaleOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleOrderServiceImpl implements SaleOrderService {

    @Autowired
    private SaleOrderMapper saleOrderMapper;

    @Autowired
    private SaleOrderPublisher saleOrderPublisher;

    @Override
    public void insertSaleOrder(SaleOrder saleOrder) {
        saleOrderMapper.insertSaleOrder(saleOrder);
        List<SaleOrderItem> saleOrderItems = saleOrder.getSaleOrderItems();
        saleOrderItems.stream().forEach(e -> {
            e.setParentId(Integer.parseInt(String.valueOf(saleOrder.getId())));
        });
        saleOrderMapper.insertSaleOrderItems(saleOrderItems);
    }

    @Override
    public List<SaleOrder> selectSaleOrderStockIn() {
        return saleOrderMapper.selectSaleOrderStockIn();
    }

    @Override
    public void saleOrderStockIn(int saleOrderId, String skuCode, int saleOrderItemId, int quantityIn) {
        final SkuInventoryInOut skuInventoryInOut = new SkuInventoryInOut();
        skuInventoryInOut.setSkuCode(skuCode);
        skuInventoryInOut.setParentId(saleOrderId);
        skuInventoryInOut.setCount(quantityIn);
        skuInventoryInOut.setItemId(saleOrderItemId);
        skuInventoryInOut.setType(SkuInventoryInOutType.PURCHASE_IN);
        saleOrderPublisher.sendMsg(skuInventoryInOut);
    }

}
