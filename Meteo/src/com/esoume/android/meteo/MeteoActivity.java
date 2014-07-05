package com.esoume.android.meteo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.esoume.android.meteo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @date 08/01/2012
 * @author Emmanuel Soume (www.emmanuel-soume.ca)
 *
 */

public class MeteoActivity extends Activity implements OnPageChangeListener {


	DataMeteoStore prefs;
	int pageSelected = 0;
	boolean isDeleteMeteo = false;

	PagerAdapterMeteo adapter;
	CharSequence[] listItemsOfCity;
	//int positionInFlip = 1;
	String language;
	static ArrayList<ReaderMeteo> listMeteo;
	static ArrayList<String> listCity;

	// on l obtient dans les prefernces
	String provinceCityDefault = "Qc"; // prefs.getprovinceCityDefault();
	String codeDefault = "s0000635"; // prefs.getCodeDefault();
	String nameFrDefault = "Montreal"; // prefs.getNameFrDefault();
	String nameEnDefault = "Montreal"; // prefs.getNameEnDefault();

	ProgressBar progressBar;

	TextView nameCity,temperature,messageInfoMeteo,humidite,metricHumidite,
	vent,metricVent,pression,metricPression,visibilite,metricVisibilite,
	today,temperatureToday,nextFirstDay,temperatureNextFirstDay,nextSecondDay,temperatureNextSecondDay;

	ImageView iconCode,iconMeteoToday,iconMeteoNextFirstDay,iconMeteoNextSecondDay;

	// parsers de flux pour la liste de ville
	ReaderCity reader;


	/** le flipper de la meteo des villes ajoutees*/
	private ViewPager viewPagerMeteo;

	/** Liste de parametres generaux de la langue*/
	private TextView titleMeteo;

	/** Liste de parametres generaux de la langue*/
	private ListView listCityMeteo;

	/** le bouton pour retour a l accueil principal*/
	Button  btnBack;

	/** le bouton pour choisir la meteo d une ville*/
	Button btnModify;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewflipper_meteo);
		initialize();
		
		listMeteo.add(getCityMeteoDefault(codeDefault));
		listCity.add((language.equalsIgnoreCase("English") ? nameEnDefault : nameFrDefault));
		
		prefs.setMeteoCity(getCityMeteoDefault(codeDefault), (language.equalsIgnoreCase("English") ? nameEnDefault : nameFrDefault));
		prefs.setCity((language.equalsIgnoreCase("English") ? nameEnDefault : nameFrDefault),pageSelected);
		
		addListMeteo(listMeteo,listCity);

		viewPagerMeteo.setAdapter(adapter);
		viewPagerMeteo.setOnPageChangeListener(this);

		btnBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) 
			{	
				deleteItem();
				//isDeleteMeteo = true;
			}
		});

		btnModify.setOnClickListener(new OnClickListener() {
			public void onClick(View v) 
			{	
				showListMeteoCity();
			}
		});

	}

	public void initialize(){

		prefs = new DataMeteoStore(this);
		adapter = new PagerAdapterMeteo(this);

		language = this.getResources().getConfiguration().locale.getDisplayLanguage();
		viewPagerMeteo = (ViewPager) findViewById(R.id.viewPagerMeteo);

		listMeteo = new ArrayList<ReaderMeteo> ();
		listCity = new ArrayList<String> ();
		//progressBar = (ProgressBar) findViewById(R.id.weather_progress);

		titleMeteo = ( TextView ) findViewById(R.id.titleMeteo);
		listCityMeteo  = (ListView) findViewById(R.id.listCityMeteo);
		btnBack = (Button) findViewById(R.id. btnDelete);
		btnModify = (Button) findViewById(R.id. btnModify);
	}


	public void showListMeteoCity(){

		//On instancie notre layout en tant que View
		LayoutInflater factory = LayoutInflater.from(this);
		final View list = factory.inflate(R.layout.list_city_meteo, null);

		//Creation de l'AlertDialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		//builder.setIcon(R.drawable.ic_popup_reminder)
		builder.setTitle(R.string.titleListCity);

		listCityMeteo = (ListView) list.findViewById(R.id.listCityMeteo);
		// on cree notre liste pour changer de langue
		String[] from = new String[] {"city"};
		int[] to = new int[] { R.id.nameCityMeteo};

		// prepare notre les liste de ville
		List<HashMap<String, Object>> cityMeteo = new ArrayList<HashMap<String, Object>>();

		reader = new ReaderCity("http://dd.meteo.ec.gc.ca/citypage_weather/xml/siteList.xml",language);

		List<String> listCity = reader.getList();

		final CharSequence[] listItemsOfCity = new CharSequence[listCity.size()];

		HashMap<String, Object> mapNameCity = new HashMap<String, Object>();

		for(int i=0;i< listCity.size();i++){
			listItemsOfCity[i] = String.valueOf(listCity.get(i)); 

			// ajoute des items dans la liste
			mapNameCity = new HashMap<String, Object>();
			mapNameCity.put("city",listItemsOfCity[i]);
			cityMeteo.add(mapNameCity);

		}

		// on configure notre adaptateur
		SimpleAdapter adapter = new SimpleAdapter(this,cityMeteo,R.layout.list_item_2_parametres,from,to);

		listCityMeteo.setAdapter(adapter);

		//ajout d un listener au clic dans la liste
		listCityMeteo.setOnItemClickListener(new OnItemClickListener() {
			//@Override
			public void onItemClick(AdapterView<?> a,View v,int position,long id) {}
		});

		builder.setItems(listItemsOfCity,new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int position) {

				//take the HashMap content infos of our item
				HashMap<String, Object> map = (HashMap<String, Object>) listCityMeteo.getItemAtPosition(position);
				String city = (String)map.get("city");

				if(city.equals(listItemsOfCity[position])) {

					for( City c : reader.getProvinceQuebecMeteo() ){
						if(c.getNameCityEnglish().equals(city) || c.getNameCityFrench().equals(city)){

							update(c.getCode(), String.valueOf(listItemsOfCity[position]));

						}
					}
				}

			}

		})
		.setNegativeButton(R.string.alertCancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichItem) {
				dialog.cancel();
			}
		});

		builder.create().show();
	}
	public void update(String code,String s){
		
		pageSelected = pageSelected + 1;
		prefs.setMeteoCity(getMeteoCity(code), s);
		prefs.setCity(s,pageSelected);
		
		listMeteo.add(getMeteoCity(code));
		listCity.add(s);
		addListMeteo(listMeteo,listCity);
	}

	public ReaderMeteo getMeteoCity(String code){

		// cette url doit etre refaite (pas beau !!)
		String url;

		if(language.equalsIgnoreCase("English")){
			url = "http://dd.meteo.ec.gc.ca/citypage_weather/xml/QC/"+code+"_e.xml";
		}
		else{
			url = "http://dd.meteo.ec.gc.ca/citypage_weather/xml/QC/"+code+"_f.xml";
		}

		return new ReaderMeteo(url);

	}

	public void addListMeteo(ArrayList<ReaderMeteo> listMeteo,ArrayList<String> listCity){
		
		
		pageSelected = pageSelected + 1;
		for(int i=0;i<listMeteo.size();i++){
		   prefs.setMeteoCity(listMeteo.get(i), listCity.get(i));
		   prefs.setCity(listCity.get(i),pageSelected);
		   pageSelected++;
		}
		adapter.addReaderMeteo(listMeteo,listCity);
		viewPagerMeteo.setCurrentItem(adapter.getCount()-1);
		
		Log.e("position view pager courant :",""+viewPagerMeteo.getCurrentItem());
		Log.e("ville :",""+listCity.get(viewPagerMeteo.getCurrentItem()));
		
	}

	public void deleteItem(){
        
		if(listMeteo.size() >= 1){
			listMeteo.remove(viewPagerMeteo.getCurrentItem());
			listCity.remove(viewPagerMeteo.getCurrentItem());
			adapter.addReaderMeteo(listMeteo,listCity);
			viewPagerMeteo.setCurrentItem(adapter.getCount()-1);
			//addListMeteo(listMeteo,listCity);
			
			if(listMeteo.size()== 1){
				Toast.makeText(this,"Impossible de supprimer toutes les villes", Toast.LENGTH_SHORT).show();
			}
		}
		
		Log.e("position view pager courant :",""+viewPagerMeteo.getCurrentItem());
		Log.e("ville :",""+listCity.get(viewPagerMeteo.getCurrentItem()));
		
	}


	public ReaderMeteo getCityMeteoDefault(String code){
		String url;

		if(language.equalsIgnoreCase("English")){

			url = "http://dd.meteo.ec.gc.ca/citypage_weather/xml/QC/"+code+"_e.xml";
		}
		else{

			url = "http://dd.meteo.ec.gc.ca/citypage_weather/xml/QC/"+code+"_f.xml";
		}
		return new ReaderMeteo(url);

	}

	@Override
	public void onPageScrollStateChanged(int page) {

	}

	@Override
	public void onPageScrolled(int page, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int page) {
		Log.e("position view pager :",""+viewPagerMeteo.getCurrentItem());
		Log.e("page selectionnee :",""+page);
		Log.e("ville :",""+listCity.get(viewPagerMeteo.getCurrentItem()));
	}

}
