package com.scarlesh.slashouse.gw2tool;

import java.util.ArrayList;

/**
 * Created by Slashouse on 23/07/2017.
 */

public class WorldBosses extends MetaEventView {
    int[] mapSize = {0, 9, 21, 29, 41, 49, 61, 69, 81, 89, 97, 103, 109};
    String[] firstSpawns = {
            "01.00", "01.15", "01.30", "01.45","02.00", "00.15","02.30", "00.30","00.00",
            "00.30","00.00", "01.00", "02.00"};
    int[] backPics = {R.drawable.sha, R.drawable.jw, R.drawable.mu, R.drawable.sb, R.drawable.gm, R.drawable.ss,
            R.drawable.jor, R.drawable.fe, R.drawable.tc, R.drawable.md, R.drawable.teq, R.drawable.tt, R.drawable.kq};
    
    public final static String[] allTheBosses = {
            "Shatterer","Jungle Wurm","Modniir Ulgoth","Shadow Behemoth","Golem Mark II",
            "Svanir Shaman","Claw of Jormag","Fire Elemental","Taidha Covington", "Megadestroyer",
            "Tequatl the Sunless","Triple Trouble", "Karka Queen"};

    // NEW FORMAT
    ArrayList<String[]> preEvents = new ArrayList<>();
    String[] shatter = {"Shatterer\nLowland Burns\n(Blazeridge Steppes)"};
    String[] jungleWurm = {"Jungle Wurm\nWychmire Swamp\n(Caledon Forest)"};
    String[] modniirUlgoth = {"Modniir Ulgoth\nModniir Gorge\n(Harathi Hinterlands)"};
    String[] shadowBehemoth = {"Shadow Behemoth\nGodslost Swamp\n(Queensdale)"};
    String[] golemMarkII = {"Golem Mark II\nWhitland Flats\n(Mount Maelstrom)"};
    String[] svanirShaman = {"Svanir Shaman\nFrusenfell Creek\n(Wayfarer Foothills)"};
    String[] clawofJormag = {"Claw of Jormag\nFrostwalk Tundra\n(Frostgorge Sound)"};
    String[] fireElemental = {"Fire Elemental\nThaumanova Reactor\n(Metrica Province)"};
    String[] taidha = {"Taidha Covington\nLaughing Gull Island\n(Bloodtide Coast)"};
    String[] megadestroyer = {"Megadestroyer\nMaelstrom's Bile\n(Mount Maelstrom)"};
    String[] teqaSpawn = {"Tequatl the Sunless\nSplintered Coast\n(Sparkfly Fen)"};
    String[] ttSpawn = {"Triple Trouble\nFirth of Revanion\nBloodtide Coast"};
    String[] karkaSpawn = {"Karka Queen\nDriftglass Springs or Southsun Shoals \n(Southsun Cove)"};

    ArrayList<String[]> preDuration = new ArrayList<>();
    String[] timeShatter = {"03.00"};
    String[] timeJungleWurm = {"02.00"};
    String[] timeModniirUlgoth = {"03.00"};
    String[] timeShadowBehemoth = {"02.00"};
    String[] timeGolemMarkII = {"03.00"};
    String[] timeSvanirShaman = {"02.00"};
    String[] timeClawofJormag = {"03.00"};
    String[] timeFireElemental = {"02.00"};
    String[] timeTaidha = {"03.00"};
    String[] timeMegadestroyer = {"03.00"};
    //Special Cases
    String[] timeTeqaSpawn = {"04.00"};
    String[] timeTTSpawn = {"04.00"};
    String[] timeKarkaSpawn = {"04.00"};

    int contentView = R.layout.events;
    int currentView = R.id.listEv;
    
    @Override
    protected void initializeMaps() {
        preEvents.add(shatter);
        preEvents.add(jungleWurm);
        preEvents.add(modniirUlgoth);
        preEvents.add(shadowBehemoth);
        preEvents.add(golemMarkII);
        preEvents.add(svanirShaman);
        preEvents.add(clawofJormag);
        preEvents.add(fireElemental);
        preEvents.add(taidha);
        preEvents.add(megadestroyer);
        preEvents.add(teqaSpawn);
        preEvents.add(ttSpawn);
        preEvents.add(karkaSpawn);
        preDuration.add(timeShatter);
        preDuration.add(timeJungleWurm);
        preDuration.add(timeModniirUlgoth);
        preDuration.add(timeShadowBehemoth);
        preDuration.add(timeGolemMarkII);
        preDuration.add(timeSvanirShaman);
        preDuration.add(timeClawofJormag);
        preDuration.add(timeFireElemental);
        preDuration.add(timeTaidha);
        preDuration.add(timeMegadestroyer);
        preDuration.add(timeTeqaSpawn);
        preDuration.add(timeTTSpawn);
        preDuration.add(timeKarkaSpawn);
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
        return allTheBosses;
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
