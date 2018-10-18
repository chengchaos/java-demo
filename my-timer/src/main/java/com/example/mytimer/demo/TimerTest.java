package com.example.mytimer.demo;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 简短的描述
 * </strong>
 * <br /><br />
 * 详细的说明
 * </p>
 *
 * @author chengchao - 18-10-18 上午11:57 <br />
 * @since 1.0
 */
public class TimerTest extends TimerTask {


    private Timer timer;

    @Override
    public void run() {

        System.out.println("Task is running");
    }


    public static void execute() {
        TimerTest timerTest = new TimerTest();
        timerTest.timer = new Timer();
        // 立刻开始执行timerTest任务，只执行一次
        timerTest.timer.schedule(timerTest, new Date());

        //立刻开始执行timerTest任务，执行完本次任务后，隔2秒再执行一次
        //timerTest.timer.schedule(timerTest,new Date(),2000);
        //一秒钟后开始执行timerTest任务，只执行一次
        //timerTest.timer.schedule(timerTest,1000);
        //一秒钟后开始执行timerTest任务，执行完本次任务后，隔2秒再执行一次
        //timerTest.timer.schedule(timerTest,1000,2000);
        //立刻开始执行timerTest任务，每隔2秒执行一次
        //timerTest.timer.scheduleAtFixedRate(timerTest,new Date(),2000);
        //一秒钟后开始执行timerTest任务，每隔2秒执行一次
        //timerTest.timer.scheduleAtFixedRate(timerTest,1000,2000);


        try {
            TimeUnit.MILLISECONDS.sleep(10000L);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        timerTest.timer.cancel();
        //结束任务执行，程序并不终止,因为线程是JVM级别的
        //timerTest.cancel();
    }
}
