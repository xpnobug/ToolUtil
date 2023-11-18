package com.reai.toolutil;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.b3log.latke.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @author 86136
 */
@SpringBootApplication
@Configuration
@EnableEncryptableProperties
public class ToolUtilApplication {
    private static final Logger LOGGER = Logger.getLogger(ToolUtilApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ToolUtilApplication.class, args);
        LOGGER.info("JVM Memory:　"
            + (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024
            + "MB / " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "MB");
    }

}
