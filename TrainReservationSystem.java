package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.Train;
import model.User;

public class TrainReservationSystem {
    static Train[] trainDatabase = {
            new Train("123", "Delhi", "Kanpur"),
            new Train("456", "Mumbai", "Karnataka"),
            new Train("476", "Mumbai", "Kanpur"),
            new Train("856", "Rajasthan", "Karnataka"),
            new Train("956", "Gujrat", "Tamil Nadu"),
            new Train("556", "delhi", "kanpur")
    };

    static User[] userDatabase = new User[100];
    static int userCount = 0;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println(
                    "*************Indian Railways Reservation System*****************");
            System.out.println(
                    "****************************************************************");
            System.out.println();

            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            int choice;
            while (true) {
                try {
                    choice = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine();
                }
            }
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    System.out.println("***//****Exiting Railway Reservation System. Goodbye!****//****");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void login() {
        System.out.print("Enter your name: ");
        scanner.nextLine();
        String name = getValidName();

        System.out.print("Enter your age: ");
        int age = getValidAge();
        String phoneNo;
        do {
            System.out.print("Enter your phone number: ");
            phoneNo = scanner.next();

            if (!phoneNo.matches("[1-9][0-9]{9}") || phoneNo.startsWith("0") || phoneNo.matches(".*[a-zA-Z]+.*")) {
                System.out.println("Invalid phone number. Please try again.");
            }
        } while (!phoneNo.matches("[1-9][0-9]{9}") || phoneNo.startsWith("0") || phoneNo.matches(".*[a-zA-Z]+.*"));

        System.out.println();

        while (true) {
            System.out.println("Options:");
            System.out.println("1. Book a Ticket");
            System.out.println("2. View Available Trains");
            System.out.println("3. Exit");

            int choice;

            while (true) {
                try {
                    System.out.print("Enter your choice: ");
                    choice = scanner.nextInt();
                    if (choice < 1 || choice > 4) {
                        System.out.println("Invalid input. Please enter a number between 1 and 4.");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine();
                }
            }

            switch (choice) {
                case 1:
                    bookTicket(name, age, phoneNo);
                    break;

                case 2:
                    viewTrains();
                    break;

                case 3:
                    System.out.println("***//****Exiting Railway Reservation System. Goodbye!****//****");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void bookTicket(String name, int age, String phoneNo) {
        System.out.println("Enter your departure station: ");
        String departure = getValidDeparture();

        System.out.println("Enter your destination station: ");
        String destination = getValidDestination();

        if (!hasAvailableTrains(departure, destination)) {
            System.out.println("No available trains for the specified departure and destination.");
            System.out.println("***************** THANKYOU ***********************");
            return;
        }

        displayAvailableTrains(departure, destination);

        System.out.println("Enter the train ID you want to select: ");
        String trainId = scanner.next();
        if (!isValidTrainId(trainId, departure, destination)) {
            System.out.println("Invalid train ID. Please try again.");
            return;
        }

        int numPassengers = getValidPassengerCount();
        for (int i = 0; i < numPassengers; i++) {
            System.out.println("Enter details for Passenger " + (i + 1) + ":");
            System.out.print("Name: ");
            scanner.nextLine();

            String passengerName = getValidName();

            String passengerClass;
            do {
                System.out.print("Select class for Passenger " + (i + 1) + " (3AC, 2AC, 1AC, General): ");
                passengerClass = scanner.next().toLowerCase();

                if (!passengerClass.equals("3ac") && !passengerClass.equals("2ac") && !passengerClass.equals("1ac")
                        && !passengerClass.equals("general")) {
                    System.out.println("Invalid class. Please enter a valid class.");
                }
            } while (!passengerClass.equals("3ac") && !passengerClass.equals("2ac") && !passengerClass.equals("1ac")
                    && !passengerClass.equals("general"));

            System.out.print("Does Passenger " + (i + 1) + " want food (yes/no): ");
            String wantFoodInput = scanner.next().toLowerCase();
            boolean wantFood = wantFoodInput.equals("yes");

            System.out.print("Enter berth preference for Passenger " + (i + 1) + " (upper/lower): ");
            String berthPreference = scanner.next().toLowerCase();

            System.out.println();

            System.out.println("**********************  Train Ticket Details *************************");
            System.out.println("Name: " + passengerName);
            System.out.println("Departure: " + departure);
            System.out.println("Destination: " + destination);
            System.out.println("Train ID: " + trainId);
            System.out.println("Class: " + passengerClass);
            System.out.println("Want Food: " + (wantFood ? "Yes" : "No"));
            System.out.println("Berth Preference: " + berthPreference);
            System.out.println("*************************************");

            savePassengerInfo(passengerName, departure, destination,
                    trainId, passengerClass, wantFood, berthPreference);
        }
        System.out.println();
        System.out.println("**********************  Train Ticket booked successfully!*************************");
        System.out.println("**********************THANKYOU FOR USING INDIAN RAILWAYS*************************");
        System.out.println();
    }

    private static boolean isValidTrainId(String trainId, String departure, String destination) {
        for (Train train : trainDatabase) {
            if (train != null && train.trainNo.equalsIgnoreCase(trainId)
                    && train.depStation.equalsIgnoreCase(departure)
                    && train.destStation.equalsIgnoreCase(destination)) {
                return true;
            }
        }
        return false;
    }

    private static Train getTrainById(String trainId) {
        for (Train train : trainDatabase) {
            if (train != null && train.trainNo.equalsIgnoreCase(trainId)) {
                return train;
            }
        }
        return null;
    }

    private static void displayAvailableTrains(String departure, String destination) {
        System.out.println("Available Trains:");
        for (Train train : trainDatabase) {
            if (train != null && train.depStation.equalsIgnoreCase(departure)
                    && train.destStation.equalsIgnoreCase(destination)) {
                System.out.println("Train No: " + train.trainNo +
                        ", Departure: " + train.depStation + ", Destination: " + train.destStation);
            }
        }
    }

    private static boolean hasAvailableTrains(String departure, String destination) {
        for (Train train : trainDatabase) {
            if (train != null && train.depStation.equalsIgnoreCase(departure)
                    && train.destStation.equalsIgnoreCase(destination)) {
                return true;
            }
        }
        return false;
    }

    private static int getValidAge() {
        while (true) {
            try {
                int age = scanner.nextInt();
                scanner.nextLine();
                if (isValidAge(age)) {
                    return age;
                } else {
                    System.out.println("Invalid age. Please enter a valid age (numeric value).");
                    System.out.print("Enter your age: ");
                }
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid age format. Please enter a valid age (numeric value).");
                System.out.print("Enter your age: ");
            }
        }
    }

    private static boolean isValidAge(int age) {
        return age > 0;
    }

    private static String getValidName() {
        while (true) {
            String name = scanner.nextLine();
            if (isValidName(name)) {
                return name;
            } else {
                System.out.println("Invalid name. Please enter a name containing only alphabets.");
                System.out.print("Enter your name: ");
            }
        }
    }

    private static String getValidDeparture() {
        while (true) {
            try {
                String departure = scanner.next();
                if (isValidDeparture(departure)) {
                    return departure;
                } else {
                    System.out.println(
                            "Invalid departure station. Please enter a valid station name (alphabetic characters only).");
                    System.out.print("Enter your departure station: ");
                }
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine();
                System.out.println(
                        "Invalid departure station format. Please enter a valid station name (alphabetic characters only).");
                System.out.print("Enter your departure station: ");
            }
        }
    }

    private static boolean isValidDeparture(String departure) {
        return departure.matches("[a-zA-Z]+");
    }

    private static boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    private static String getValidDestination() {
        while (true) {
            try {
                String destination = scanner.next();
                if (isValidDestination(destination)) {
                    return destination;
                } else {
                    System.out.println(
                            "Invalid destination station. Please enter a valid station name ");
                    System.out.print("Enter your destination station: ");
                }
            } catch (java.util.InputMismatchException e) {
                scanner.nextLine();
                System.out.println(
                        "Invalid destination station format. Please enter a valid station name");
                System.out.print("Enter your destination station: ");
            }
        }
    }

    private static boolean isValidDestination(String destination) {
        return destination.matches("[a-zA-Z]+");
    }

    private static int getValidPassengerCount() {
        while (true) {
            try {
                System.out.print("Enter the number of passengers: ");
                return Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static void savePassengerInfo(String passengerName, String departure, String destination,
            String trainId, String passengerClass, boolean wantFood, String berthPreference) {
        String query = "INSERT INTO passenger_info (name,departure, destination, train_id, class, want_food, berth_preference) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, passengerName);
            preparedStatement.setString(2, departure);
            preparedStatement.setString(3, destination);
            preparedStatement.setString(4, trainId);
            preparedStatement.setString(5, passengerClass);
            preparedStatement.setBoolean(6, wantFood);
            preparedStatement.setString(7, berthPreference);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewTrains() {
        System.out.println("All Available Trains:");
        boolean availableTrainsExist = false;

        for (Train train : trainDatabase) {
            if (train != null) {
                System.out.println("Train No: " + train.trainNo +
                        ", Departure: " + train.depStation + ", Destination: " + train.destStation);
                availableTrainsExist = true;
            }
        }

        if (!availableTrainsExist) {
            System.out.println("No trains available.");
        }

        if (!availableTrainsExist) {
            System.out.println("No trains available.");
        }
    }
}