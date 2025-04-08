package classes;

import java.util.StringJoiner;

public class Car {
    private String model;
    private int year;
    private Boolean isElectric;

    public Car() {
        this.model = "Default model";
        this.year = 0;
        this.isElectric = false;
    }

    public Car(String model, int year, Boolean isElectric) {
        this.model = model;
        this.year = year;
        this.isElectric = isElectric;
    }

    public void honk() {
        System.out.println("Beep beep!");
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("model='" + model + "'")
                .add("year='" + year + "'")
                .add("isElectric=" + isElectric)
                .toString();
    }
}
