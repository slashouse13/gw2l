package com.scarlesh.slashouse.gw2tool;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class MainHead {
    private static final String[]listOfGameTypes = {"pve","pvp","wvw","special"};

    @TargetApi(Build.VERSION_CODES.KITKAT)

    public static ArrayList<StructMyDailies> start(String site, Context c)  {
		// TODO Auto-generated method stub
		String[] tables= {"","",""};
		String[] ana;
		String temp="", temporary="";
        ArrayList<Structures> ListOfAchieves = new ArrayList<Structures>();
		URL website = null;
		try {
			website = new URL(site);
		} catch (IOException dio) {
			// TODO Auto-generated catch block
			dio.printStackTrace();
		}
        temporary = copyToFile(website);
        temp = temporary.replaceAll("\"", "");
		int check = 0;

		ana=filtered(temp);
		for (String item : ana)
			if(item.contains("level:") || Objects.equals(item, "\n") || Objects.equals(item, "\r")){
				;
			}
			else{
				if(item.indexOf("pve")>-1){
					check=0;
				}
				else
				if(item.indexOf("pvp")>-1){
					check=1;
				}
				else
				if(item.indexOf("wvw")>-1){
					check=2;
				}
				tables[check]+=blanks(item)+"\n";
			}

		for(String item : tables){
			ArrayList<String> meat=butcher(item);
			ArrayList<Structures> meme=Structures.buildup(meat);
			ListOfAchieves.addAll(meme);
		}

        ArrayList<StructMyDailies> finalTable = StructMyDailies.doIt(ListOfAchieves);
		return finalTable;
	}
	static HttpURLConnection urlConnection = null;

	public static String copyToFile(URL website) {
            BufferedReader read = null;
            String reader = "";
            StringBuilder sb;
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) website.openConnection();
                read = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                sb = new StringBuilder();
                String line = null;
                while ((line = read.readLine()) != null)
                    sb.append(line + "\n");
                reader = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return reader;
    }


	private static ArrayList<String> butcher(String toAnalyze){
		ArrayList<String> full = new ArrayList<String>();
		String[] versus;
		String tempor="";
		versus=toAnalyze.split("\n");
		//System.out.print(versus[0]+"\n");
		tempor+= versus[0].substring(0, versus[0].length()-2)+"\n";
		for (int i=1;i<versus.length-1;i++){

			if(versus[i].indexOf("]")==0)
				//System.out.print("Skip");
				;

			if(versus[i].indexOf("]")>0){
				tempor+=versus[i];
				full.add(tempor.replace("]", ""));
				tempor="";
			}
			else{
				tempor+=versus[i].replace("[", "")+"\n";
			}
		}
		return full;

	}

	public static String[] filtered(String t){

		String[] workingon=null;
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
		//System.out.print(i+" su "+ workingon.length);
		return hey2;
	}

	public static String blanks(String raw){
		String clean;
        clean = "";
        raw=raw.replace("\n", "");
		clean=raw.replace(" ", "");
		return clean;
	}

}