package com.wisteria.purchaseCenter.service;

import com.wisteria.purchaseCenter.entity.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderService {

    void insertPurchaseOrder(PurchaseOrder purchaseOrder);

    List<PurchaseOrder> selectPurchaseOrderStockIn();

    void purchaseOrderStockIn(int purchaseOrderId, String skuCode, int purchaseOrderItemId, int quantityIn);
}
