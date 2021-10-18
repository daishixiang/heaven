package com.daisx.heaven;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @Author: daisx
 * @Date: 2021/2/18 13:39
 */
@SpringBootApplication
@EnableProcessApplication
public class HeavenApplication {
    public static void main(String[] args) {
        SpringApplication.run(HeavenApplication.class, args);
    }

}
