package com.scarlesh.slashouse.gw2tool;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Structures {
	public String type;
	public String id;
	public int min;
	public int max;
	public int access;
	
	public static ArrayList<Structures> buildup(JsonArray input, ArrayList<DailyDescriptor> d){
		ArrayList<Structures> thereYouGo = new ArrayList<>();
		for (int x=0; x < input.size(); x++){
			thereYouGo.add(new Structures((JsonObject) input.get(x), d));
		}
		return thereYouGo;
	}

	/**
	*This takes care of getting max and min values for the required level.
	*From the String id, it fetches the daily description
	*The access is defined pretty badly by checking the description itself
	*/
	private Structures (JsonObject inp, ArrayList<DailyDescriptor> desc){
		this.id = get_by_id(inp.get("id").getAsInt(), desc);
		this.min = inp.get("level").getAsJsonObject().get("min").getAsInt();
		this.max = inp.get("level").getAsJsonObject().get("max").getAsInt();
		if (this.id.contains("Desert") || this.id.contains("Bounty Hunter"))
			this.access=3;
		else if (this.id.contains("Heart"))
			this.access=2;
		else
			this.access=1;
	}

	public String toString(){
		String out="";
		String[] type={"Guild Wars 2","Heart of Thorns", "Path of Fire"};
		if(this.access==1)
			out += this.type + "\t" + this.id + "\t" + this.max;
		else
			out += this.type + "\t" + this.id + "\t" + this.max + "\t" + type[this.access];
		out += "\n";
		return out;
	}

	public String get_by_id(int id, ArrayList<DailyDescriptor> d){
		for (int i=0; i<d.size(); i++){
			if (d.get(i).id == id){
				return d.get(i).description;
			}
		}
		return "";
	}
	
}
