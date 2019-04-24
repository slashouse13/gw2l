package com.scarlesh.slashouse.gw2tool;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Slashouse on 23/07/2017.
 */

public class Events extends Activity {
    ListView layv;
    public final static int DST = 0; //Daylight Savings Time
    ArrayList<double[]> spawns = new ArrayList<>();
    public final static String[] allTheBosses = {"Shatterer","Jungle Wurm","Modniir Ulgoth","Shadow Behemoth","Golem Mark II",
            "Svanir Shaman","Claw of Jormag","Fire Elemental","Taidha Covington", "Megadestroyer",
            "Tequatl the Sunless","Triple Trouble", "Karka Queen"};
    public final static String[] waypoints ={"Lowland Burns\n(Blazeridge Steppes)","Wychmire Swamp\n(Caledon Forest)",
            "Modniir Gorge\n(Harathi Hinterlands)","Godslost Swamp\n(Queensdale)","Whitland Flats\n(Mount Maelstrom)",
    "Frusenfell Creek\n(Wayfarer Foothills)","Frostwalk Tundra\n(Frostgorge Sound)","Thaumanova Reactor\n(Metrica Province)",
    "Laughing Gull Island\n(Bloodtide Coast)","Maelstrom's Bile\n(Mount Maelstrom)","Splintered Coast\n(Sparkfly Fen)",
    "Firth of Revanion\nBloodtide Coast","Driftglass Springs or Southsun Shoals \n(Southsun Cove)"};
//    //Winter time
//    double[] timeShatter=       {01.00,04.00,07.00,10.00,13.00,16.00,19.00,22.00};
//    double[] timeJungleWurm=    {01.15,03.15,05.15,07.15,09.15,11.15,13.15,15.15,17.15,19.15,21.15,23.15};
//    double[] timeModniirUlgoth= {01.30,04.30,07.30,10.30,13.30,16.30,19.30,22.30};
//    double[] timeShadowBehemoth={01.45,03.45,05.45,07.45,09.45,11.45,13.45,15.45,17.45,19.45,21.45,23.45};
//    double[] timeGolemMarkII=   {02.00,05.00,08.00,11.00,14.00,17.00,20.00,23.00};
//    double[] timeSvanirShaman=  {00.15,02.15,04.15,06.15,08.15,10.15,12.15,14.15,16.15,18.15,20.15,22.15};
//    double[] timeClawofJormag=  {02.30,05.30,08.30,11.30,14.30,17.30,20.30,23.30};
//    double[] timeFireElemental= {00.30,02.45,04.45,06.45,08.45,10.45,12.45,14.45,16.45,18.45,20.45,22.45};
//    double[] timeTaidha=        {00.00,03.00,06.00,09.00,12.00,15.00,18.00,21.00};
//    double[] timeMegadestroyer= {00.30,03.30,06.30,09.30,12.30,15.30,18.30,21.30};
//    double[] teqaSpawn =        {00.00,03.15,07.00,11.30,16.00,19.00};
//    double[] ttSpawn =          {01.00,04.15,08.00,12.30,17.00,20.00};
//    double[] karkaSpawn=        {02.00,06.00,10.30,15.00,18.00,23.00};
    double[] timeShatter=       {02.00,05.00,08.00,11.00,14.00,17.00,20.00,23.00};
    double[] timeJungleWurm=    {00.15,02.15,04.15,06.15,08.15,10.15,12.15,14.15,16.15,18.15,20.15,22.15};
    double[] timeModniirUlgoth= {00.30,02.30,05.30,08.30,11.30,14.30,17.30,20.30};
    double[] timeShadowBehemoth={00.45,02.45,04.45,06.45,08.45,10.45,12.45,14.45,16.45,18.45,20.45,22.45};
    double[] timeGolemMarkII=   {00.00,03.00,06.00,09.00,12.00,15.00,18.00,21.00};
    double[] timeSvanirShaman=  {01.15,03.15,05.15,07.15,09.15,11.15,13.15,15.15,17.15,19.15,21.15,23.15};
    double[] timeClawofJormag=  {00.30,03.30,06.30,09.30,12.30,15.30,18.30,21.30};
    double[] timeFireElemental= {01.30,03.45,05.45,07.45,09.45,11.45,13.45,15.45,17.45,19.45,21.45,23.45};
    double[] timeTaidha=        {01.00,04.00,07.00,10.00,13.00,16.00,19.00,22.00};
    double[] timeMegadestroyer= {01.30,04.30,07.30,10.30,13.30,16.30,19.30,22.30};
    double[] teqaSpawn =        {01.00,04.15,08.00,12.30,17.00,20.00};
    double[] ttSpawn =          {02.00,05.15,09.00,13.30,18.00,21.00};
    double[] karkaSpawn=        {00.00,03.00,07.00,11.30,16.00,19.00};
    final public SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    int[] backPics = {R.drawable.sha, R.drawable.jw, R.drawable.mu, R.drawable.sb, R.drawable.gm, R.drawable.ss,
            R.drawable.jor, R.drawable.fe, R.drawable.tc, R.drawable.md, R.drawable.teq, R.drawable.tt, R.drawable.kq};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.events);
        spawns.add((timeShatter));
        spawns.add((timeJungleWurm));
        spawns.add((timeModniirUlgoth));
        spawns.add((timeShadowBehemoth));
        spawns.add((timeGolemMarkII));
        spawns.add((timeSvanirShaman));
        spawns.add((timeClawofJormag));
        spawns.add((timeFireElemental));
        spawns.add((timeTaidha));
        spawns.add((timeMegadestroyer));
        spawns.add((teqaSpawn));
        spawns.add((ttSpawn));
        spawns.add((karkaSpawn));
        ArrayList<Boss> listOfSchedule = new ArrayList<>();

        //Adding all the boss spawn elements into a single Object
        for (int count =0 ; count<allTheBosses.length;count++) {
            listOfSchedule.add(new Boss().add(allTheBosses[count], spawns.get(count)));
        }
        //Sort by Time spawn
        final ArrayList<Boss> timerReal=setFullTimesSpawn(listOfSchedule,allTheBosses.length, allTheBosses);

        //Current time
        double[] currentTime = MainMenu.getDate();

        double time = currentTime[0]-DST;
        TimeZone tz = TimeZone.getDefault();
        final double timeZone = getTimeZoneDiff(tz.getDisplayName(false,tz.SHORT, Locale.getDefault()));

        //Selection of incoming events
        int considerFromHere=0, listTimeSize=timerReal.size();
        for(int i=0; i<listTimeSize;i++)
            if(time > (changeSpawnTimeZone(timerReal.get(i).date[0],timeZone))
                    && time < (changeSpawnTimeZone(timerReal.get(i+1).date[0],timeZone))) {
                if(i==listTimeSize-1)
                    considerFromHere=i;
                else
                    considerFromHere=i+1;
                break;
            }
        DecimalFormat df = new DecimalFormat("00.00");
        //DISPLAY TIMER
        layv= (ListView) findViewById(R.id.listEv);
        String[] ev = new String[MainMenu.TODISPLAY];
        Double[] tmleft = new Double[MainMenu.TODISPLAY], tmspwn = new Double[MainMenu.TODISPLAY];
        for(int i=0; i<MainMenu.TODISPLAY;i++){
            int index;
            if((considerFromHere+i) < listTimeSize)
                index=considerFromHere+i;
            else
                index=(considerFromHere+i)%listTimeSize;
            double nextSpawn = changeSpawnTimeZone(timerReal.get(index).date[0],timeZone);
            double tm = MapEvents.sumDates(currentTime[0],nextSpawn);     //Time left to spawn in minutes
            ev[i]=timerReal.get(index).name;
            tmleft[i]=tm;
            tmspwn[i]=changeSpawnTimeZone(timerReal.get(index).date[0],timeZone);
        }
        Adapter adapter = new Adapter(this,ev,tmleft,tmspwn,backPics,waypoints);
        layv.setAdapter(adapter);
    }

    public static ArrayList<Boss> setFullTimesSpawn (ArrayList<Boss> all, int dim, String[] entity){
        ArrayList<Boss> timer = new ArrayList<>();
        ArrayList<Integer> whereAreWe = new ArrayList<>();
        for(int i=0;i<dim;i++)
            whereAreWe.add(0);
        for(int j=0; j<114;j++) {
            double[] earliest={30.0};
            int bestPos=0;
            for (int i = 0; i < dim; i++) {
                if(all.get(i).date.length > whereAreWe.get(i))
                if ((all.get(i).date[whereAreWe.get(i)] <= earliest[0]))
                    {
                        bestPos=i;
                        earliest[0] = all.get(i).date[whereAreWe.get(i)];
                    }
            }
            whereAreWe.set(bestPos, whereAreWe.get(bestPos)+1);
            timer.add(new Boss().add(entity[bestPos], earliest));
        }
        System.out.println("pause");
        return timer;
    }

    public static double getTimeZoneDiff (String timezone){
        int sz = timezone.length();
        if(sz>3) {
            double ret = Double.parseDouble(timezone.substring(sz - 5, sz - 3));
            if (timezone.charAt(sz - 6) == '-')
                ret *= -1;
            return ret;
        }
        else
            return 0;
    }
    public static double changeSpawnTimeZone ( double spawn, double timezone){
        return (spawn+timezone)%24.0;
    }
}
