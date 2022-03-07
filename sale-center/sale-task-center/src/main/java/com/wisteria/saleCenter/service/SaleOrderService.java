package com.wisteria.saleCenter.service;

import com.wisteria.common.entity.base.Res;
import com.wisteria.saleCenter.entity.SaleOrder;
import com.wisteria.saleCenter.entity.SaleOrderLd;
import com.wisteria.saleCenter.exception.InsertSaleOrderRedisException;

public interface SaleOrderService {

    void insertSaleOrder(SaleOrder saleOrder);

    void insertSaleOrderLd(SaleOrderLd saleOrderLd);

    void postSaleOrderLd(SaleOrderLd saleOrderLd) throws InsertSaleOrderRedisException;

    void insertSaleMqError(String message);

    Res postSecKillOrder(String skuCode);
}
