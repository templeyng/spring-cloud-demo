package com.wisteria.saleServerCenter.api;

import com.wisteria.common.entity.base.DicConstant;
import com.wisteria.common.entity.base.Res;
import com.wisteria.common.entity.product.Sku;
import com.wisteria.common.utils.ProductRedisTemplate;
import com.wisteria.common.utils.SaleRedisTemplate;
import com.wisteria.common.utils.StockRedisTemplate;
import com.wisteria.saleServerCenter.service.SaleOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("secKill")
@Slf4j
public class SecKillRestApi {

    @Autowired
    private StockRedisTemplate stockRedisTemplate;

    @Autowired
    private ProductRedisTemplate productRedisTemplate;

    @Autowired
    private SaleRedisTemplate saleRedisTemplate;

    @Autowired
    private SaleOrderService saleOrderService;

    /**
     * 初始化秒杀事件
     *
     * @return
     */
    @GetMapping("secKillInit")
    public Res secKillInit() {
        final long l = productRedisTemplate.lGetListSize(DicConstant.PRODUCT_SKU_ALL_LIST);
        if (l > 0) {
            final List<Object> skus = productRedisTemplate.lGet(DicConstant.PRODUCT_SKU_ALL_LIST, 0, l);
            skus.forEach(e -> {
                Sku s = (Sku) e;
                final Object o = stockRedisTemplate.get(DicConstant.SKU_STOCK_INVENTORY_AVAILABLE + s.getSkuCode());
                if (o != null) {
                    int quantity = ((int) o * 9) / 10;
                    saleRedisTemplate.set(DicConstant.STOCK_SKU_SEC_KILL_INVENTORY_AVAILABLE + s.getSkuCode(), quantity);
                    stockRedisTemplate.set(DicConstant.SKU_STOCK_INVENTORY_AVAILABLE_OCCUPIED + s.getSkuCode(), quantity);
                    stockRedisTemplate.set(DicConstant.SKU_STOCK_INVENTORY_AVAILABLE + s.getSkuCode(), ((int) o - quantity));
                }
            });
            return Res.success("初始化秒杀事件完成");
        }
        return Res.success("初始化秒杀事件失败");
    }

    @PostMapping("secKill")
    public Res secKillOrder(@RequestParam("skuCode") String skuCode) {
        return saleOrderService.postSecKillOrder(skuCode);
    }
}
