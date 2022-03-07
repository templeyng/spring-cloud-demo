package com.wisteria.purchaseServerCenter.service.impl;

import com.wisteria.common.entity.base.DicConstant;
import com.wisteria.common.entity.product.Sku;
import com.wisteria.common.utils.ProductRedisTemplate;
import com.wisteria.purchaseCenterBase.entity.PurchaseOrder;
import com.wisteria.purchaseCenterBase.entity.PurchaseOrderItem;
import com.wisteria.purchaseCenterBase.exception.InsertPurchaseOrderRedisException;
import com.wisteria.purchaseCenterBase.utils.CommonUtils;
import com.wisteria.purchaseServerCenter.message.send.PurchaseOrderDbAddPublisher;
import com.wisteria.purchaseServerCenter.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderDbAddPublisher purchaseOrderDbAddPublisher;

    @Autowired
    private ProductRedisTemplate productRedisTemplate;

    @Override
    public void secKillOrderInit() throws InsertPurchaseOrderRedisException {
        final long productSize = productRedisTemplate.lGetListSize(DicConstant.PRODUCT_SKU_ALL_LIST);
        if (productSize == 0) {
            throw new InsertPurchaseOrderRedisException();
        }
        final List<Object> skus = productRedisTemplate.lGet(DicConstant.PRODUCT_SKU_ALL_LIST, 0, productSize);
        if (skus.size() == 0) {
            throw new InsertPurchaseOrderRedisException();
        }
        skus.forEach(e -> {
            Sku s = (Sku) e;
            PurchaseOrderItem p1 = new PurchaseOrderItem();
            p1.setPrice(s.getPrice());
            p1.setQuantity(CommonUtils.loadRandomQuantity());
            p1.setSkuCode(s.getSkuCode());
            p1.setPriceTotal(s.getPriceTotal());
            p1.setTax(s.getTax());
            List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();
            purchaseOrderItems.add(p1);
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setPurchaseOrderItems(purchaseOrderItems);
            purchaseOrder.setPurchaseOrderId(CommonUtils.loadRandomId() + "-" + CommonUtils.loadRandomId() + "-" + CommonUtils.loadRandomId() + "-" + CommonUtils.loadRandomId());
            purchaseOrder.setCreateTime(new Date());
            purchaseOrder.setUpdateTime(new Date());
            purchaseOrder.setStatus(0);
            purchaseOrderDbAddPublisher.sendMsg(purchaseOrder);
        });
    }

}
