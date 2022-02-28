package com.wisteria.common.entity.product;

import lombok.Data;

@Data
public class Sku {
    private int skuId;
    private String skuCode;
    private String skuName;
    private double price;
    private double tax;
    private double priceTotal;
}