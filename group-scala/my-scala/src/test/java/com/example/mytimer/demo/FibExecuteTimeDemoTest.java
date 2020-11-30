package com.example.mytimer.demo;

import org.junit.Test;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/9/7 17:39 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
public class FibExecuteTimeDemoTest {

    @Test
    public void test1() {
        double res;
        FibExecuteTimeDemo fibExecuteTimeDemo = new FibExecuteTimeDemo();
        long then = System.currentTimeMillis();
        for (int i = 0; i < fibExecuteTimeDemo.nLoops; i++) {
            res = fibExecuteTimeDemo.fibIMpl1(50);
        }
        long now = System.currentTimeMillis();
        System.out.println("Elapsed time: "+ (now - then));
    }

}
