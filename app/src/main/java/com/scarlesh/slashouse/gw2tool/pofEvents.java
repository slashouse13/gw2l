package com.scarlesh.slashouse.gw2tool;
import java.util.ArrayList;

public class pofEvents extends MetaEventView {
    int[] mapSize = {0, 25, 37, 61, 73, 97};
    String[] firstSpawns = {"00.05", "1.00", "0.00", "1.45", "0.45", "1.00"};
    static String[] allTheMaps = {"Crystal Oasis", "Desert Highlands", "Domain of Vabbi",
            "Domain of Istan", "Thunderhead Keep", "Jahai Bluffs"};
    int[] backPics={R.drawable.choya,R.drawable.highlands, R.drawable.zealots, R.drawable.palawadan,
            R.drawable.north_thund, R.drawable.branded_shatty};

    //Events name
    ArrayList<String[]> preEvents = new ArrayList<>();
    String[] crystal = {"Casino Coins", "Choya Pinata"};
    String[] highlands = {"Buried Treasure"};
    String[] vabbi = {"Forged with Fire", "Serpent's Ire"};
    String[] istan = {"Palawadan"};
    String[] jahai = {"Escort","Death-Branded Shatterer"};
    String[] thuderhead = {"The Oil Floes", "Thunderhead Keep"};

    //Timers duration - Until the next event. If a meta is on a 2h CD, the total for
    //single array must be 2h.
    ArrayList<String[]> preDuration = new ArrayList<>();
    String[] crystalEventsTimes = {"0.15", "1.45"};
    String[] highlandsEventsTimes = {"2.00"};
    String[] vabbiEventsTimes = {"0.30", "1.30"};
    String[] istanEventsTimes = {"2.00"};
    String[] thunderEventsTimes = {"1.0", "1.0"};
    String[] jahaiEventsTimes = {"0.15","1.45"};

    int contentView = R.layout.pof_events;
    int currentView = R.id.pofList;


    @Override
    protected void initializeMaps() {
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
        preDuration.add(jahaiEventsTimes);
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
    protected int[] getMapSize(){
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