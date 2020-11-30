package com.example.demo.bean;

import java.util.HashMap;
import java.util.Map;

public class Dog extends Mammal {

    {
        System.out.println("Dog ... block");
    }

    static {
        System.out.println("Dog ... static block ");
    }

    public Dog() {
        System.out.println("Dog ... Constructor");
    }

    public void run() {
        System.out.println(this.getId() + "sya: My name is "+ this.getName() + ", and I run so quick ...");
    }


    public static void main(String[] args) {
        Dog d = new Dog();
        d.setId(1);
        d.setName("哈士奇");
        d.run();

        HashMap<Integer, Integer> map = new HashMap<>(16, 1.0f);

        for (int i = 0; i < 15; i++) {
            System.out.println("i => "+ i);
            map.put(i, i);
        }
        System.out.println(map.size());



    }
}
