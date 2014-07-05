package com.techsolcom.android.meteo;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PagerAdapterMeteo extends PagerAdapter {

	ReaderMeteo meteo;
	ArrayList<ReaderMeteo> listMeteo;
	ArrayList<View> layout;
	ArrayList<String> listCity;
	DataMeteoStore prefs;

	Context context;
	
	TextView nameCity,temperature,messageInfoMeteo,humidite,metricHumidite,
	vent,metricVent,pression,metricPression,visibilite,metricVisibilite,
	today,temperatureToday,nextFirstDay,temperatureNextFirstDay,nextSecondDay,temperatureNextSecondDay;

	ImageView iconCode,iconMeteoToday,iconMeteoNextFirstDay,iconMeteoNextSecondDay;

	public PagerAdapterMeteo(Context context) {
		this.context = context;
		listMeteo = new ArrayList<ReaderMeteo> ();
		listCity = new ArrayList<String> ();
		layout = new ArrayList<View> ();
		prefs = new DataMeteoStore(context);
	}

	public void addReaderMeteo(ArrayList<ReaderMeteo> listMeteo,ArrayList<String> listCity){

		this.listMeteo = listMeteo;
		this.listCity = listCity;
		for(int i=0;i<listMeteo.size();i++){
			layout.add(((LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.screen_meteo_city, null));
		}
	}
	
	@Override
	public void destroyItem(View view, int position, Object object) {
		((ViewPager) view).removeView((View) object);
	}

	@Override
	public void finishUpdate(View arg0) {

	}

	@Override
	public int getCount() {
		return listCity.size();
	}
	
	public String getNameCity(int position){
		return listCity.get(position).toString();
	}

	@Override
	public Object instantiateItem(View view, int position) {

		View v = layout.get(position);

		nameCity = ( TextView ) v.findViewById(R.id.nameCity);
		temperature = ( TextView ) v.findViewById(R.id.temperature);
		messageInfoMeteo = ( TextView ) v.findViewById(R.id.messageInfoMeteo);

		humidite = ( TextView ) v.findViewById(R.id.humidite);
		vent = ( TextView ) v.findViewById(R.id.vent);
		pression = ( TextView ) v.findViewById(R.id.pression);
		visibilite = ( TextView ) v.findViewById(R.id.visibilite);

		today = ( TextView ) v.findViewById(R.id.today);
		temperatureToday = ( TextView ) v.findViewById(R.id.temperatureToday);

		nextFirstDay = ( TextView ) v.findViewById(R.id.nextFirstDay);
		temperatureNextFirstDay = ( TextView ) v.findViewById(R.id.temperatureNextFirstDay);

		nextSecondDay = ( TextView ) v.findViewById(R.id.nextSecondDay);
		temperatureNextSecondDay = ( TextView ) v.findViewById(R.id.temperatureNextSecondDay);

		iconCode = (ImageView) v.findViewById(R.id.iconCode);
		iconMeteoToday = (ImageView) v.findViewById(R.id.iconMeteoToday);
		iconMeteoNextFirstDay = (ImageView) v.findViewById(R.id.iconMeteoNextFirstDay);
		iconMeteoNextSecondDay = (ImageView) v.findViewById(R.id.iconMeteoNextSecondDay);

		setInfoMeteoCity(listMeteo.get(position),listCity.get(position));

		((ViewPager) view).addView(v/*,position*/);

		return v;
	}

	public void setInfoMeteoCity(ReaderMeteo meteo,String city){

		nameCity.setText(prefs.getNameCity(city)/*city*/);

		if(meteo.getCurrentCondition().size() > 0 && meteo.getCurrentCondition().get(0) != null){
			humidite.setText(String.valueOf(prefs.getRelativeHumidity(city)/*meteo.getCurrentCondition().get(0).getRelativeHumidity()*/).
					                        concat(prefs.getUnityRelativeHumidity(city)/*meteo.getCurrentCondition().get(0).getunityRelativeHumidity()*/));
			pression.setText(String.valueOf(prefs.getPressure(city)/*meteo.getCurrentCondition().get(0).getPressure()*/).
					         concat(prefs.getUnityPressure(city)/*meteo.getCurrentCondition().get(0).getUnityPressure()*/));
			visibilite.setText(String.valueOf(prefs.getVisibility(city)/*meteo.getCurrentCondition().get(0).getVisibility()*/).
					         concat(prefs.getUnityVisibility(city)/*meteo.getCurrentCondition().get(0).getUnityVisibility()*/));
		}

		if(meteo.getWinds().size() > 0 && meteo.getWinds().get(0) != null){
			vent.setText(String.valueOf(prefs.getSpeed(city)/*meteo.getWinds().get(0).getSpeed()*/).
					     concat(prefs.getUnitySpeed(city)/*meteo.getWinds().get(0).getUnitySpeed()*/));
		}

		ForecastMeteoData meteoData;
		if(meteo.getForecast().size() > 0 && meteo.getForecast().get(0) != null){
			meteoData = meteo.getForecast().get(0);
			messageInfoMeteo.setText(prefs.getMessageInfoMeteo(city)/*meteoData.getTextForecastSummary()*/);	
			temperature.setText(String.valueOf(prefs.getTemperature(city)/*meteoData.getTemperature()*/).
					            concat(prefs.getUnityTemperature(city)/*meteoData.getUnityTemperature()*/));

			int iconSource = prefs.getIconCode0(city)/*meteoData.getIconCode()*/;
			iconCode.setBackgroundDrawable(context.getResources().getDrawable(IconMeteo.getDrawableMeteo(iconSource)));

		}		
		if(meteo.getForecast().size() > 1 && meteo.getForecast().get(1) != null){
			meteoData = meteo.getForecast().get(1);
			today.setText(prefs.getToday(city)/*meteoData.getTextForecastName()*/);
			temperatureToday.setText(String.valueOf(prefs.getTemperature(city)/*meteoData.getTemperature()*/).
		            concat(prefs.getUnityTemperature(city)/*meteoData.getUnityTemperature()*/));	

			int iconSource = prefs.getIconCode1(city)/*meteoData.getIconCode()*/;
			iconMeteoToday.setBackgroundDrawable(context.getResources().getDrawable(IconMeteo.getDrawableMeteo(iconSource)));

		}
		if(meteo.getForecast().size() > 2 && meteo.getForecast().get(2) != null){
			meteoData = meteo.getForecast().get(2);
			nextFirstDay.setText(prefs.getNextFirstDay(city)/*meteoData.getTextForecastName()*/);
			temperatureNextFirstDay.setText(String.valueOf(prefs.getTemperature(city)/*meteoData.getTemperature()*/).
		            concat(prefs.getUnityTemperature(city)/*meteoData.getUnityTemperature()*/));

			int iconSource = prefs.getIconCode2(city)/*meteoData.getIconCode()*/;
			iconMeteoNextFirstDay.setBackgroundDrawable(context.getResources().getDrawable(IconMeteo.getDrawableMeteo(iconSource)));

		}
		if(meteo.getForecast().size() > 3 && meteo.getForecast().get(3) != null){
			meteoData = meteo.getForecast().get(3);
			nextSecondDay.setText(prefs.getNextSecondDay(city)/*meteoData.getTextForecastName()*/);
			temperatureNextSecondDay.setText(String.valueOf(prefs.getTemperature(city)/*meteoData.getTemperature()*/).
		            concat(prefs.getUnityTemperature(city)/*meteoData.getUnityTemperature()*/));

			int iconSource = prefs.getIconCode3(city)/*meteoData.getIconCode()*/;
			iconMeteoNextSecondDay.setBackgroundDrawable(context.getResources().getDrawable(IconMeteo.getDrawableMeteo(iconSource)));

		}			
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {

	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {

	}

}


