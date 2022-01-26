package com.wisteria.productCenter.service;

import com.wisteria.common.entity.product.Sku;

import java.util.List;

public interface SkuService {
    /**
     * 获取全部SKU信息
     *
     * @return
     */
    List<Sku> loadSkuAll();

    /**
     * 根据SkuCode获取SKU信息
     *
     * @param skuCode
     * @return
     */
    Sku loadSkuByCode(String skuCode);

    /**
     * 根据SskuId获取SKU信息
     *
     * @param skuId
     * @return
     */
    Sku loadSkuById(int skuId);

    /**
     * 插入SKU
     *
     * @param sku
     */
    void insertSku(Sku sku);

    /**
     * 批量插入SKU
     *
     * @param skuList
     */
    void insertSkus(List skuList);
}
