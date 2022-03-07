package com.wisteria.purchaseServerCenter.api;

import com.wisteria.common.entity.base.Res;
import com.wisteria.purchaseCenterBase.exception.InsertPurchaseOrderRedisException;
import com.wisteria.purchaseServerCenter.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("purchaseOrder")
@Slf4j
public class PurchaseOrderRestApi {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping("secKillOrderInit")
    public Res secKillOrderInit() {
        try {
            purchaseOrderService.secKillOrderInit();
            return Res.success();
        } catch (InsertPurchaseOrderRedisException e) {
            log.error("加载秒杀失败", e);
        }
        return Res.error("加载秒杀失败");
    }
}
