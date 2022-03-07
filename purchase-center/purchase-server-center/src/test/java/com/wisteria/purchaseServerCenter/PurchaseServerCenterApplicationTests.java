package com.wisteria.purchaseServerCenter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootTest(classes = PurchaseServerCenterApplication.class)
@ComponentScans(value = {
        @ComponentScan(value = "com.wisteria.*")
})
@Slf4j
class PurchaseServerCenterApplicationTests {

    @Test
    void insertPurchaseOrderTest() {

    }
}