package com.wisteria.stockJobCenter.service.impl;

import com.wisteria.common.entity.base.Res;
import com.wisteria.common.entity.product.SkuInventoryInOutType;
import com.wisteria.common.entity.product.SkuStock;
import com.wisteria.common.entity.product.SkuStockFlowCollect;
import com.wisteria.stockCenterBase.entity.SkuStockFlow;
import com.wisteria.stockJobCenter.entity.InventoryRefreshTask;
import com.wisteria.stockJobCenter.mapper.SkuStockMapper;
import com.wisteria.stockJobCenter.service.SkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SkuStockServiceImpl implements SkuStockService {

    @Autowired
    private SkuStockMapper skuStockMapper;

    @Override
    public Res inventoryQuantityRefresh() {
        //判断当前是否存在未完结的任务
        InventoryRefreshTask inventoryRefreshTask = skuStockMapper.loadInventoryRefreshTaskLeast();
        //存在则退出等待下次任务时间
        //不存在则开始刷新库存任务
        if (inventoryRefreshTask == null) {
            inventoryRefreshTask = new InventoryRefreshTask();
            inventoryRefreshTask.setStockFlowId(0);
            inventoryRefreshTask.setTaskStatus(1);
        }
        if (inventoryRefreshTask != null && inventoryRefreshTask.getTaskStatus() == 1) {
            Map<String, Integer> skuStockMap = new HashMap<>();
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
            //查找需要处理的流水记录
            List<SkuStockFlowCollect> skuStockChange = skuStockMapper.loadSkuStockChange(stockFlowId, skuStockFlow.getId());
            //流水放入MAP
            skuStockChange.forEach(e -> {
                switch (e.getType()) {
                    case SkuInventoryInOutType.REFUND_OUT:
                    case SkuInventoryInOutType.SALE_OUT:
                        e.setQuantity(e.getQuantity() * -1);
                }
                if (skuStockMap.containsKey(e.getSkuCode())) {
                    Integer skuStockQuantity = skuStockMap.get(e.getSkuCode());
                    skuStockMap.put(e.getSkuCode(), (skuStockQuantity + e.getQuantity()));
                }else{
                    skuStockMap.put(e.getSkuCode(), e.getQuantity());
                }
            });

            //处理后的MAP数据插入临时表
            List<SkuStockFlowCollect> insertList = new ArrayList<>();
            skuStockMap.forEach((k, v) -> {
                SkuStockFlowCollect sfc = new SkuStockFlowCollect();
                sfc.setSkuCode(k);
                sfc.setQuantity(v);
                insertList.add(sfc);
                if (insertList.size() == 1000) {
                    skuStockMapper.insertSkuStockTemp(insertList);
                    insertList.clear();
                }
            });
            if(insertList.size() > 0){
                skuStockMapper.insertSkuStockTemp(insertList);
            }

            //查找库存为空的数据，新增对应的SKU库存0数据
            skuStockMapper.insertSkuStockNullable();

            //通过临时表更新库存表
            skuStockMapper.refreshStockByStockTemp();
        }
        return Res.success();
    }

}
