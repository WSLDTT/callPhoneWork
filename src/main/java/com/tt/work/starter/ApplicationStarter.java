package com.tt.work.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wenshilong
 * @date 2020/06/23
 */
@SpringBootApplication
@MapperScan("com.tt.work.starter.impoetfile.dao")
public class ApplicationStarter {


    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class,args);
    }
}
