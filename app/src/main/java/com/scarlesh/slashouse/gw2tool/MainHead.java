package com.scarlesh.slashouse.gw2tool;

import android.content.Context;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainHead {
    private static final String[]listOfGameTypes = {"pve","pvp","wvw","special"};


    public static ArrayList<StructMyDailies> start(String site, Context c)  {
        ArrayList<Structures> ListOfAchieves = new ArrayList<>();
		URL website = null;
		try {
			website = new URL(site);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String string_json = copyToFile(website);
		JsonObject jsonObject = new JsonParser().parse(string_json).getAsJsonObject();

		ArrayList<String> url_params_id = new ArrayList<String>();
		for(int i=0; i< listOfGameTypes.length; i++)
			url_params_id.add("");
		ArrayList<String> ids = new ArrayList<String>();
		for (String key : listOfGameTypes){
			int num_dailies = jsonObject.get(key).getAsJsonArray().size();
			int key_index = java.util.Arrays.asList(listOfGameTypes).indexOf(key);

			for (int e=0; e<num_dailies; e++){
				ids.add(jsonObject.get(key).getAsJsonArray().get(e).getAsJsonObject().get("id").getAsString());

				if(url_params_id.get(key_index).length() == 0)
					url_params_id.set(key_index, url_params_id.get(key_index) + jsonObject.get(key).getAsJsonArray().get(e).getAsJsonObject().get("id").getAsString());
				else
					url_params_id.set(key_index, url_params_id.get(key_index) + "," + jsonObject.get(key).getAsJsonArray().get(e).getAsJsonObject().get("id").getAsString());
			}
			if(url_params_id.get(key_index).length() > 0){
				ArrayList<DailyDescriptor> json_daily_descriptor = Translate.translateAll(url_params_id.get(key_index));
				ListOfAchieves.addAll(Structures.buildup((JsonArray) jsonObject.get(key), json_daily_descriptor));
			}
		}
		return StructMyDailies.doIt(ListOfAchieves);
	}

	public static String copyToFile(URL website) {
		BufferedReader read;
		try {
			HttpURLConnection urlConnection = (HttpURLConnection) website.openConnection();
			read = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = read.readLine()) != null) {
				content.append(inputLine);
			}
			return content.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
    }

	public static String[] filtered(String t){

		String[] workingon;
		int i=0;
		workingon=t.split("[{,}]");
		String[] hey=new String[workingon.length];
		for (String stuff : workingon){
			if (!stuff.isEmpty()){
				hey[i]=stuff;
				i++;
			}
		}
		String[] hey2=new String[i];
        System.arraycopy(hey, 0, hey2, 0, i);
		return hey2;
	}

}