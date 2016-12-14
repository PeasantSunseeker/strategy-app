package main;

import models.Aerodynamic;
import models.Battery;
import models.Motor;
import models.Parasitic;
import models.Rolling;
import models.Solar;

public class Main {

    public static void main(String[] args) {
        double velocity = 80; // km/h
        double weight = 2700; // Newtons

        double aeroPower = Aerodynamic.aerodynamicPower(velocity);
//		System.out.format("Aero Power: %.0f\n", aeroPower);

        double rollingPower = Rolling.rollingPower(velocity, weight);
//		rollingPower = Math.round(rollingPower);
//		System.out.format("Rolling Power: %.0f\n", rollingPower);

        double totalPower = (aeroPower + rollingPower) / Motor.getEfficiency() + Parasitic.getPowerLossDriving();
//		totalPower = Math.round(totalPower);
//		System.out.format("Total Power: %.0f\n", totalPower);

        double start = 9;
        double end = 17;
        double periodSize = 0.5;
        double totalCharge = 0;
        double distance = 0;

        System.out.format("%5s | %5s | %6s | %3s | %5s | %6s | %5s | %5s | %4s | %5s | %3s\n",
                "Time", "Angle", "Solar", "Aero", "Roll", "Total", "Battery", "Batt Cap", "Batt Chg", "Tot Chg", "Distance");

        for (double time = start; time < end; time += periodSize) {
            double middleTime = time + (periodSize / 2);
            double sunAngle = Solar.getAngle(middleTime);
            double solarPower = Solar.solarPower(middleTime);
            double batteryPower = totalPower - solarPower;
            double batteryCap = Battery.getCapacity(batteryPower);
            double batteryCharge = batteryPower * periodSize / batteryCap * 100;
            batteryCharge *= -1;
            totalCharge += batteryCharge;
            distance += velocity * periodSize;

            System.out.format("%5.2f | %5.2f | %6.1f | %4.0f | %5.1f | %5.1f | %7.1f | %8.2f | %8.2f | %7.2f | %6.0fkm\n",
                    middleTime, sunAngle, solarPower, aeroPower, rollingPower, totalPower, batteryPower, batteryCap, batteryCharge, totalCharge, distance);
        }
    }

}
