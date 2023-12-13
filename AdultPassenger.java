package model;

public class AdultPassenger extends Passenger {
    public AdultPassenger(String name, int age) {
        super(name, age);
    }

    @Override
    public void displayDetails() {
        System.out.println("Adult Passenger - Name: " + name + ", Age: " + age);
    }
}