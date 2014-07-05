package com.esoume.android.meteo;

import java.util.ArrayList;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

/**
 * @date 08/01/2012
 * @author Emmanuel Soume (www.emmanuel-soume.ca)
 *
 */
public class DataMeteoStore {

	Context ctx;
	String LIST_CITY = "city";
	//String NUMBER_CITY = "number city";

	public DataMeteoStore(Context ctx) {
		this.ctx = ctx;
	}

	public void setCity(String city,int number){

		if(city != null){

			// OBTENIR EDITEUR DE PREFERENCES
			SharedPreferences prefs = ctx.getSharedPreferences(LIST_CITY, Context.MODE_PRIVATE);
			Editor editor = prefs.edit();

			// SAUVER LES INFOS DE LA VILLE
			editor.putString(city, city);
			editor.putInt("number",number );
			editor.commit();
		}

	}
	
	public int getNumber(String city){
		if(city != null){
			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getInt("number",0);
		}
		else{
			return 0;
		}
	}

	public void setMeteoCity(ReaderMeteo meteo,String city){
		if(city != null){
			// OBTENIR EDITEUR DE PREFERENCES
			SharedPreferences prefs = ctx.getSharedPreferences(city, Context.MODE_PRIVATE);
			Editor editor = prefs.edit();

			// SAUVER LES INFOS DE LA VILLE
			editor.putString("nameCity", city);


			editor.putFloat("relativeHumidity", meteo.getCurrentCondition().get(0).getRelativeHumidity());
			editor.putFloat("pressure", meteo.getCurrentCondition().get(0).getPressure());
			editor.putFloat("visibility", meteo.getCurrentCondition().get(0).getVisibility());
			editor.putString("speed",meteo.getWinds().get(0).getSpeed());

			editor.putString("unityRelativeHumidity", meteo.getCurrentCondition().get(0).getunityRelativeHumidity());
			editor.putString("unityPressure", meteo.getCurrentCondition().get(0).getUnityPressure());
			editor.putString("unitySpeed", meteo.getWinds().get(0).getUnitySpeed());
			editor.putString("unityVisibility", meteo.getCurrentCondition().get(0).getUnityVisibility());


			ForecastMeteoData meteoData;

			for (int i=0;i<3;i++){
				if(meteo.getForecast().size() > 0 && meteo.getForecast().get(i) != null){
					meteoData = meteo.getForecast().get(i);	

					if(i==0){
						editor.putString("message".concat(String.valueOf(i)), meteoData.getTextForecastSummary());
					}
					else{
						editor.putString("message".concat(String.valueOf(i)), meteoData.getTextForecastName());
					}
					editor.putFloat("temperature".concat(String.valueOf(i)), meteoData.getTemperature());
					editor.putString("unityTemperature", meteoData.getUnityTemperature());
					editor.putInt("iconCode".concat(String.valueOf(i)), meteoData.getIconCode());
				}
			}

			editor.commit();
		}
	}

	public float getRelativeHumidity(String city){
		if(city != null){
			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getFloat("relativeHumidity",0.0f);
		}
		else{
			return 0.0f;
		}
	}

	public String getUnityRelativeHumidity(String city){
		if(city != null){

			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getString("unityRelativeHumidity","");

		}
		else{
			return null;
		}
	}

	public float getPressure(String city){
		if(city != null){
			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getFloat("pressure",0.0f);
		}
		else{
			return 0.0f;
		}
	}

	public String getUnityPressure(String city){
		if(city != null){

			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getString("unityPressure","");

		}
		else{
			return null;
		}
	}

	public float getVisibility(String city){
		if(city != null){
			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getFloat("visibility",0.0f);
		}
		else{
			return 0.0f;
		}
	}

	public String getUnityVisibility(String city){
		if(city != null){

			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getString("unityVisibility","");

		}
		else{
			return null;
		}
	}

	public String getSpeed(String city){
		if(city != null){
			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getString("speed","");
		}
		else{
			return null;
		}
	}

	public String getUnitySpeed(String city){
		if(city != null){

			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getString("unitySpeed","");

		}
		else{
			return null;
		}
	}

	public String getCodeCity(String city){
		if(city != null){
			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getString("code","");
		}
		else{
			return null;
		}
	}

	public String getNameCity(String city){
		if(city != null){

			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getString("nameCity","");
		}
		else{
			return null;
		}
	}

	public String getMessageInfoMeteo(String city){
		if(city != null){

			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getString("message0","");

		}
		else{
			return null;
		}
	}

	public String getToday(String city){
		if(city != null){

			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getString("message1","");

		}
		else{
			return null;
		}
	}

	public String getNextFirstDay(String city){
		if(city != null){

			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getString("message2","");

		}
		else{
			return null;
		}
	}

	public String getNextSecondDay(String city){
		if(city != null){

			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getString("message3","");

		}
		else{
			return null;
		}
	}

	public float getTemperature(String city){
		if(city != null){

			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getFloat("temperature0",0.0f);

		}
		else{
			return 0.0f;
		}
	}

	public float getTemperatureToday(String city){
		if(city != null){

			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getFloat("temperature1",0.0f);

		}
		else{
			return 0.0f;
		}
	}

	public float getTemperatureFirstToday(String city){
		if(city != null){

			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getFloat("temperature2",0.0f);

		}
		else{
			return 0.0f;
		}
	}

	public float getTemperatureSecondToday(String city){
		if(city != null){

			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getFloat("temperature3",0.0f);

		}
		else{
			return 0.0f;
		}
	}

	public String getUnityTemperature(String city){
		if(city != null){
			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getString("unityTemperature","");

		}
		else{
			return null;
		}
	}


	public int getIconCode0(String city){
		if(city != null){
			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getInt("iconCode0",0);
		}
		else{
			return 0;
		}
	}
	public int getIconCode1(String city){
		if(city != null){
			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getInt("iconCode1",0);
		}
		else{
			return 0;
		}
	}

	public int getIconCode2(String city){
		if(city != null){
			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getInt("iconCode2",0);
		}
		else{
			return 0;
		}
	}

	public int getIconCode3(String city){
		if(city != null){
			SharedPreferences prefs = ctx.getSharedPreferences(city,Context.MODE_PRIVATE);
			return prefs.getInt("iconCode3",0);
		}
		else{
			return 0;
		}
	}

}
