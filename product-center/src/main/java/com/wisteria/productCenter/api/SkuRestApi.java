package com.wisteria.productCenter.api;

import com.wisteria.common.entity.base.Res;
import com.wisteria.common.entity.product.Sku;
import com.wisteria.productCenter.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sku")
@Slf4j
public class SkuRestApi {

    @Autowired
    private SkuService skuService;

    /**
     * 获取Sku列表
     *
     * @return
     */
    @GetMapping("loadSkuAll")
    public Res loadSkuAll() {
        List<Sku> skus = skuService.loadSkuAll();
        return Res.success(skus);
    }

    /**
     * 获取Sku列表
     *
     * @return
     */
    @GetMapping("loadSkuById")
    public Res loadSkuById(int skuId) {
        Sku sku = skuService.loadSkuById(skuId);
        return Res.success(sku);
    }
}
