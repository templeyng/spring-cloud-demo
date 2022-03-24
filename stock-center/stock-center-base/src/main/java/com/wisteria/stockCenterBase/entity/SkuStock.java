package com.wisteria.stockCenterBase.entity;

import lombok.Data;

@Data
public class SkuStock {
    private int stockId;
    private String skuCode;
    private int inventoryAvailable;
    private int inventoryTotal;
}
