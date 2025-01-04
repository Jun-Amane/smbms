package moe.zzy040330.smbms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("moe.zzy040330.smbms.mapper")
public class SmbmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmbmsApplication.class, args);
    }

}
