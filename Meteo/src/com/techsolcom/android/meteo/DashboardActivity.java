package com.techsolcom.android.meteo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DashboardActivity extends Activity{

	LinearLayout side_top;
	LinearLayout side_right;
	LinearLayout side_left;
	
	String language;

	TextView titleItem,descriptionItem;
	ImageView iconItem;

	DataMeteoStore prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dashboard);
		showSlideMeteo();

		initialize();
	}

	public void initialize(){
		language = this.getResources().getConfiguration().locale.getDisplayLanguage();
		side_top = ( LinearLayout ) findViewById(R.id.side_top);
		side_right = ( LinearLayout ) findViewById(R.id.side_right);
		side_left = ( LinearLayout ) findViewById(R.id.side_left);  
		titleItem  = ( TextView ) findViewById(R.id.titleItem);
		descriptionItem  = ( TextView ) findViewById(R.id.descriptionItem);
		iconItem  = ( ImageView ) findViewById(R.id.iconItem);

		prefs = new DataMeteoStore(this);

	}

	public void showSlideMeteo(){
		String codeCity;
		/*for(int i=0;i<prefs.getSizeListMeteo();i++){
			side_right.addView(((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_dashboard, null));
			codeCity = prefs.getCodeCity(i);
			titleItem.setText(String.valueOf(language.equalsIgnoreCase("English") ? prefs.getNameCityEnglish(codeCity) : prefs.getNameCityFrench(codeCity)));
			iconItem.setBackgroundDrawable(getResources().getDrawable(IconMeteo.getDrawableMeteo(prefs.getIconCode(codeCity))));
			descriptionItem.setText(String.valueOf(prefs.getTemperature(codeCity)+"".concat(prefs.getUnityTemperature(codeCity))));
		}*/
	}
	
	public void setSlide(){
		String code;
		for(int i=0;i< 3;i++){
		  /*code = "s00006".concat(String.valueOf(i));
		  prefs.setCodeCity(code);
		  prefs.setNameCityEnglish(code, nameCity);
		  prefs.setIconCode(code, iconCode);
		  prefs.setTemperature(code, newTemperature);
		  prefs.setUnityTemperature(code, unityTemperature);*/
		}
	}

}
