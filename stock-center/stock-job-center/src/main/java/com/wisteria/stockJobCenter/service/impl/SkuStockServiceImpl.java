package com.wisteria.stockJobCenter.service.impl;

import com.wisteria.common.entity.product.SkuInventoryInOut;
import com.wisteria.common.entity.product.SkuStock;
import com.wisteria.stockCenterBase.entity.SkuStockFlow;
import com.wisteria.stockJobCenter.entity.InventoryRefreshTask;
import com.wisteria.stockJobCenter.mapper.SkuStockMapper;
import com.wisteria.stockJobCenter.service.SkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SkuStockServiceImpl implements SkuStockService {

    @Autowired
    private SkuStockMapper skuStockMapper;

    @Override
    public void inventoryQuantityRefresh() {
        //判断当前是否存在未完结的任务
        InventoryRefreshTask inventoryRefreshTask = skuStockMapper.loadInventoryRefreshTaskLeast();
        //存在则退出等待下次任务时间
        //不存在则开始刷新库存任务
        if (inventoryRefreshTask == null || (inventoryRefreshTask != null && inventoryRefreshTask.getTaskStatus() == 1)) {
            //获取上次完结的id,(没有则变模式从0开始)
            int stockFlowId = inventoryRefreshTask.getStockFlowId();

            //加载最新的库存流水Id
            SkuStockFlow skuStockFlow = skuStockMapper.loadSkuStockFlowLeast();

            //将任务加入数据库
            InventoryRefreshTask newTask = new InventoryRefreshTask();
            newTask.setTaskTime(new Date());
            newTask.setStockFlowId(skuStockFlow.getId());
            skuStockMapper.insertInventoryRefreshTask(newTask);

            //开始处理任务,加载时间段内的库存流水
            List<SkuStock> skuStockChange = skuStockMapper.loadSkuStockChange();
        }
    }

}
