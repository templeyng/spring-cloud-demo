package com.wisteria.stockCenter.mapper;


import com.wisteria.common.entity.product.SkuStock;
import com.wisteria.stockCenter.entity.SkuStockFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SkuStockMapper {

    SkuStock loadSkuStockBySkuCode(String skuCode);

    void insertSkuStock(SkuStock skuStock);

    void updateSkuStock(SkuStock skuStock);

    void insertSkuStockFlow(SkuStockFlow skuStockFlow);

    void insertSkuStockMqError(@Param("message") String message);
}
