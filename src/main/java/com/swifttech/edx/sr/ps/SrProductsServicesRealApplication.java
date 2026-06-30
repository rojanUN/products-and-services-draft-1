package com.swifttech.edx.sr.ps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.swifttech.edx"})
public class SrProductsServicesRealApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrProductsServicesRealApplication.class, args);
    }

}
