package com.wisteria.stockCenter.service.impl;

import com.wisteria.common.entity.product.SkuStock;
import com.wisteria.stockCenter.entity.SkuStockFlow;
import com.wisteria.stockCenter.mapper.SkuStockMapper;
import com.wisteria.stockCenter.service.SkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkuStockServiceImpl implements SkuStockService {

    @Autowired
    private SkuStockMapper skuStockMapper;


    @Override
    public SkuStock loadSkuStockBySkuCode(String skuCode) {
        return skuStockMapper.loadSkuStockBySkuCode(skuCode);
    }

    @Override
    public void insertSkuStock(SkuStock skuStock) {
        skuStockMapper.insertSkuStock(skuStock);
    }

    @Override
    public void updateSkuStock(SkuStock skuStock) {
        skuStockMapper.updateSkuStock(skuStock);
    }

    @Override
    public void insertSkuStockFlow(SkuStockFlow skuStockFlow) {
        skuStockMapper.insertSkuStockFlow(skuStockFlow);
    }

    @Override
    public void insertSkuStockMqError(String message) {
        skuStockMapper.insertSkuStockMqError(message);
    }

}
