package com.xiaoyiyiyo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ServletComponentScan
public class SsoClient2Application
{
    public static void main( String[] args )
    {
        SpringApplication.run(SsoClient2Application.class);
    }
}
