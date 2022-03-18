package com.wisteria.purchaseTaskCenter.service.impl;

import com.wisteria.common.entity.product.SkuInventoryInOut;
import com.wisteria.common.entity.product.SkuInventoryInOutType;
import com.wisteria.purchaseCenterBase.entity.PurchaseOrder;
import com.wisteria.purchaseCenterBase.entity.PurchaseOrderItem;
import com.wisteria.purchaseTaskCenter.mapper.PurchaseOrderMapper;
import com.wisteria.purchaseTaskCenter.message.send.PurchaseOrderStockInPublisher;
import com.wisteria.purchaseTaskCenter.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private PurchaseOrderStockInPublisher purchaseOrderStockInPublisher;

    @Override
    public void insertPurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrderMapper.insertPurchaseOrder(purchaseOrder);
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrder.getPurchaseOrderItems();
        purchaseOrderItems.forEach(e -> e.setParentId(Integer.parseInt(String.valueOf(purchaseOrder.getId()))));
        purchaseOrderMapper.insertPurchaseOrderItems(purchaseOrderItems);
        purchaseOrderItems.forEach(e -> {
            log.info("SKU存入库存开始：{}", e);
            purchaseOrderStockIn(e.getParentId(), e.getSkuCode(), e.getItemId(), e.getQuantity());
        });
    }

    @Override
    public void purchaseOrderStockIn(int purchaseOrderId, String skuCode, int purchaseOrderItemId, int quantityIn) {
        final SkuInventoryInOut skuInventoryInOut = new SkuInventoryInOut();
        skuInventoryInOut.setSkuCode(skuCode);
        skuInventoryInOut.setParentId(purchaseOrderId);
        skuInventoryInOut.setCount(quantityIn);
        skuInventoryInOut.setItemId(purchaseOrderItemId);
        skuInventoryInOut.setType(SkuInventoryInOutType.PURCHASE_IN);
        purchaseOrderStockInPublisher.sendMsg(skuInventoryInOut);
    }

    @Override
    public void insertPurchaseMqError(String message) {
        purchaseOrderMapper.insertPurchaseMqError(message);
    }

}
