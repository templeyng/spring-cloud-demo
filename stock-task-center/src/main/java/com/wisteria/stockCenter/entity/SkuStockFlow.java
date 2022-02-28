package com.wisteria.stockCenter.entity;

import lombok.Data;

@Data
public class SkuStockFlow {
    private int id;
    private int type;
    private String skuCode;
    private int quantity;
    private String serialNumber;
}
