package com.example.mytimer.demo;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/9/22 09:27 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
public class FibExecuteTimeDemo {


    double fibIMpl1(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Must be > 0");
        }
        if (n == 0) return 0D;
        if (n == 1) return 1D;
        double d = fibIMpl1(n - 2) + fibIMpl1(n - 1);
        if (Double.isInfinite((d))) {
            throw new ArithmeticException("Overflow");
        }

        return d;
    }

    int nLoops = 10;

    public static void main(String[] args) {

        FibExecuteTimeDemo fibExecuteTimeDemo = new FibExecuteTimeDemo();
        int nLoops = 2;

        double res;
        long then = System.currentTimeMillis();
        for (int i = 0; i < nLoops; i++) {
            res = fibExecuteTimeDemo.fibIMpl1(50);
        }
        long now = System.currentTimeMillis();
        System.out.println("Elapsed time: "+ (now - then));

    }
}
