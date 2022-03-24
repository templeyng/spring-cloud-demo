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
            return Res.success("订单初始化成功");
        } catch (InsertPurchaseOrderRedisException e) {
            log.error("订单初始化失败", e);
        }
        return Res.error("订单初始化失败");
    }
}
