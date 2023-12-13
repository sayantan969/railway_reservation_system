package model;

public abstract class Passenger {
    public String name;
    public int age;

    public Passenger(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public abstract void displayDetails();
}