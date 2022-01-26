package com.wisteria.productCenter.service.impl;

import com.wisteria.common.entity.product.Sku;
import com.wisteria.productCenter.mapper.SkuMapper;
import com.wisteria.productCenter.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuMapper skuMapper;

    @Override
    public List<Sku> loadSkuAll() {
        return skuMapper.loadSkuAll();
    }

    @Override
    public Sku loadSkuByCode(String skuCode) {
        return skuMapper.loadSkuByCode(skuCode);
    }

    @Override
    public Sku loadSkuById(int skuId) {
        return skuMapper.loadSkuById(skuId);
    }

    @Override
    public void insertSku(Sku sku) {
        skuMapper.insertSku(sku);
    }

    @Override
    public void insertSkus(List skuList) {
        skuMapper.insertSkus(skuList);
    }
}
