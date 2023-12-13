package model;

public class ChildPassenger extends Passenger {
    public ChildPassenger(String name, int age) {
        super(name, age);
    }

    @Override
    public void displayDetails() {
        System.out.println("Child Passenger - Name: " + name + ", Age: " + age);
    }
}
