package com.scarlesh.slashouse.gw2tool;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

class Translate {

	static String translate(String id){
		String description="";
		String[] ana;
		URL website;
		try {
			website = new URL("https://api.guildwars2.com/v2/achievements?wiki=1&lang=en&ids=" + id);
			String descriptionFromWeb = MainHead.copyToFile(website);
			String temp = descriptionFromWeb.replace("\"", "");

			ana = MainHead.filtered(temp);
			for(String element : ana){
				String[] descr1=element.split(":");
				if(descr1[0].contains("name")){
					description=descr1[1].replace("\"", "");
					break;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return description;
	}

	static ArrayList<DailyDescriptor> translateAll(String ids){
		ArrayList<DailyDescriptor> description = new ArrayList<>();
		URL website;
		try {
			website = new URL("https://api.guildwars2.com/v2/achievements?wiki=1&lang=en&ids=" + ids);
			String descriptionFromWeb = MainHead.copyToFile(website);
			JsonArray jsonObjects = new JsonParser().parse(descriptionFromWeb).getAsJsonArray();
			for (int entry=0; entry<jsonObjects.size(); entry++){
				description.add(new DailyDescriptor(jsonObjects.get(entry).getAsJsonObject().get("id").getAsInt(),
						jsonObjects.get(entry).getAsJsonObject().get("name").getAsString()));
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return description;
	}
}
