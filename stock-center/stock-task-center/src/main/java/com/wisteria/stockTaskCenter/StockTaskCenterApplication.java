package com.wisteria.stockTaskCenter;

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
public class StockTaskCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockTaskCenterApplication.class, args);
    }

}