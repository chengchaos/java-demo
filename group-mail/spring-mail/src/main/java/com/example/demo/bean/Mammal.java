package com.example.demo.bean;

public class Mammal extends Animal {

    {
        System.out.println("Mammal ... block");
    }

    static {
        System.out.println("Mammal ... static block ");
    }

    public Mammal() {
        System.out.println("Mammal ... Constructor");
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
