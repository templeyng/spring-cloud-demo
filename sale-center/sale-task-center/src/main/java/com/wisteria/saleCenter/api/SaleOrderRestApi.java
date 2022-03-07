package com.wisteria.saleCenter.api;

import com.wisteria.common.entity.base.Res;
import com.wisteria.common.utils.PurchaseRedisTemplate;
import com.wisteria.saleCenter.entity.SaleOrderItem;
import com.wisteria.saleCenter.entity.SaleOrderLd;
import com.wisteria.saleCenter.exception.InsertSaleOrderRedisException;
import com.wisteria.saleCenter.service.SaleOrderService;
import com.wisteria.saleCenter.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("saleOrder")
@Slf4j
public class SaleOrderRestApi {

    @Autowired
    private SaleOrderService saleOrderService;

    @Autowired
    private PurchaseRedisTemplate purchaseRedisTemplate;

    /**
     * 生成采购订单
     *
     * @param saleOrderLd
     * @return
     */
    @PostMapping("postSaleOrderLd")
    public Res postSaleOrderLd(@RequestParam("saleOrderLd") SaleOrderLd saleOrderLd) {
        try {
            saleOrderService.postSaleOrderLd(saleOrderLd);
        } catch (InsertSaleOrderRedisException e) {
            return Res.error("下单失败");
        }
        return Res.success("下单成功");
    }

    @PostMapping("postSaleOrderLdTest")
    public Res postSaleOrderLdTest(@RequestParam("skuCode") String skuCode,
                                   @RequestParam("price") double price,
                                   @RequestParam("quantity") int quantity) {
        try {
            int parentId = CommonUtils.loadIdBySys();
            int itemId = CommonUtils.loadIdBySys();
            SaleOrderLd saleOrderLd = new SaleOrderLd();
            SaleOrderItem saleOrderItem = new SaleOrderItem();
            saleOrderItem.setPrice(price);
            saleOrderItem.setQuantity(quantity);
            saleOrderItem.setStatus(0);
            saleOrderItem.setSkuCode(skuCode);
            saleOrderItem.setParentId(parentId);
            saleOrderItem.setItemId(itemId);
            saleOrderLd.setStatus(0);
            saleOrderLd.setId(parentId);
            saleOrderLd.setSaleOrderItem(saleOrderItem);
            saleOrderLd.setSaleOrderId(skuCode + "_" + parentId + "_" + itemId);
            saleOrderService.postSaleOrderLd(saleOrderLd);
        } catch (InsertSaleOrderRedisException e) {
            return Res.error("下单失败");
        }
        return Res.success("下单成功");
    }
}
