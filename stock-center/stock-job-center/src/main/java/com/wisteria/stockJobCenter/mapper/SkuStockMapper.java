package com.wisteria.stockJobCenter.mapper;

import com.wisteria.stockCenterBase.entity.SkuStockFlowCollect;
import com.wisteria.stockCenterBase.entity.SkuStockFlow;
import com.wisteria.stockJobCenter.entity.InventoryRefreshTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SkuStockMapper {
    InventoryRefreshTask loadInventoryRefreshTaskLeast();

    void insertInventoryRefreshTask(InventoryRefreshTask newTask);

    SkuStockFlow loadSkuStockFlowLeast();

    List<SkuStockFlowCollect> loadSkuStockChange(@Param("flowIdStart") int flowIdStart, @Param("flowIdEnd") int flowIdEnd);

    void insertSkuStockTemp(@Param("list") List<SkuStockFlowCollect> insertList);

    void refreshStockByStockTemp();

    void insertSkuStockNullable();

    void updateInventoryRefreshTaskStatus(@Param("id") int id);

    void clearSkuStockTemp();
}
