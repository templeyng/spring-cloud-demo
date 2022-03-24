package com.wisteria.stockTaskCenter;

import com.wisteria.common.entity.base.Res;
import com.wisteria.stockCenterBase.entity.SkuStock;
import com.wisteria.common.utils.SaleRedisTemplate;
import com.wisteria.stockTaskCenter.service.SkuStockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootTest
@ComponentScans(value = {
        @ComponentScan(value = "com.wisteria.*")
})
class StockTaskCenterApplicationTests {
    @Autowired
    private SkuStockService skuStockService;

    @Autowired
    private SaleRedisTemplate saleRedisTemplate;

    @Test
    void contextLoads() {
        int purchaseCount = 2;
        String skuCode = "SIITH2022010500010";
        increaseInventoryBySkuCode(purchaseCount, skuCode);
    }

    private Res increaseInventoryBySkuCode(int purchaseCount, String skuCode) {
        String stockRedisKey = "sku_stock_" + skuCode;
        SkuStock skuStock;
        Object skuStockObject = saleRedisTemplate.get(stockRedisKey);
        if (skuStockObject == null) {
            skuStock = skuStockService.loadSkuStockBySkuCode(skuCode);
        } else {
            skuStock = (SkuStock) skuStockObject;
        }
        skuStock.setInventoryAvailable(skuStock.getInventoryAvailable() + purchaseCount);
        skuStock.setInventoryTotal(skuStock.getInventoryTotal() + purchaseCount);
        saleRedisTemplate.set(stockRedisKey, skuStock);
        return Res.success();
    }

    private Res decreaseInventoryAvailableBySkuCode(int purchaseCount, String skuCode) {
        if (purchaseCount == 0) {
            return Res.error();
        }
        String stockRedisKey = "sku_stock_" + skuCode;
        SkuStock skuStock;
        Object skuStockObject = saleRedisTemplate.get(stockRedisKey);
        if (skuStockObject == null) {
            return Res.error();
        }
        skuStock = (SkuStock) skuStockObject;

        skuStock.setInventoryAvailable(skuStock.getInventoryAvailable() + purchaseCount);
        skuStock.setInventoryTotal(skuStock.getInventoryTotal() + purchaseCount);
        saleRedisTemplate.set(stockRedisKey, skuStock);
        return Res.success();
    }
}
