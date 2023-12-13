package model;

public class User {
    String name;
    String departure;
    String destination;
    int age;
    String phoneNo;
    public String selectedtrainId;
    public String selectedClass;
    private int numberOfPassengers;

    Passenger[] passengers;
    String berthPreference;
    boolean wantFood;
    public boolean setWantFood;

    public User(String name, String departure, String destination, int age, String phoneNo) {
        this.name = name; 
        this.departure = departure.toLowerCase();
        this.destination = destination.toLowerCase();
        this.age = age;
        this.phoneNo = phoneNo;
        this.numberOfPassengers = 1;
        this.passengers = new Passenger[numberOfPassengers];
        this.berthPreference = "";
        this.wantFood = false;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
        this.passengers = new Passenger[numberOfPassengers];
    }

    public void addPassenger(Passenger passenger, int index) {
        passengers[index] = passenger;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setBerthPreference(String berthPreference) {
        this.berthPreference = berthPreference;
    }

    public void setWantFood(boolean wantFood) {
        this.wantFood = wantFood;
    }
}