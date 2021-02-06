package com.scarlesh.slashouse.gw2tool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Slashouse on 29/08/2017.
 */

public class DayNight {
    static double firstSpawn = 1.3;   //Winter time
    //static double firstSpawn = Calendar.getInstance().set(Calendar.HOUR_OF_DAY, 1).set(Calendar.MINUTE, 30);
//    private static double firstSpawn = 0.3;     //Summer time
//    private static double[] durations = {1.1,0.05,0.4,0.05}; //Day,Dusk,Night,Dawn
    private static int[] durations = {70, 5, 40, 5}; //Day,Dusk,Night,Dawn
    public final static String[] ddnd = {"Day","Dusk","Night","Dawn"};

    public static ArrayList<Boss> getCycle(){
        Calendar start_time = Calendar.getInstance();
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        start_time.set(Calendar.HOUR_OF_DAY, 0);
        start_time.set(Calendar.MINUTE, 30);
        ArrayList<Boss> cycle = new ArrayList<>();
        BigDecimal temp = BigDecimal.valueOf(firstSpawn);
        int count=0;

        while (start_time.before(tomorrow)){
            double[] double_time = {start_time.get(Calendar.HOUR_OF_DAY), (start_time.get(Calendar.MINUTE) / 100.0)};
            cycle.add(new Boss().add(ddnd[count % 4], double_time));
            start_time.add(Calendar.MINUTE, durations[count % 4]);
            count++;
        }

//        while(temp.compareTo(BigDecimal.valueOf(24))<0){
//            double[] time = {temp.doubleValue()};
//            cycle.add(new Boss().add(ddnd[count%4],time));
//            temp=MapEvents.sumMinutes(BigDecimal.valueOf(time[0]),BigDecimal.valueOf(durations[count%4]));
//            count++;
//        }
        return cycle;
    }
}
