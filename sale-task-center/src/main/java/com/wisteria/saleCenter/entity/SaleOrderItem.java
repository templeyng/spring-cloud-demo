package com.wisteria.saleCenter.entity;

import lombok.Data;

@Data
public class SaleOrderItem {
    private int itemId;
    private int parentId;
    private String skuCode;
    private double price;
    private int quantity;
    private int status;
}
