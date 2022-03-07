package com.wisteria.purchaseCenterBase.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PurchaseOrder {
    private int id;
    private String purchaseOrderId;
    private Date createTime;
    private Date updateTime;
    private int status;
    private List<PurchaseOrderItem> purchaseOrderItems;
}
