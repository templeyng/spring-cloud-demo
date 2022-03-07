package com.wisteria.purchaseServerCenter.service;

import com.wisteria.purchaseCenterBase.exception.InsertPurchaseOrderRedisException;

public interface PurchaseOrderService {

    void secKillOrderInit() throws InsertPurchaseOrderRedisException;
}
