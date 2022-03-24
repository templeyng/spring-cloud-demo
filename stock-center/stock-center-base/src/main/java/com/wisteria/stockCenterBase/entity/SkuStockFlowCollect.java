package com.wisteria.stockCenterBase.entity;

import lombok.Data;

@Data
public class SkuStockFlowCollect {
    private String skuCode;
    private int quantity;
    private int type;
}
