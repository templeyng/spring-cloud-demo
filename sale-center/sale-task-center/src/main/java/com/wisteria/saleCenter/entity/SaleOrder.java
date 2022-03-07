package com.wisteria.saleCenter.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SaleOrder {
    private int id;
    private String saleOrderId;
    private Date createTime;
    private Date updateTime;
    private int status;
}
