package com.wisteria.stockJobCenter.service;

import com.wisteria.common.entity.product.SkuStock;
import com.wisteria.stockCenterBase.entity.SkuStockFlow;

public interface SkuStockService {

    /**
     * 刷新sku库存数据
     */
    void inventoryQuantityRefresh();
}
