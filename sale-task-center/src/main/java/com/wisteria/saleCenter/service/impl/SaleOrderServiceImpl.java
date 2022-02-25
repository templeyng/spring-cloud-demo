package com.wisteria.saleCenter.service.impl;

import com.wisteria.common.entity.base.DicConstant;
import com.wisteria.common.entity.base.Res;
import com.wisteria.common.entity.product.SkuInventoryInOut;
import com.wisteria.common.entity.product.SkuInventoryInOutType;
import com.wisteria.common.utils.SaleRedisTemplate;
import com.wisteria.common.utils.StockRedisTemplate;
import com.wisteria.saleCenter.entity.SaleOrder;
import com.wisteria.saleCenter.entity.SaleOrderItem;
import com.wisteria.saleCenter.entity.SaleOrderLd;
import com.wisteria.saleCenter.exception.InsertSaleOrderRedisException;
import com.wisteria.saleCenter.mapper.SaleOrderMapper;
import com.wisteria.saleCenter.message.send.SaleOrderDbAddPublisher;
import com.wisteria.saleCenter.message.send.SaleOrderStockOutPublisher;
import com.wisteria.saleCenter.message.send.SecKillInitializePublisher;
import com.wisteria.saleCenter.service.SaleOrderService;
import com.wisteria.saleCenter.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SaleOrderServiceImpl implements SaleOrderService {

    @Autowired
    private SaleOrderMapper saleOrderMapper;

    @Autowired
    private SaleOrderStockOutPublisher saleOrderStockOutPublisher;

    @Autowired
    private SaleOrderDbAddPublisher saleOrderDbAddPublisher;

    @Autowired
    private SecKillInitializePublisher secKillInitializePublisher;

    @Autowired
    private StockRedisTemplate stockRedisTemplate;

    @Autowired
    private SaleRedisTemplate saleRedisTemplate;

    @Override
    public void insertSaleOrder(SaleOrder saleOrder) {

    }

    @Override
    public void insertSaleOrderLd(SaleOrderLd saleOrderLd) {
        SaleOrderItem saleOrderItem = saleOrderLd.getSaleOrderItem();
        saleOrderMapper.insertSaleOrder(saleOrderLd);
        saleOrderItem.setParentId(Integer.parseInt(String.valueOf(saleOrderLd.getId())));
        List<SaleOrderItem> saleOrderItems = new ArrayList<>();
        saleOrderItems.add(saleOrderItem);
        saleOrderMapper.insertSaleOrderItems(saleOrderItems);
    }

    @Override
    public void postSaleOrderLd(SaleOrderLd saleOrderLd) throws InsertSaleOrderRedisException {
        SaleOrderItem saleOrderItem = saleOrderLd.getSaleOrderItem();
        final Object o = stockRedisTemplate.get(DicConstant.STOCK_SKU_INVENTORY_AVAILABLE + saleOrderItem.getSkuCode());
        if (o == null) {
            saleOrderLd.setStatus(-1);
            saleOrderDbAddPublisher.sendMsg(saleOrderLd);
            throw new InsertSaleOrderRedisException();
        } else {
            if ((int) o >= saleOrderItem.getQuantity()) {
                SkuInventoryInOut skuInventoryInOut = new SkuInventoryInOut();
                skuInventoryInOut.setSkuCode(saleOrderItem.getSkuCode());
                skuInventoryInOut.setParentId(saleOrderItem.getParentId());
                skuInventoryInOut.setCount(saleOrderItem.getQuantity());
                skuInventoryInOut.setItemId(saleOrderItem.getItemId());
                skuInventoryInOut.setType(SkuInventoryInOutType.SALE_OUT);
                String uuid = CommonUtils.loadIdBySys() + "_" + CommonUtils.loadIdBySys() + "_" + CommonUtils.loadIdBySys();
                final Object ot = saleRedisTemplate.get(DicConstant.STOCK_INOUT_REDIS_REQUEST + uuid);
                if (ot == null) {
                    saleRedisTemplate.set(DicConstant.STOCK_INOUT_REDIS_REQUEST + uuid, 0);
                    skuInventoryInOut.setUuid(uuid);
                    saleOrderStockOutPublisher.sendMsg(skuInventoryInOut);

                    int max = 120;
                    int i = 1;
                    while (true) {
                        log.info("-----------thread-------{},i {}", uuid, i);
                        final Object result = saleRedisTemplate.get(DicConstant.STOCK_INOUT_REDIS_RESULT + uuid);
                        i++;
                        log.info("-----------thread-------{},i {},result {}", uuid, i, result);
                        if (result != null) {
                            saleRedisTemplate.del(DicConstant.STOCK_INOUT_REDIS_REQUEST + uuid, DicConstant.STOCK_INOUT_REDIS_RESULT + uuid);
                            log.info("-----------thread-------{},i {},result {},del redis", uuid, i, result);
                            if ((int) result == 0) {
                                log.info("-----------thread-------{},i {},result {},saleOrderDbAddPublisher", uuid, i, result);
                                saleOrderLd.setStatus(-1);
                                saleOrderDbAddPublisher.sendMsg(saleOrderLd);
                                throw new InsertSaleOrderRedisException();
                            } else {
                                log.info("-----------thread-------{},i {},result {},saleOrderDbAddPublisher", uuid, i, result);
                                saleOrderDbAddPublisher.sendMsg(saleOrderLd);
                            }
                            log.info("-----------thread-------{},i {},result {} end", uuid, i, result);
                            break;
                        }
                        if (i > max) {
                            break;
                        }
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    saleOrderLd.setStatus(-1);
                    saleOrderDbAddPublisher.sendMsg(saleOrderLd);
                    throw new InsertSaleOrderRedisException();
                }
            } else {
                saleOrderLd.setStatus(-1);
                saleOrderDbAddPublisher.sendMsg(saleOrderLd);
                throw new InsertSaleOrderRedisException();
            }
        }
    }

    @Override
    public void insertSaleMqError(String message) {
        saleOrderMapper.insertSaleMqError(message);
    }

    @Override
    public Res postSecKillOrder(String skuCode) {
        final Object o = saleRedisTemplate.get(DicConstant.STOCK_SKU_SEC_KILL_INVENTORY_AVAILABLE + skuCode);
        if (o != null) {
            int quantity = (int) o;
            if (quantity >= 1) {
                SkuInventoryInOut skuInventoryInOut = new SkuInventoryInOut();
                skuInventoryInOut.setSkuCode(skuCode);
                skuInventoryInOut.setParentId(0);
                skuInventoryInOut.setCount(1);
                skuInventoryInOut.setItemId(0);
                skuInventoryInOut.setType(SkuInventoryInOutType.SALE_SEC_KILL_OUT);
                String uuid = CommonUtils.loadIdBySys() + "_" + CommonUtils.loadIdBySys() + "_" + CommonUtils.loadIdBySys();
                final Object ot = saleRedisTemplate.get(DicConstant.STOCK_INOUT_REDIS_REQUEST + uuid);
                if (ot == null) {
                    saleRedisTemplate.set(DicConstant.STOCK_INOUT_REDIS_REQUEST + uuid, 0);
                    skuInventoryInOut.setUuid(uuid);
                    secKillInitializePublisher.sendMsg(skuInventoryInOut);
                    saleRedisTemplate.set(DicConstant.STOCK_SKU_SEC_KILL_INVENTORY_AVAILABLE + skuCode, quantity - 1);
                    return Res.success("订购成功");
                }
            }
        }
        return Res.error("商品已售完");
    }
}
