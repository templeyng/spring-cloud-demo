package com.wisteria.saleTaskCenter.service;

import com.wisteria.saleCenterBase.entity.SaleOrderLd;

public interface SaleOrderService {

    void insertSaleOrderLd(SaleOrderLd saleOrderLd);

    void insertSaleMqError(String message);
}
