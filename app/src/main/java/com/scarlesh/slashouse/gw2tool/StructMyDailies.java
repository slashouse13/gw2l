package com.scarlesh.slashouse.gw2tool;

import java.util.ArrayList;

/**
 * Created by Slashouse on 23/07/2017.
 */

@SuppressWarnings("deprecation")
public class StructMyDailies {
    public String environment = "";
    public String description;
    public int maxLvl;
    public int users;

    public StructMyDailies (String vs, String des, int mxl, int access){
        environment=vs;
        description=des;
        maxLvl=mxl;
        users=access;
    }
    public static ArrayList<StructMyDailies> doIt (ArrayList<Structures> shiny){
        //here comes a string array, each will have a single daily
        //[pve] Description [level range] [users]
        ArrayList<StructMyDailies> collect = new ArrayList<>();
        int c=0;
        for (Structures itemInList : shiny){
            if(c > (shiny.size() - 16))
                itemInList.type = "fractal";
            collect.add(new StructMyDailies(itemInList.type,itemInList.id, itemInList.max,itemInList.access));       //Everyone
            c++;
        }
        return collect;
    }

}
