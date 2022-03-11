package com.wisteria.stockJobCenter.job;

import com.wisteria.stockJobCenter.service.SkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryQuantityJob {

    @Autowired
    private SkuStockService skuStockService;

    @Scheduled(cron="0 */5 * * * ?")
    public void inventoryQuantityRefresh(){
        skuStockService.inventoryQuantityRefresh();
    }

}