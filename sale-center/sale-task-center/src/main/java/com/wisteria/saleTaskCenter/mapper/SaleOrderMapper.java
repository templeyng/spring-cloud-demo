package com.wisteria.saleTaskCenter.mapper;


import com.wisteria.saleCenterBase.entity.SaleOrder;
import com.wisteria.saleCenterBase.entity.SaleOrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SaleOrderMapper {

    void insertSaleOrder(SaleOrder saleOrder);

    void insertSaleOrderItems(@Param("saleOrderItems") List<SaleOrderItem> saleOrderItems);

    void insertSaleMqError(String message);
}
