package com.scarleshbrihouse.slashouse.gw2tool;

import android.annotation.TargetApi;
import android.os.Build;
import java.net.MalformedURLException;
import java.net.URL;

class Translate {

	@TargetApi(Build.VERSION_CODES.KITKAT)
	static String translate(String id){
		String description="";
		String[] ana;
		URL website;
		try {
			website = new URL("https://api.guildwars2.com/v2/achievements?wiki=1&lang=en&ids="+id);
			String descriptionFromWeb = MainHead.copyToFile(website);
			String temp=descriptionFromWeb.replace("\"", "");

			ana=MainHead.filtered(temp);
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
}
