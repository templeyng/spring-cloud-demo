package com.wisteria.saleCenter.mapper;


import com.wisteria.saleCenter.entity.SaleOrder;
import com.wisteria.saleCenter.entity.SaleOrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SaleOrderMapper {

    void insertSaleOrder(SaleOrder saleOrder);

    void insertSaleOrderItems(@Param("saleOrderItems") List<SaleOrderItem> saleOrderItems);

    List<SaleOrder> selectSaleOrderStockIn();
}
