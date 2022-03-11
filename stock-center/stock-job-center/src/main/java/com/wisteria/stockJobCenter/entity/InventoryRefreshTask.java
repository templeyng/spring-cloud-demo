package com.wisteria.stockJobCenter.entity;

import lombok.Data;

import java.util.Date;

@Data
public class InventoryRefreshTask {
    private int id;
    private Date taskTime;
    private int stockFlowId;
    private int taskStatus;
    private Date createTime;
}
