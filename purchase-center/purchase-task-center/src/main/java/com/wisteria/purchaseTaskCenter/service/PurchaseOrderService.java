package com.wisteria.purchaseTaskCenter.service;

import com.wisteria.purchaseCenterBase.entity.PurchaseOrder;
import com.wisteria.purchaseCenterBase.exception.InsertPurchaseOrderRedisException;

import java.util.List;

public interface PurchaseOrderService {

    void insertPurchaseOrder(PurchaseOrder purchaseOrder);

    void purchaseOrderStockIn(int purchaseOrderId, String skuCode, int purchaseOrderItemId, int quantityIn);

    void insertPurchaseMqError(String message);
}
