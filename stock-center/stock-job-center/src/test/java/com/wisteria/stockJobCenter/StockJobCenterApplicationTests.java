package com.wisteria.stockJobCenter;

import com.wisteria.stockJobCenter.util.FavFTPUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootTest
@ComponentScans(value = {
        @ComponentScan(value = "com.wisteria.*")
})
class StockJobCenterApplicationTests {

    @Test
    void contextLoads() {
        String hostname = "116.6.111.178";
        int port = 8021;
        String username = "amazonread";
        String password = "L9kgpztnEj";
        String pathname = "2017manualFile/jinnitie";
        String filename = "2021-11-01_jinnitie_MonthlyTransaction_FR.csv";
//        String originfilename = "C:\\Users\\Downloads\\Downloads.rar";
//        FavFTPUtil.uploadFileFromProduction(hostname, port, username, password, pathname, filename, originfilename);

        String localpath = "D:/";

        FavFTPUtil.downloadFile(hostname, port, username, password, pathname, filename, localpath);

    }
}
