package com.wisteria.saleServerCenter.service;

import com.wisteria.common.entity.base.Res;

public interface SaleOrderService {
    Res postSecKillOrder(String skuCode);
}
