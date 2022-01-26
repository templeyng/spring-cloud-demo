package com.wisteria.common.entity.product;

import lombok.Data;

@Data
public class SkuStock {
    private int stockId;
    private String skuCode;
    private int inventoryAvailable;
    private int inventoryTotal;
}
