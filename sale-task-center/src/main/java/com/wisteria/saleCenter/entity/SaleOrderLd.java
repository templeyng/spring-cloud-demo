package com.wisteria.saleCenter.entity;

import lombok.Data;

@Data
public class SaleOrderLd extends SaleOrder {
    private SaleOrderItem saleOrderItem;
}
