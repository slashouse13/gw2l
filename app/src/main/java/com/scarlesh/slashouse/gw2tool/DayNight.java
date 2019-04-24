package com.scarlesh.slashouse.gw2tool;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Slashouse on 29/08/2017.
 */

public class DayNight {
    static double firstSpawn = 1.3;   //Winter time
    //private static double firstSpawn = 0.3;     //Summer time
    private static double[] durations = {1.1,0.05,0.4,0.05}; //Day,Dusk,Night,Dawn
    public final static String[] ddnd = {"Day","Dusk","Night","Dawn"};

    public static ArrayList<Boss> getCycle(){
        ArrayList<Boss> cycle = new ArrayList<>();
        BigDecimal temp = BigDecimal.valueOf(firstSpawn);
        int count=0;

        while(temp.compareTo(BigDecimal.valueOf(24))<0){
            double[] time = {temp.doubleValue()};
            cycle.add(new Boss().add(ddnd[count%4],time));
            temp=MapEvents.sumMinutes(BigDecimal.valueOf(time[0]),BigDecimal.valueOf(durations[count%4]));
            count++;
        }
        return cycle;
    }
}
