package com.wisteria.purchaseServerCenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScans(value = {
        @ComponentScan(value = "com.wisteria.*")
})
public class PurchaseServerCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurchaseServerCenterApplication.class, args);
    }

}