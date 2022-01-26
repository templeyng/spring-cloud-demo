package com.wisteria.purchaseCenter.service.impl;

import com.wisteria.common.entity.product.SkuInventoryInOut;
import com.wisteria.common.entity.product.SkuInventoryInOutType;
import com.wisteria.purchaseCenter.entity.PurchaseOrder;
import com.wisteria.purchaseCenter.entity.PurchaseOrderItem;
import com.wisteria.purchaseCenter.mapper.PurchaseOrderMapper;
import com.wisteria.purchaseCenter.message.PurchaseOrderPublisher;
import com.wisteria.purchaseCenter.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private PurchaseOrderPublisher purchaseOrderPublisher;

    @Override
    public void insertPurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrderMapper.insertPurchaseOrder(purchaseOrder);
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrder.getPurchaseOrderItems();
        purchaseOrderItems.stream().forEach(e -> {
            e.setParentId(Integer.parseInt(String.valueOf(purchaseOrder.getId())));
        });
        purchaseOrderMapper.insertPurchaseOrderItems(purchaseOrderItems);
    }

    @Override
    public List<PurchaseOrder> selectPurchaseOrderStockIn() {
        return purchaseOrderMapper.selectPurchaseOrderStockIn();
    }

    @Override
    public void purchaseOrderStockIn(int purchaseOrderId, String skuCode, int purchaseOrderItemId, int quantityIn) {
        final SkuInventoryInOut skuInventoryInOut = new SkuInventoryInOut();
        skuInventoryInOut.setSkuCode(skuCode);
        skuInventoryInOut.setParentId(purchaseOrderId);
        skuInventoryInOut.setCount(quantityIn);
        skuInventoryInOut.setItemId(purchaseOrderItemId);
        skuInventoryInOut.setType(SkuInventoryInOutType.PURCHASE_IN);
        purchaseOrderPublisher.sendMsg(skuInventoryInOut);
    }

}
