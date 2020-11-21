package com.example.demo.bean;

public class Animal {

    {
        System.out.println("Animal ... block");
    }

    static {
        System.out.println("Animal ... static block ");
    }

    public Animal() {
        System.out.println("Animal ... Constructor");
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
