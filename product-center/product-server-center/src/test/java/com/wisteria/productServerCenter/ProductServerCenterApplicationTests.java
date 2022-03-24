package com.wisteria.productServerCenter;

import com.wisteria.common.entity.product.Sku;
import com.wisteria.common.utils.ProductRedisTemplate;
import com.wisteria.common.utils.PurchaseRedisTemplate;
import com.wisteria.common.utils.SaleRedisTemplate;
import com.wisteria.common.utils.StockRedisTemplate;
import com.wisteria.productServerCenter.service.SkuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class ProductServerCenterApplicationTests {
    @Autowired
    private SkuService skuService;

    @Autowired
    private ProductRedisTemplate productRedisTemplate;
    @Autowired
    private PurchaseRedisTemplate purchaseRedisTemplate;
    @Autowired
    private SaleRedisTemplate saleRedisTemplate;
    @Autowired
    private StockRedisTemplate stockRedisTemplate;

    @Test
    void contextLoads() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String day = sdf.format(new Date());

        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(5);
        formatter.setGroupingUsed(false);

        List<Sku> skus = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            Sku sku = new Sku();
            sku.setSkuCode(getSkuPrefix() + day + formatter.format(i));
            sku.setSkuName("测试商品" + sku.getSkuCode());
            skus.add(sku);
        }

        skuService.insertSkus(skus);
    }

    private String getSkuPrefix() {
        //需要生成几位
        int n = 5;
        //最终生成的字符串
        String str = "";
        for (int i = 0; i < n; i++) {
            str = str + (char) (Math.random() * 26 + 'A');
        }
        return str;
    }

    @Test
    void testRedis(){
        productRedisTemplate.set("2222222", "2222222");
        purchaseRedisTemplate.set("2222222", "2222222");
        saleRedisTemplate.set("2222222", "2222222");
        stockRedisTemplate.set("2222222", "2222222");
    }
}