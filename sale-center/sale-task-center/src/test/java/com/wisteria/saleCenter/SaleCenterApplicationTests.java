package com.wisteria.saleCenter;


import com.wisteria.saleCenter.entity.SaleOrderLd;
import com.wisteria.saleCenter.exception.InsertSaleOrderRedisException;
import com.wisteria.saleCenter.service.SaleOrderService;
import com.wisteria.saleCenter.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@Slf4j
class SaleCenterApplicationTests {

    @Autowired
    private SaleOrderService saleOrderService;

    @Test
    void contextLoads() throws InsertSaleOrderRedisException {
        SaleOrderLd saleOrderLd = new SaleOrderLd();
        saleOrderLd.setStatus(0);
        saleOrderLd.setSaleOrderId("111-222-33333");

        saleOrderService.postSaleOrderLd(saleOrderLd);
    }

    @Test
    void testLong() {
        final long nowTime = new Date().getTime();
        long longTime = nowTime % 10000;
        final double random = Math.random() * 100000000;
        final int count = (int) (random / 1);
        log.info("longTime {}", longTime);
        log.info("random {}", random);
        log.info("count {}", count);
    }

    @Test
    void testRandom(){
        log.info("result {}",CommonUtils.loadIdBySys());
        log.info("result {}",CommonUtils.loadIdBySys());
        log.info("result {}",CommonUtils.loadIdBySys());
        log.info("result {}",CommonUtils.loadIdBySys());
        log.info("result {}",CommonUtils.loadIdBySys());
    }

}
