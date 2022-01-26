package com.wisteria.purchaseCenter.entity;

import lombok.Data;

@Data
public class PurchaseOrderItem {
    private int itemId;
    private int parentId;
    private String skuCode;
    private double price;
    private double tax;
    private double priceTotal;
    private int quantity;
    private int status;
}
