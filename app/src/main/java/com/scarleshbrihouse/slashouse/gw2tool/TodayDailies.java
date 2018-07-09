package com.scarleshbrihouse.slashouse.gw2tool;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;

import com.scarleshbrihouse
        .slashouse.gw2tool.R;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class TodayDailies extends Activity {
    ListView layv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_today);
        Bundle extra = getIntent().getExtras();
        if (extra.getString("site").contains("tomorrow")) {
            setTitle("Tomorrow's dailies");
        }
        ArrayList<StructMyDailies> toBeWritten = MainHead.start(extra.getString("site"),this);
        int dim = toBeWritten.size(),less=0;
        boolean lvl80 =extra.getBoolean("eighty");
        if(lvl80)
            for(int i=0;i<dim;i++)
                if(toBeWritten.get(i).maxLvl<80)
                    less++;

        String[] dailiesDes = new String[dim-less];
        int[] whoCanPlay = new int[dim-less];
        Double[] levels = new Double[dim-less];

        layv= (ListView) findViewById(R.id.listdailies);
        less=0;
        for (int i=0;i<dim;i++) {
            if(lvl80 && toBeWritten.get(i).maxLvl==80) {
                dailiesDes[less] = toBeWritten.get(i).description;
                levels[less] = (double) toBeWritten.get(i).maxLvl;
                whoCanPlay[less] = toBeWritten.get(i).users;
                less++;
            }
            else
            if(!lvl80){
                dailiesDes[i] = toBeWritten.get(i).description;
                levels[i] = (double) toBeWritten.get(i).maxLvl;
                whoCanPlay[i] = toBeWritten.get(i).users;
            }
        }
        Adapter adapter = new Adapter(this,dailiesDes,levels,whoCanPlay);
        layv.setAdapter(adapter);
    }
}
