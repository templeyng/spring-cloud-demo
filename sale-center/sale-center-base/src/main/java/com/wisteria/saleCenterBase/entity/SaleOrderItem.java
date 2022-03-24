package com.wisteria.saleCenterBase.entity;

import lombok.Data;

@Data
public class SaleOrderItem {
    private int itemId;
    private int parentId;
    private String skuCode;
    private double price;
    private double tax;
    private double priceTotal;
    private int quantity;
    private int status;
}
