package com.scarleshbrihouse.slashouse.gw2tool;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.scarleshbrihouse.slashouse.gw2tool.R;

import java.util.ArrayList;

class Adapter extends ArrayAdapter<String> {
    private String[] ent = {}, wp= {};
    private Double[] tim = {}, spawn={};
    private int[] pictures = {},who={};
    private int siz;
    private boolean[] user;
    private ArrayList<String[]> preEvents = new ArrayList<>();
    private Context c;

    Adapter(Context con, String[] entities, Double[] times, boolean[] usr){
        super(con, R.layout.row_list,entities);
        this.ent=entities;
        this.tim=times;
        this.user=usr;
        this.c=con;
    }
    public Adapter (Context con, String[] entities, Double[] times, Double[] spawn, int[] pic, String[] wp){
        super(con,R.layout.row_list,entities);
        this.ent=entities;
        this.tim=times;
        this.spawn=spawn;
        this.pictures=pic;
        this.wp=wp;
        //this.preEvents=new
        this.c=con;
    }

    //Events
    public Adapter (Context con, String[] entities, Double[] times, Double[] spawn, int[] pic, ArrayList<String[]> preev, int sizeOfMaps){
        super(con,R.layout.row_list,entities);
        this.ent=entities;
        this.tim=times;
        this.spawn=spawn;
        this.pictures=pic;
        this.preEvents=preev;
        this.siz=sizeOfMaps;
        this.c=con;
        this.wp=new String[]{""};
    }
    //Dailies
    public Adapter(Context con, String[] entities, Double[] times,int[] usr) {
        super(con,R.layout.row_list,entities);
        this.ent=entities;
        this.tim=times;
        this.spawn=spawn;
        this.who=usr;
        this.c=con;
    }

    public class ViewHold{
        TextView entity,timeLeft,waypoint,timeSpawn;
    }
    private int lastpos= -1;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView (int pos, View conView, ViewGroup parent){
        final ViewHold hold;
        final View res;
        if(conView==null){
            hold = new ViewHold();
            LayoutInflater layInf = LayoutInflater.from(getContext());

            conView= layInf.inflate(R.layout.row_list,parent, false);
            hold.entity=    (TextView) conView.findViewById(R.id.entity);
            //hold.timeLeft=  (TextView) conView.findViewById(R.id.timeLeft);
            hold.waypoint=  (TextView) conView.findViewById(R.id.waypoint);
            hold.timeSpawn= (TextView) conView.findViewById(R.id.timeSpawn);
            conView.setTag(hold);
        }
        else{
            hold = (ViewHold) conView.getTag();
            res=conView;
        }
        Animation ani = AnimationUtils.loadAnimation(c, (pos > lastpos) ? R.anim.up_from_bottom : R.anim.down_from_top);
        lastpos=pos;

        //Assigning Data
        if (pictures.length>0){
            hold.entity.setText(ent[pos]);
            hold.timeSpawn.setText(String.format("%.2f",spawn[pos]));
            long time = (long) (tim[pos]*60000);
            ///////////////TIME LEFT TO EVENTS TEMPORARILY DISABLED

            /*new CountDownTimer(time, 1000){
                public void onTick(long millisUntilFinished){
                    int timeToDisplay= (int) (millisUntilFinished/60000);
                    int h=(timeToDisplay/60),m=timeToDisplay%60;
                    hold.timeLeft.setText(h+":"+String.format("%02d", (m+1)%60));
                }
                public  void onFinish(){
                    hold.timeLeft.setText("Started");
                }
            }.start();*/
            if(pictures.length==siz) {
                for (int count = 0; count < preEvents.size(); count++)
                    for(int j=0;j<preEvents.get(count).length;j++)
                        if (ent[pos] == preEvents.get(count)[j]) {
                            conView.setBackground(ContextCompat.getDrawable(c, pictures[count]));
                            hold.waypoint.setText("");
                        }
            }
            else
                for(int cnt=0;cnt<Events.allTheBosses.length;cnt++)
                    if(ent[pos]==Events.allTheBosses[cnt]) {
                        conView.setBackground(ContextCompat.getDrawable(c, pictures[cnt]));
                        hold.waypoint.setText(wp[cnt]);
                    }
        }
        else {
            //Dailies
            hold.entity.setText(ent[pos]);
            //hold.timeLeft.setText(String.format("%f",tim[pos]).substring(0,2));
            switch(who[pos]){
                case 1:conView.setBackground(ContextCompat.getDrawable(c, R.drawable.gw2));break;
                case 2:conView.setBackground(ContextCompat.getDrawable(c, R.drawable.gw2hot));break;
                case 3:conView.setBackground(ContextCompat.getDrawable(c, R.drawable.gw2pof));break;
            }
        }
        return conView;
    }
}
