package com.wisteria.stockJobCenter.mapper;

import com.wisteria.common.entity.product.SkuStock;
import com.wisteria.stockCenterBase.entity.SkuStockFlow;
import com.wisteria.stockJobCenter.entity.InventoryRefreshTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SkuStockMapper {
    InventoryRefreshTask loadInventoryRefreshTaskLeast();

    void insertInventoryRefreshTask(InventoryRefreshTask newTask);

    SkuStockFlow loadSkuStockFlowLeast();

    List<SkuStock> loadSkuStockChange();
}
