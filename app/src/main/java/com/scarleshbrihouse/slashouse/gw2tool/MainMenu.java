package com.scarleshbrihouse.slashouse.gw2tool;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;
import com.scarleshbrihouse.slashouse.gw2tool.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MainMenu extends Activity {
    public final static int TODISPLAY=15;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String[] web = {"https://api.guildwars2.com/v2/achievements/daily",
                "https://api.guildwars2.com/v2/achievements/daily/tomorrow"};
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_dailies);

        final CheckBox level80 = (CheckBox) findViewById(R.id.eighty);
        level80.setChecked(true);
        final Button tomorrowButton =(Button)findViewById(R.id.tomorrow);
        final Button todayButton =(Button)findViewById(R.id.today);
        final Button eventsButton =(Button)findViewById(R.id.events);
        final Button mapEventsButton = (Button)findViewById(R.id.mapEvents);
        final Button pofButton =(Button)findViewById(R.id.pofButotn);
        final RelativeLayout back = (RelativeLayout)findViewById(R.id.lay);
        final int[] backPics = {R.drawable.daysky,R.drawable.dusksky,R.drawable.nightsky,R.drawable.dusksky};
        final TextView timeLeft = (TextView)findViewById(R.id.ddnd);

        ArrayList<Boss> dayNightCycle = DayNight.getCycle();
        double[] currentTime = getDate();
        int fromHere=0;
        while(currentTime[0] > dayNightCycle.get(fromHere).date[0])
            fromHere++;

        fromHere--;

        int dayPhase= 0;    //To set next day phase background in case activity's up for day phase change

        //Set background by comparing the current day phase with the phase name
        for(int i =0;i< DayNight.ddnd.length;i++)
            if (dayNightCycle.get(fromHere).name == DayNight.ddnd[i]){
                switch (i){
                    case 0: back.setBackground(ContextCompat.getDrawable(this, backPics[0]));
                            dayPhase=0;
                        break;
                    case 1: back.setBackground(ContextCompat.getDrawable(this, backPics[1]));dayPhase=1;
                        break;
                    case 2: back.setBackground(ContextCompat.getDrawable(this, backPics[2]));dayPhase=2;
                        break;
                    case 3: back.setBackground(ContextCompat.getDrawable(this, backPics[3]));dayPhase=3;
                        break;
                }
            }
            fromHere++;
        long time = (long) (MapEvents.sumDates(currentTime[0],dayNightCycle.get(fromHere).date[0])*60000);
        final String currentSky = dayNightCycle.get(fromHere-1).name, nextSky=dayNightCycle.get(fromHere).name;
        final boolean[] changeBack = {false};
        new CountDownTimer(time, 1000){
            public void onTick(long millisUntilFinished){
                int timeToDisplay= (int) (millisUntilFinished/1000);
                int h=(timeToDisplay/60),m=timeToDisplay%60;
                //timeLeft.setText(currentSky+" will expire in : "+h+":"+String.format("%02d", (m+1)%60));
            }
            public  void onFinish(){
                timeLeft.setText(nextSky + " is coming!");
                changeBack[0] =true;
            }
        }.start();
        //Next phase's background
        if(changeBack[0])
            back.setBackground(ContextCompat.getDrawable(this, backPics[(dayPhase+1)%4]));

        //TODAY'S DAILIES BUTTON
        todayButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent;
                intent = new Intent(MainMenu.this, TodayDailies.class);
                if(level80.isChecked())
                    intent.putExtra("eighty",true);
                else
                    intent.putExtra("eighty",false);
                intent.putExtra("site", web[0]);
                startActivity(intent);
            }
        });
        //TOMORROW'S DAILIES BUTTON
        tomorrowButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent;
                intent = new Intent(MainMenu.this, TodayDailies.class);
                if(level80.isChecked())
                    intent.putExtra("eighty",true);
                else
                    intent.putExtra("eighty",false);
                intent.putExtra("site", web[1]);
                startActivity(intent);
            }
        });

        //EVENTS
        eventsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainMenu.this, Events.class);
                startActivity(intent);
                }
            });
        //HoT meta
        mapEventsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainMenu.this, MapEvents.class);
                startActivity(intent);
            }
        });
        //PoF Events
        pofButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainMenu.this, pofEvents.class);
                startActivity(intent);
            }
        });
    }
    public static double[] getDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String current = sdf.format(calendar.getTime()).replace(":",".");
        double time = Double.parseDouble(current)-Events.DST;

        TimeZone tz = TimeZone.getDefault();
        final double timeZone = Events.getTimeZoneDiff(tz.getDisplayName(false,tz.SHORT, Locale.getDefault()));
        double[] ret = {time,timeZone};
        return ret;
    }
}
