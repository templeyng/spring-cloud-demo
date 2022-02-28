package com.wisteria.purchaseCenter.api;

import com.wisteria.common.entity.base.Res;
import com.wisteria.purchaseCenter.entity.PurchaseOrder;
import com.wisteria.purchaseCenter.exception.InsertPurchaseOrderRedisException;
import com.wisteria.purchaseCenter.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("purchaseOrder")
@Slf4j
public class PurchaseOrderRestApi {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    /**
     * 生成采购订单
     *
     * @param purchaseOrder
     * @return
     */
    @GetMapping("addPurchaseOrder")
    public Res addPurchaseOrder(@RequestParam("purchaseOrder") PurchaseOrder purchaseOrder) {
        purchaseOrderService.insertPurchaseOrder(purchaseOrder);
        return Res.success();
    }

    /**
     * 采购入库
     *
     * @param purchaseOrderId
     * @param skuCode
     * @param purchaseOrderItemId
     * @param quantityIn
     * @return
     */
    @PutMapping("purchaseOrderStockIn")
    public Res purchaseOrderStockIn(@RequestParam("purchaseOrderId") int purchaseOrderId,
                                    @RequestParam("skuCode") String skuCode,
                                    @RequestParam("purchaseOrderItemId") int purchaseOrderItemId,
                                    @RequestParam("quantityIn") int quantityIn) {
        purchaseOrderService.purchaseOrderStockIn(purchaseOrderId, skuCode, purchaseOrderItemId, quantityIn);
        return Res.success();
    }

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
