package com.wisteria.purchaseTaskCenter.mapper;


import com.wisteria.purchaseCenterBase.entity.PurchaseOrder;
import com.wisteria.purchaseCenterBase.entity.PurchaseOrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaseOrderMapper {

    void insertPurchaseOrder(PurchaseOrder purchaseOrder);

    void insertPurchaseOrderItems(@Param("purchaseOrderItems") List<PurchaseOrderItem> purchaseOrderItems);

    List<PurchaseOrder> selectPurchaseOrderStockIn();

    void insertPurchaseMqError(String message);
}
