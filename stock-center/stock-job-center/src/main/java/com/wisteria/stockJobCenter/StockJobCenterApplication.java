package com.wisteria.stockJobCenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScans(value = {
        @ComponentScan(value = "com.wisteria.*")
})
@EnableScheduling
public class StockJobCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockJobCenterApplication.class, args);
    }

}