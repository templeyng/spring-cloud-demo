package com.wisteria.stockCenter.service;

import com.wisteria.common.entity.product.SkuStock;
import com.wisteria.stockCenter.entity.SkuStockFlow;

public interface SkuStockService {
    /**
     * 根据SkuId获取SKU库存信息
     *
     * @param skuCode
     * @return
     */
    SkuStock loadSkuStockBySkuCode(String skuCode);

    /**
     * 插入SKU库存数据
     *
     * @param skuStock
     */
    void insertSkuStock(SkuStock skuStock);

    /**
     * 插入sku库存数据
     *
     * @param skuStock
     */
    void updateSkuStock(SkuStock skuStock);

    /**
     * 插入库存流水
     * @param skuStockFlow
     */
    void insertSkuStockFlow(SkuStockFlow skuStockFlow);

    /**
     * 插入库存流水错误日志
     * @param message
     */
    void insertSkuStockMqError(String message);
}
