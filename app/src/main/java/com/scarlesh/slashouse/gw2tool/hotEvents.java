package com.scarlesh.slashouse.gw2tool;
import java.util.ArrayList;

/**
 * Created by Slashouse on 26/07/2017.
 */

public class hotEvents extends MetaEventView {
    int[] mapSize = {0, 48, 60, 107, 143};
    static String[] allTheMaps = {"Auric Basin", "Dragon's Stand", "Dry Top",
            "Verdant Brink", "Tangled Depths"};
    String[] firstSpawns = {"00.45","01.30","00.40","00.10","00.25"};    //Summer Times
    int[] backPics={R.drawable.ab,R.drawable.ds, R.drawable.td, R.drawable.vb ,R.drawable.dt};

    ArrayList<String[]> preEvents = new ArrayList<>();
    String[] ABevents = {"Challenges", "Octovines", "Reset", "Pylons"};
    String[] VBevents = {"Night Bosses", "Daytime", "Night"};
    String[] TDevents = {"Against the Chak Gerent", "Chak Gerent", "Help the Outposts"};
    String[] DTevents = {"Sandstorm", "Crash Site"};
    String[] DSevents = {"Start advancing on the Blighting Towers"};

    ArrayList<String[]> preDuration = new ArrayList<>();
    String[] ABsubEventsTimes = {"0.15", "0.20", "0.10", "1.15"};
    String[] VBsubEventsTimes = {"0.20", "1.15", "0.25"};
    String[] TDsubEventsTimes = {"0.05", "0.20", "1.35"};
    String[] DTsubEventsTimes = {"0.20", "0.40"};
    String[] DSsubEventsTimes = {"2.0"};

    int contentView = R.layout.hot_events;
    int currentView = R.id.hotList;

    @Override
    public void initializeMaps(){
        preEvents.add(ABevents);
        preEvents.add(DSevents);
        preEvents.add(DTevents);
        preEvents.add(VBevents);
        preEvents.add(TDevents);
        preDuration.add(ABsubEventsTimes);
        preDuration.add(DSsubEventsTimes);
        preDuration.add(DTsubEventsTimes);
        preDuration.add(VBsubEventsTimes);
        preDuration.add(TDsubEventsTimes);
    }

    @Override
    protected int getContentView(){
        return contentView;
    }

    @Override
    protected int getViewById(){
        return currentView;
    }
    @Override
    protected String[] getFirstSpawns(){
        return firstSpawns;
    }
    @Override
    protected int[] getBackPics(){
        return backPics;
    }

    @Override
    protected int[] getMapSize() {
        return mapSize;
    }

    @Override
    protected String[] getAllTheMaps(){
        return allTheMaps;
    }
    @Override
    protected ArrayList<String[]> getPreEvents(){
        return preEvents;
    }
    @Override
    protected ArrayList<String[]> getPreDuration(){
        return preDuration;
    }

}
