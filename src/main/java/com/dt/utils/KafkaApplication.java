package com.dt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * @Author jiangyao
 * @Date 2019/6/27 9:35
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.dt.utils"})
@EnableCaching //开启注解
@EnableKafka
public class KafkaApplication {

    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaApplication.class);

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(KafkaApplication.class);
        app.run(args);

        LOGGER.info("kafka-consumer-demo start Success");
    }
}
