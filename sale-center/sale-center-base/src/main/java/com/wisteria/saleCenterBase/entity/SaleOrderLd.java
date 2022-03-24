package com.wisteria.saleCenterBase.entity;

import lombok.Data;

@Data
public class SaleOrderLd extends SaleOrder {
    private SaleOrderItem saleOrderItem;
}
