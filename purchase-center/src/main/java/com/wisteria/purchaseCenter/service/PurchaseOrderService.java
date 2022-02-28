package com.wisteria.purchaseCenter.service;

import com.wisteria.purchaseCenter.entity.PurchaseOrder;
import com.wisteria.purchaseCenter.exception.InsertPurchaseOrderRedisException;

import java.util.List;

public interface PurchaseOrderService {

    void insertPurchaseOrder(PurchaseOrder purchaseOrder);

    List<PurchaseOrder> selectPurchaseOrderStockIn();

    void purchaseOrderStockIn(int purchaseOrderId, String skuCode, int purchaseOrderItemId, int quantityIn);

    void secKillOrderInit() throws InsertPurchaseOrderRedisException;
}
