package com.scarlesh.slashouse.gw2tool;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class MetaEventView extends Activity {
    ListView lv;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(getContentView());
        lv = findViewById(getViewById());
        initializeMaps();
        ArrayList<Boss> listOfEvents = new ArrayList<>();
        ArrayList<Integer> mapsize = new ArrayList<>();

        String[] allTheMaps = getAllTheMaps();

        //All pre-events for a full map rotation are created
        for (int rotation=0; rotation < allTheMaps.length; rotation++){
            ArrayList<Boss> temp = setMapEvents(rotation);      //Add a full day map meta rotation
            mapsize.add(temp.size());
            listOfEvents.addAll(temp);                          //Add the map to the full set of HoT
        }

        final ArrayList<Boss> hotList = sortTimes(listOfEvents, getMapSize()); //Sort by time

        //Current TIME and Time Zone
        double[] currentTime = MainMenu.getDate();

        final double timeZone = GWToolDate.getTimeZoneDiff();
        double time = currentTime[0] - timeZone;

        int considerFromHere = 0, listTimeSize = hotList.size();

        for(int i=0; i < listTimeSize - 1; i++){
            if(time > hotList.get(i).date[0] && time < hotList.get(i + 1).date[0]) {
                if(i == listTimeSize-1)
                    considerFromHere=i;
                else
                    considerFromHere=i+1;
                break;
            }
        }

        String[] ev = new String[MainMenu.TODISPLAY];
        Double[] tmleft = new Double[MainMenu.TODISPLAY], tmspwn = new Double[MainMenu.TODISPLAY];

        for(int i=0; i < MainMenu.TODISPLAY; i++){
            final int index;
            if (considerFromHere + i < listTimeSize)
                index = considerFromHere+i;
            else
                index = (considerFromHere + i) % listTimeSize;
            double nextSpawn = hotList.get(index).date[0];
            double tm = 0;     //Time left to spawn in minutes
            try {
                tm = GWToolDate.sumDates(currentTime[0],nextSpawn);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ev[i] = hotList.get(index).name;
            tmleft[i] = tm;
            tmspwn[i] = hotList.get(index).date[0];
        }
        Adapter adapter = new Adapter(
                this,
                ev,
                tmleft,
                tmspwn,
                getBackPics(),
                getPreEvents(),
                allTheMaps.length
        );
        lv.setAdapter(adapter);
    }

    @NotNull
    private ArrayList<Boss> setMapEvents (int index){
        ArrayList<Boss> fullMapTimes = new ArrayList<>();
        String next_start = getFirstSpawns()[index];

        for(int j=0; Double.parseDouble(next_start) < 24; j++) {
            double[] timeInDbl = { Double.parseDouble(next_start) };
            fullMapTimes.add(
                    new Boss().add(
                            getPreEvents().get(index)[j % getPreEvents().get(index).length],
                            timeInDbl)
            );

            double prev_value = Double.parseDouble(next_start);
            next_start = GWToolDate.sumMinutes(
                    next_start,
                    getPreDuration().get(index)[j % getPreEvents().get(index).length]
            );
            if (prev_value > Double.parseDouble(next_start))
                break;
        }
        return fullMapTimes;
    }

    public static ArrayList<Boss> sortTimes(ArrayList<Boss> all, int[] mapEventSize) {
        ArrayList<Boss> timer = new ArrayList<>();
        // Need to find a better way to dynamically track the size for each map's events
        // They will be a fixed size, this is why I hardcoded them. Man, if this was Python, ez
        int[] copySize = mapEventSize.clone();
        int dim = all.size();
        int cs_dim = copySize.length;
        for (int i = 0; i < dim; i++) {
            int bestPos = 0;
            for (int j = 0; j < cs_dim; j++) {                               //From the start point of each map time
                double current_date = all.get(copySize[j]).date[0];
                if (current_date <= all.get(copySize[bestPos]).date[0]) {        //check the earliest time of each map
                    bestPos = j;
                }
            }
            timer.add(all.get(copySize[bestPos]));
            //change the earliest position of that map
            int next_pos = (bestPos + 1) % cs_dim;
            int threshold = mapEventSize[next_pos];
            if (next_pos == 0)
                threshold = dim;
            copySize[bestPos] = ((copySize[bestPos] + 1) % threshold);
        }
        return timer;
    }

    protected abstract void initializeMaps();
    protected abstract int getContentView();
    protected abstract int getViewById();
    protected abstract String[] getFirstSpawns();
    protected abstract String[] getAllTheMaps();
    protected abstract int[] getBackPics();
    protected abstract int[] getMapSize();
    protected abstract ArrayList<String[]> getPreEvents();
    protected abstract ArrayList<String[]> getPreDuration();
}
