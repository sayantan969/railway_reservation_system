package model;

public class Train {
    public String trainNo;
    public String depStation;
    public String destStation;

    public Train(String trainNo, String depStation, String destStation) {
        this.trainNo = trainNo;
        this.depStation = depStation.toLowerCase(); // Convert to lowercase
        this.destStation = destStation.toLowerCase(); // Convert to lowercase
    }
}
