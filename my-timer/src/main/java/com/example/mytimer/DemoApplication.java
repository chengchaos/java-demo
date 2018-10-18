package com.example.mytimer;

import com.example.mytimer.demo.QuartzSchedulerDemo;
import com.example.mytimer.demo.RedisReadWriteService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
//@EnableScheduling
public class DemoApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);


	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        QuartzSchedulerDemo quartzSchedulerDemo = context.getBean(QuartzSchedulerDemo.class);

        RedisReadWriteService redisReadWriteService = context.getBean(RedisReadWriteService.class);
        redisReadWriteService.save();
        LOGGER.info(quartzSchedulerDemo.toString());

        String quartzJobName = "mydemo";
        try {
            quartzSchedulerDemo.start(quartzJobName, "0/3 * * * * ?");
        } catch (SchedulerException e) {
            LOGGER.error("SchedulerException : ", e);
        }


        //TimerDemo.execute();

        try {
            TimeUnit.MINUTES.sleep(1L);
        } catch (InterruptedException e) {
            LOGGER.warn("SchedulerException : ", e);
            Thread.currentThread().interrupt();
        }

        String v = redisReadWriteService.read();
        System.out.println(" v ============> "+ v);
        quartzSchedulerDemo.stop(quartzJobName);
    }
}
