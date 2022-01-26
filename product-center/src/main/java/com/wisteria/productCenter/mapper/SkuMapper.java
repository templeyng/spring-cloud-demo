package com.wisteria.productCenter.mapper;


import com.wisteria.common.entity.product.Sku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SkuMapper {

    List<Sku> loadSkuAll();

    Sku loadSkuByCode(String skuCode);

    Sku loadSkuById(int skuId);

    void insertSku(Sku sku);

    void insertSkus(@Param("skuList") List skuList);
}
