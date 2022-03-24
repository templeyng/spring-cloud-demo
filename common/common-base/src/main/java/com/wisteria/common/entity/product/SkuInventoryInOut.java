package com.wisteria.common.entity.product;

import lombok.Data;

@Data
public class SkuInventoryInOut {
    private String skuCode;
    private int count;
    private int type;
    private int parentId;
    private int itemId;
    private String uuid;
}
