package com.scarlesh.slashouse.gw2tool;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import static java.math.BigDecimal.ROUND_UNNECESSARY;

public class pofEvents extends Activity {
    double[] firstSpawns = {1.05, 0.00, 2.00, 0.45, 0.45, 0.00}; //UTC+1
    static String[] allTheMaps = {"Crystal Oasis", "Desert Highlands", "Domain of Vabbi",
            "Domain of Istan", "Thunderhead Keep","The Oil Floes", "Jahai Bluffs"};
    int[] backPics={R.drawable.choya,R.drawable.highlands, R.drawable.zealots, R.drawable.palawadan, R.drawable.north_thund, R.drawable.oil_thund, R.drawable.branded_shatty};
    //Events name
    String[] crystal = {"Casino Coins", "Choya Pinata"};
    String[] highlands = {"Buried Treasure"};
    String[] vabbi = {"Zealots"};
    String[] istan = {"Palawadan"};
    String[] thuderhead = {"Thunderhead Keep","The Oil Floes"};
    String[] jahai = {"Escort","Death-Branded Shatterer"};

    //Timers duration - Until the next event. If a meta is on a 2h CD, the total for
    //single array must be 2h.
    double[] crystalEventsTimes = {0.15, 1.45};
    double[] highlandsEventsTimes = {2.00};
    double[] vabbiEventsTimes = {1.30};
    double[] istanEventsTimes = {2.00};
    double[] thunderEventsTimes = {1.0,1.0}; //North, south
    double[] shattyEventsTimes = {0.15,1.45};
    ListView lv;

    ArrayList<String[]> preEvents = new ArrayList<>();
    ArrayList<double[]> preDuration = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.pof_events);

        preEvents.add(crystal);
        preEvents.add(highlands);
        preEvents.add(vabbi);
        preEvents.add(istan);
        preEvents.add(thuderhead);
        preEvents.add(jahai);

        preDuration.add(crystalEventsTimes);
        preDuration.add(highlandsEventsTimes);
        preDuration.add(vabbiEventsTimes);
        preDuration.add(istanEventsTimes);
        preDuration.add(thunderEventsTimes);
        preDuration.add(shattyEventsTimes);

        ArrayList<Boss> listOfEvents = new ArrayList<>();
        ArrayList<Integer> mapsize = new ArrayList<>();


        //Full list of scheduled event - Must undergo time sort
        for (int rotation = 0; rotation < preEvents.size(); rotation++) {
            ArrayList<Boss> temp = setMapEvents(rotation);      //Add a full day map meta rotation
            mapsize.add(temp.size());
            listOfEvents.addAll(temp);                          //Add the map to the full set of HoT -
        }
        //Current time
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        double[] currentTime = MainMenu.getDate();
        double time = currentTime[0]-Events.DST;
        TimeZone tz = TimeZone.getDefault();
        final double timeZone = Events.getTimeZoneDiff(tz.getDisplayName(false,tz.SHORT, Locale.getDefault()));

        final ArrayList<Boss> hotList = sortTimes(listOfEvents, mapsize);
        int considerFromHere=0, listTimeSize=hotList.size();
        //time--;
        for(int i=0; i<listTimeSize-1;i++)
            if(time> (Events.changeSpawnTimeZone(hotList.get(i).date[0],timeZone))
                    && time<(Events.changeSpawnTimeZone(hotList.get(i+1).date[0],timeZone))) {
                if(i==listTimeSize-1)
                    considerFromHere=i;
                else
                    considerFromHere=i+1;
                break;
            }
        DecimalFormat df = new DecimalFormat("00.00");


        //DISPLAY TIMER
        lv= (ListView) findViewById(R.id.pofList);
        String[] ev = new String[MainMenu.TODISPLAY];
        Double[] tmleft = new Double[MainMenu.TODISPLAY], tmspwn = new Double[MainMenu.TODISPLAY];
        for(int i=0; i<MainMenu.TODISPLAY;i++){
            final int index;
            if((considerFromHere+i) < listTimeSize)
                index=considerFromHere+i;
            else
                index=(considerFromHere+i)%listTimeSize;
            double nextSpawn = Events.changeSpawnTimeZone(hotList.get(index).date[0],timeZone);
            double tm = sumDates(currentTime[0],nextSpawn);     //Time left to spawn in minutes
            ev[i]=hotList.get(index).name;
            tmleft[i]=tm;
            tmspwn[i]=Events.changeSpawnTimeZone(hotList.get(index).date[0],timeZone);
        }
        Adapter adapter = new Adapter(this,ev,tmleft,tmspwn,backPics,preEvents,allTheMaps.length);
        lv.setAdapter(adapter);
    }

    private ArrayList<Boss> setMapEvents(int index) {
        ArrayList<Boss> fullMapTimes = new ArrayList<>();
        if (index == 2)
            System.out.println();
        BigDecimal asdf;
        asdf = BigDecimal.valueOf(firstSpawns[index]);
        for (int j = 0; asdf.compareTo(BigDecimal.valueOf(24)) < 0; j++) {
            double[] timeInDbl = {asdf.doubleValue()};
            fullMapTimes.add(new Boss().add(preEvents.get(index)[j % preEvents.get(index).length], timeInDbl));
            asdf = MapEvents.sumMinutes(asdf, BigDecimal.valueOf(preDuration.get(index)[j % preEvents.get(index).length]));
        }
        return fullMapTimes;
    }
    public static ArrayList<Boss> sortTimes(ArrayList<Boss> all, ArrayList<Integer> mapsize) {
        ArrayList<Boss> timer = new ArrayList<>();
        int dim=all.size(), maxSteps=0;
        int[] copySize= {0,24,36,51,63,87};
        //{"Crystal Oasis", "Desert Highlands", "Domain of Vabbi",
        //            "Domain of Istan"}

        for(int i = 0 ; i<mapsize.size(); i++) {
            maxSteps += mapsize.get(i);                         //total number of steps to do, might be the same of dim
        }
        for (int i=0; i<dim;i++){
            int bestPos=0;
            double min = 25.00;                                 //Max time in ArrayList will be 24.00
            for(int j=0;j<copySize.length;j++){                               //From the start point of each map time
                if (all.get(copySize[j]).date[0]<=min) {        //check the earliest time of each map
                    min = all.get(copySize[j]).date[0];         //save the time
                    bestPos=j;                                  //same its position
                }
            }
            timer.add(all.get(copySize[bestPos]));
            copySize[bestPos]++;                                //change the earliest position of that map
        }
        return timer;
    }
    public static double sumDates(double currentTm, double spawn){

        int javaEAccallonatoEnonParsa = (int) currentTm;
        double hm = Math.round((currentTm - javaEAccallonatoEnonParsa)*100);
        double currMinutes=(hm*60)+javaEAccallonatoEnonParsa;
        int h= (int) spawn;
        int m= (int) ((spawn-h)*100);
        double spawnTime = (h*60)+m;
        if(spawnTime<currMinutes)           //In case spawn time is after midnight we add 24h (
            spawnTime+=MapEvents.DAY;       //1440 minutes) in order to have a correct time difference
        return spawnTime-currMinutes;
    }

    public static double subDates(double currentTm, double spawn){
        //Just in case we want to implement "just started events" or "in progress"
        //this is to check that events that starts right before midnight
        //will be considered without having the negative time
        int javaEAccallonatoEnonParsa = (int) currentTm;
        double hm = Math.round((currentTm - javaEAccallonatoEnonParsa)*100);
        double currMinutes=(hm*60)+javaEAccallonatoEnonParsa;
        int h= (int) spawn;
        int m= (int) ((spawn-h)*100);

        double spawnTime = (h*60)+m;
        if(spawnTime>currMinutes)
            spawnTime-=MapEvents.DAY;
        return spawnTime+currMinutes;
    }

    public static BigDecimal sumMinutes(BigDecimal min1, BigDecimal min2){
        BigDecimal hours1 = min1.setScale(0,BigDecimal.ROUND_DOWN);
        BigDecimal stuff1 = min1.subtract(hours1).multiply(BigDecimal.valueOf(100));
        BigDecimal hours2 = min2.setScale(0,BigDecimal.ROUND_DOWN);
        BigDecimal stuff2 = (min2.subtract(hours2).multiply(BigDecimal.valueOf(100)));
        BigDecimal ret=BigDecimal.valueOf(0),temp;

        //if sum minutes goes over 60
        if(stuff1.add(stuff2).compareTo(BigDecimal.valueOf(60))>=0) {
            temp=stuff1.add(stuff2);
            ret=temp.remainder(BigDecimal.valueOf(60));
            ret=ret.add(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(100),2,ROUND_UNNECESSARY);
        }
        else {
            temp=stuff1.add(stuff2).setScale(2);
            ret=ret.add(temp).divide(BigDecimal.valueOf(100), 2, ROUND_UNNECESSARY);
        }
        return ret.add(hours1.add(hours2));
    }


    //Work in Progress
    public static BigDecimal subMinutes(BigDecimal min1, BigDecimal min2) {
        //Conversion in minutes and hours
        BigDecimal hours1 = min1.setScale(0, BigDecimal.ROUND_DOWN);
        BigDecimal stuff1 = min1.subtract(hours1).multiply(BigDecimal.valueOf(100));
        BigDecimal hours2 = min2.setScale(0, BigDecimal.ROUND_DOWN);
        BigDecimal stuff2 = (min2.subtract(hours2).multiply(BigDecimal.valueOf(100)));
        BigDecimal ret = BigDecimal.valueOf(0), temp;


        //1 if this > val, -1 if this < val, 0 if this == val.
        if (stuff1.subtract(stuff2).compareTo(BigDecimal.valueOf(0)) < 0) {
            temp = stuff1.subtract(stuff2);

            ret = (BigDecimal.valueOf(60)).subtract(temp);
            ret = ret.add(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(100), 2, ROUND_UNNECESSARY);
        } else {
            temp = stuff1.add(stuff2).setScale(2);
            ret = ret.add(temp).divide(BigDecimal.valueOf(100), 2, ROUND_UNNECESSARY);
        }
        return ret.add(hours1.add(hours2));
    }
}