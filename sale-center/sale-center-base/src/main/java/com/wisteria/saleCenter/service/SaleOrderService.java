package com.wisteria.saleCenter.service;

import com.wisteria.saleCenter.entity.SaleOrder;

import java.util.List;

public interface SaleOrderService {

    void insertSaleOrder(SaleOrder saleOrder);

    List<SaleOrder> selectSaleOrderStockIn();

    void saleOrderStockIn(int saleOrderId, String skuCode, int saleOrderItemId, int quantityIn);
}
