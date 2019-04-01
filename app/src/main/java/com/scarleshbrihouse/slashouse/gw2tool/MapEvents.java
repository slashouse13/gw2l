package com.scarleshbrihouse.slashouse.gw2tool;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import static java.math.BigDecimal.ROUND_UNNECESSARY;

/**
 * Created by Slashouse on 26/07/2017.
 */

public class MapEvents extends Activity{

    static String[] allTheMaps = {"Auric Basin", "Dragon's Stand", "Dry Top",
            "Verdant Brink", "Tangled Depths"};
    double[] firstSpawns = {0.3,0.3,1.5,0.45,0.4};    //Summer Times
    //double[] firstSpawns = {1.3,1.3,0.5,1.45,1.4}; //Winter Times
    static final int DAY = 1440;
    int[] backPics={R.drawable.ab,R.drawable.ds, R.drawable.td, R.drawable.vb ,R.drawable.dt};
    ArrayList<String[]> preEvents = new ArrayList<>();
    String[] ABevents = {"Pylons", "Challenges", "Octovines", "Reset"};
    String[] DSevents = {"Start advancing on the Blighting Towers"};
    String[] TDevents = {"Help the Outposts", "Against the Chak Gerent", "Chak Gerent"};
    String[] VBevents = {"Night", "Night Bosses", "Daytime"};
    String[] DTevents = {"Sandstorm", "Crash Site"};

    ArrayList<double[]> preDuration = new ArrayList<>();
    double[] ABsubEventsTimes = {1.15, 0.15, 0.20, 0.10};
    double[] VBsubEventsTimes = {0.25, 0.20, 1.15};
    double[] TDsubEventsTimes = {1.35, 0.05, 0.20};
    double[] DTsubEventsTimes = {0.20, 0.40};
    double[] DSsubEventsTimes = {2.0};

    ListView lv;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.mapevents);
        preEvents.add(ABevents);
        preEvents.add(DSevents);
        preEvents.add(TDevents);
        preEvents.add(VBevents);
        preEvents.add(DTevents);
        preDuration.add(ABsubEventsTimes);
        preDuration.add(DSsubEventsTimes);
        preDuration.add(TDsubEventsTimes);
        preDuration.add(VBsubEventsTimes);
        preDuration.add(DTsubEventsTimes);

        ArrayList<Boss> listOfEvents = new ArrayList<>();
        ArrayList<Integer> mapsize= new ArrayList<>();
        //All pre-events for a full map rotation are created
        for (int rotation=0; rotation<allTheMaps.length;rotation++){
            ArrayList<Boss> temp = setMapEvents(rotation);      //Add a full day map meta rotation
            mapsize.add(temp.size());
            listOfEvents.addAll(temp);                          //Add the map to the full set of HoT
        }
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        final ArrayList<Boss> hotList = sortTimes(listOfEvents, mapsize); //Sort by time

        //Current TIME and Time Zone
        double[] currentTime = MainMenu.getDate();

        double time = currentTime[0]-Events.DST;
        TimeZone tz = TimeZone.getDefault();
        final double timeZone = Events.getTimeZoneDiff(tz.getDisplayName(false,tz.SHORT, Locale.getDefault()));


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
        lv= (ListView) findViewById(R.id.list);
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

    private ArrayList<Boss> setMapEvents (int index){
        ArrayList<Boss> fullMapTimes = new ArrayList<>();
        if(index==2)
            System.out.println();
        BigDecimal asdf;
        asdf= BigDecimal.valueOf(firstSpawns[index]);
        for(int j=0;asdf.compareTo(BigDecimal.valueOf(24))<0;j++) {
            double[] timeInDbl = {asdf.doubleValue()};
            fullMapTimes.add(new Boss().add(preEvents.get(index)[j % preEvents.get(index).length], timeInDbl));
            asdf = sumMinutes(asdf, BigDecimal.valueOf(preDuration.get(index)[j % preEvents.get(index).length]));
        }
        return fullMapTimes;
    }

    public static ArrayList<Boss> sortTimes(ArrayList<Boss> all, ArrayList<Integer> mapsize) {
        ArrayList<Boss> timer = new ArrayList<>();
        int dim=all.size(), maxSteps=0;
        int[] copySize= {0,46,62,109,145};
        //{"Auric Basin", "Dragon's Stand", "Dry Top","Verdant Brink", "Tangled Depths"}

        for(int i = 0 ; i<5; i++) {
            maxSteps += mapsize.get(i);                         //total number of steps to do, might be the same of dim
        }
        for (int i=0; i<dim;i++){
            int bestPos=0;
            double min = 25.00;                                 //Max time in ArrayList will be 24.00
            for(int j=0;j<5;j++){                               //From the start point of each map time
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
        //String[] temporary = currentTm.split(".");
        //double[] hm = {Double.parseDouble(currentTm.substring(1,3)),Double.parseDouble(currentTm.substring(3))};
        int javaEAccallonatoEnonParsa = (int) currentTm;
        double hm = Math.round((currentTm - javaEAccallonatoEnonParsa)*100);
        double currMinutes=(hm*60)+javaEAccallonatoEnonParsa;
        int h= (int) spawn;
        int m= (int) ((spawn-h)*100);
        double spawnTime = (h*60)+m;
        if(spawnTime<currMinutes)   //In case spawn time is after midnight we add 24h (1440 minutes) in order to
            spawnTime+=DAY;         //have a correct time difference
        return spawnTime-currMinutes;
    }

    public static BigDecimal sumMinutes(BigDecimal min1, BigDecimal min2){
        BigDecimal hours1 = min1.setScale(0,BigDecimal.ROUND_DOWN);
        BigDecimal stuff1 = min1.subtract(hours1).multiply(BigDecimal.valueOf(100));
        BigDecimal hours2 = min2.setScale(0,BigDecimal.ROUND_DOWN);
        BigDecimal stuff2 = (min2.subtract(hours2).multiply(BigDecimal.valueOf(100)));
        BigDecimal ret=BigDecimal.valueOf(0),temp;
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
}
