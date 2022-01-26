package com.wisteria.purchaseCenter;

import com.wisteria.purchaseCenter.entity.PurchaseOrder;
import com.wisteria.purchaseCenter.entity.PurchaseOrderItem;
import com.wisteria.purchaseCenter.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = PurchaseCenterApplication.class)
@ComponentScans(value = {
        @ComponentScan(value = "com.wisteria.*")
})
@Slf4j
class PurchaseCenterApplicationTests {
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Test
    void insertPurchaseOrderTest() {
        PurchaseOrderItem p1 = new PurchaseOrderItem();
        p1.setPrice(101.5);
        p1.setQuantity(1);
        p1.setSkuCode("QIMXJ2022010500001");
        p1.setPriceTotal(109.5);
        p1.setTax(0.04);
        PurchaseOrderItem p2 = new PurchaseOrderItem();
        p2.setPrice(81);
        p2.setQuantity(4);
        p2.setSkuCode("VDDOS2022010500002");
        p2.setPriceTotal(94.3);
        p2.setTax(0.05);
        List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();
        purchaseOrderItems.add(p1);
        purchaseOrderItems.add(p2);
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setPurchaseOrderItems(purchaseOrderItems);
        purchaseOrder.setPurchaseOrderId("23145-74512-32641-74112");
        purchaseOrder.setCreateTime(new Date());
        purchaseOrder.setUpdateTime(new Date());
        purchaseOrder.setStatus(0);
        purchaseOrderService.insertPurchaseOrder(purchaseOrder);
    }

    @Test
    public void purchaseOrderStockInTest() {
//        purchaseOrderService.purchaseOrderStockIn(13,"QIMXJ2022010500001",15,21);
        purchaseOrderService.purchaseOrderStockIn(13,"VDDOS2022010500002",16,1);
    }
}