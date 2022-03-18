package com.wisteria.stockJobCenter;

import com.wisteria.common.utils.SaleRedisTemplate;
import com.wisteria.stockJobCenter.service.SkuStockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootTest
@ComponentScans(value = {
        @ComponentScan(value = "com.wisteria.*")
})
class StockJobCenterApplicationTests {
    @Autowired
    private SkuStockService skuStockService;

    @Autowired
    private SaleRedisTemplate saleRedisTemplate;

    @Test
    void contextLoads() {
    }
}
