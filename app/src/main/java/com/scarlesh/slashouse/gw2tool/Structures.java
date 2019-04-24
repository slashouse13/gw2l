package com.scarlesh.slashouse.gw2tool;

import java.util.ArrayList;
import java.util.Arrays;

public class Structures {
	public String type;
	public String id;
	public int min;
	public int max;
	public int access=0;
	
	private void changeType(String v){
		this.type=v;
	}

	public static ArrayList<Structures> buildup(ArrayList<String> input){
		ArrayList<Structures> thereYouGo = new ArrayList<Structures>();
		String vvv = input.get(0).substring(0,3);
		for(String thing : input){
			thereYouGo.add(new Structures(thing));
			thereYouGo.get(thereYouGo.size()-1).changeType(vvv);
		}
		return thereYouGo;
	}
	
	private Structures (String inp){
		String[] whatIsIt = {"id","min","max","required_access"};
		String[] in=inp.split("\n");
		int called=0;
		for (int x=1;x<in.length;x++){
			String now = in[x];
			String[] temp1=now.split(":");
			temp1=now.split(":");
			switch(Arrays.asList(whatIsIt).indexOf(temp1[0])){
				case 0: id=Translate.translate(temp1[1]); called++;
					break;
				case 1:min=Integer.parseInt(temp1[1]);
					break;
				case 2:max=Integer.parseInt(temp1[1]);
					break;
				case 3:/*
					if (in[4].contains("GuildWars2")){
						this.access=1;
					}
					else{
						this.access=in[4].length()-4;
					}*/
					if (this.id.contains("Desert") || this.id.contains("Bounty Hunter"))
						this.access=3;
					else if (this.id.contains("Heart"))
						this.access=2;
					else
						this.access=1;
					break;
				default:
					break;

				}
			}
	}
	public String toString(){
		String out="";
		String[] type={"Guild Wars 2","Heart of Thorns", "Path of Fire"};
		//for(structures item : this.current){
			if(this.access==1)
				out+=(this.type+"]\t"+this.id+"\t"+this.max);
			else
				out+=(this.type+"\t"+this.id+"\t"+this.max+"\t"+type[this.access]);
			out+=("\n");
		//}
		return out;
	}

	
	
}
