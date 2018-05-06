package com.xiaoyiyiyo.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Created by xiaoyiyiyo on 2018/5/6.
 */
@SpringBootApplication
@ServletComponentScan
public class SsoClient2Application {

    public static void main(String args[]) {
        SpringApplication.run(SsoClient2Application.class);
    }

}
