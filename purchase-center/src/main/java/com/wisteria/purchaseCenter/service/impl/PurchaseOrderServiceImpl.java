package com.wisteria.purchaseCenter.service.impl;

import com.wisteria.common.entity.base.DicConstant;
import com.wisteria.common.entity.product.Sku;
import com.wisteria.common.entity.product.SkuInventoryInOut;
import com.wisteria.common.entity.product.SkuInventoryInOutType;
import com.wisteria.common.utils.ProductRedisTemplate;
import com.wisteria.purchaseCenter.entity.PurchaseOrder;
import com.wisteria.purchaseCenter.entity.PurchaseOrderItem;
import com.wisteria.purchaseCenter.exception.InsertPurchaseOrderRedisException;
import com.wisteria.purchaseCenter.mapper.PurchaseOrderMapper;
import com.wisteria.purchaseCenter.message.PurchaseOrderPublisher;
import com.wisteria.purchaseCenter.service.PurchaseOrderService;
import com.wisteria.purchaseCenter.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private PurchaseOrderPublisher purchaseOrderPublisher;

    @Autowired
    private ProductRedisTemplate productRedisTemplate;

    @Override
    public void insertPurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrderMapper.insertPurchaseOrder(purchaseOrder);
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrder.getPurchaseOrderItems();
        purchaseOrderItems.stream().forEach(e -> {
            e.setParentId(Integer.parseInt(String.valueOf(purchaseOrder.getId())));
        });
        purchaseOrderMapper.insertPurchaseOrderItems(purchaseOrderItems);
    }

    @Override
    public List<PurchaseOrder> selectPurchaseOrderStockIn() {
        return purchaseOrderMapper.selectPurchaseOrderStockIn();
    }

    @Override
    public void purchaseOrderStockIn(int purchaseOrderId, String skuCode, int purchaseOrderItemId, int quantityIn) {
        final SkuInventoryInOut skuInventoryInOut = new SkuInventoryInOut();
        skuInventoryInOut.setSkuCode(skuCode);
        skuInventoryInOut.setParentId(purchaseOrderId);
        skuInventoryInOut.setCount(quantityIn);
        skuInventoryInOut.setItemId(purchaseOrderItemId);
        skuInventoryInOut.setType(SkuInventoryInOutType.PURCHASE_IN);
        purchaseOrderPublisher.sendMsg(skuInventoryInOut);
    }

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
            insertPurchaseOrder(purchaseOrder);
        });
    }

}
