package com.scarleshbrihouse.slashouse.gw2tool;

/**
 * Created by Slashouse on 24/07/2017.
 */
@SuppressWarnings("deprecation")
public class Boss {
    String name;
    double[] date;

    public Boss add(String arg1, double[] arg2){
        this.name=arg1;
        this.date=arg2;
        return this;
    }
    public String toString(){
        return (this.name+" at "+this.date[0]);
    }

}
