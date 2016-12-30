package main;

import models.Aerodynamic;
import models.Battery;
import models.Motor;
import models.Parasitic;
import models.Rolling;
import models.Solar;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static String[][] getData() {
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

        List<String[]> data = new ArrayList<>();
        int listCount = -1;
        int columns = 11;

        for (double time = start; time < end; time += periodSize) {
            data.add(new String[columns]);
            listCount++;

            double middleTime = time + (periodSize / 2);
            double sunAngle = Solar.getAngle(middleTime);
            double solarPower = Solar.solarPower(middleTime);
            double batteryPower = totalPower - solarPower;
            double batteryCap = Battery.getCapacity(batteryPower);
            double batteryCharge = batteryPower * periodSize / batteryCap * 100;
            batteryCharge *= -1;
            totalCharge += batteryCharge;
            distance += velocity * periodSize;

            //TODO Make data list dynamic
            data.get(listCount)[0] = String.format("%2.0f:%2.0f", middleTime, middleTime%1*60);
            data.get(listCount)[1] = String.format("%5.2f", sunAngle);
            data.get(listCount)[2] = String.format("%6.1f", solarPower);
            data.get(listCount)[3] = String.format("%4.0f", aeroPower);
            data.get(listCount)[4] = String.format("%5.1f", rollingPower);
            data.get(listCount)[5] = String.format("%5.1f", totalPower);
            data.get(listCount)[6] = String.format("%7.1f", batteryPower);
            data.get(listCount)[7] = String.format("%8.2f", batteryCap);
            data.get(listCount)[8] = String.format("%8.2f", batteryCharge);
            data.get(listCount)[9] = String.format("%7.2f", totalCharge);
            data.get(listCount)[10] = String.format("%6.0f", distance);

//            System.out.format("%5.2f | %5.2f | %6.1f | %4.0f | %5.1f | %5.1f | %7.1f | %8.2f | %8.2f | %7.2f | %6.0fkm\n",
//                    middleTime, sunAngle, solarPower, aeroPower, rollingPower, totalPower, batteryPower, batteryCap, batteryCharge, totalCharge, distance);
        }

        String[][] returnData = new String[data.size()][columns];

        for (int i = 0; i < data.size(); i++) {
            returnData[i] = data.get(i);
        }

        return returnData;
    }

}
