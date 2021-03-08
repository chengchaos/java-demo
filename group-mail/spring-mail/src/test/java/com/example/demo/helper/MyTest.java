package com.example.demo.helper;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

public class MyTest {

    static class MyBean {
        int id;
        String name;

        @Override
        public String toString() {
            return "MyBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void test1() {

        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        int processors = runtime.availableProcessors();

        System.out.println("totalMemory (虚拟机中的内存总量 Mb) => "+ totalMemory / 1024.0  / 1024.0);
        System.out.println("freeMemory (虚拟机预留内存 Mb) => "+ freeMemory / 1024.0 / 1024.0);
        System.out.println("maxMemory (虚拟机试图使用的最大内存量 Mb) => "+ maxMemory / 1024.0 / 1024.0);
        System.out.println("processors => "+ processors );
        System.out.println("计算线程可有使用的内存  理论上：最大内存量- 虚拟机预留内存，实际上：虚拟机中的内存总量-虚拟机预留内存。");

        Class<MyBean> myBeanClass = MyBean.class;
        try {
            MyBean myBean = myBeanClass.getDeclaredConstructor().newInstance();
            System.out.println("myBean =>"+ myBean);

//            MyBean myBean1 = (MyBean) Class.forName("com.example.demo.helper.MyTest.MyBean")
//                    .getDeclaredConstructor().newInstance();
            MyBean myBean1 = myBean.getClass().getDeclaredConstructor().newInstance();
            myBean1.id = 1;
            System.out.println("myBean1 =>"+ myBean1);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test2() {
        String x = "\u624b\u673a\u53f7\u8f93\u5165\u4e0d\u7b26\u5408\u89c4\u5219";
        System.out.println(x);
    }
}
