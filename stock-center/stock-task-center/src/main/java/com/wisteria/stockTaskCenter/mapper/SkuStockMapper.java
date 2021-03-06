package com.wisteria.stockTaskCenter.mapper;

import com.wisteria.stockCenterBase.entity.SkuStock;
import com.wisteria.stockCenterBase.entity.SkuStockFlow;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SkuStockMapper {
    SkuStock loadSkuStockBySkuCode(String skuCode);

    void insertSkuStock(SkuStock skuStock);

    void updateSkuStock(SkuStock skuStock);

    void insertSkuStockFlow(SkuStockFlow skuStockFlow);

    void insertSkuStockMqError(String message);
}
