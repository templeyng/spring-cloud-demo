package com.wisteria.stockJobCenter.service;

import com.wisteria.common.entity.base.Res;

public interface SkuStockService {

    /**
     * 刷新sku库存数据
     * @return
     */
    Res inventoryQuantityRefresh();
}
