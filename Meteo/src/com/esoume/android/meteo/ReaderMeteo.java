package com.esoume.android.meteo;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import android.util.Log;

/**
 * @date 10/01/2012
 * @author Emmanuel Soume (www.emmanuel-soume.ca)
 *
 */

public class ReaderMeteo {

	ArrayList<CurrentConditionMeteo> currentCondition = new ArrayList<CurrentConditionMeteo>();
	ArrayList<ForecastMeteoData> forecast = new ArrayList<ForecastMeteoData>();
	ArrayList<WindsMeteoData> winds = new ArrayList<WindsMeteoData>();
	
	Document document;

	public ReaderMeteo(String url){



		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		InputStream is;
		try {
			builder = builderFactory.newDocumentBuilder();
			is = new URL(url).openStream();

			document = builder.parse(is);
			parseDocument(document);

		} catch (MalformedURLException e) {
			Log.e("MyTag","mauvais url "+e);
		} catch (IOException e) {
			Log.e("MyTag",""+e);
		} catch (SAXException e) {
			Log.e("MyTag",""+e);
		} catch (ParserConfigurationException e) {
			Log.e("MyTag",""+e);
		}

	}
	public ArrayList<CurrentConditionMeteo> getCurrentCondition(){
		return currentCondition;
	}
	
	public ArrayList<ForecastMeteoData> getForecast(){
		return forecast;
	}
	
	public ArrayList<WindsMeteoData> getWinds(){
		return winds;
	}

	private void parseDocument(Document document){

		Element docEle = document.getDocumentElement();
		//get a nodelist of  elements
		NodeList nCurrent = docEle.getElementsByTagName("currentConditions");
		if(nCurrent != null && nCurrent.getLength() > 0) {
			for(int i = 0 ; i < nCurrent.getLength();i++) {
				//get the currentCondition element
				Element meteo = (Element)nCurrent.item(i);
					CurrentConditionMeteo e = getCurrentConditionMeteo(meteo);
					//add it to list
					currentCondition.add(e);
			}
		}
		
		NodeList n = docEle.getElementsByTagName("forecast");
		if(n != null && n.getLength() > 0) {
			for(int i = 0 ; i < n.getLength();i++) {
				//get the forecast element
				Element meteo = (Element)n.item(i);
				ForecastMeteoData e = getForecastMeteoData(meteo);
					//add it to list
					forecast.add(e);
			}
		}
		
		NodeList nWinds = docEle.getElementsByTagName("wind");
		if(nWinds != null && nWinds.getLength() > 0) {
			for(int i = 0 ; i < nWinds.getLength();i++) {
				//get the winds element
				Element meteo = (Element)nWinds.item(i);
				WindsMeteoData e = getWindsMeteoData(meteo);
					//add it to list
					winds.add(e);
			}
		}

	}

	private CurrentConditionMeteo getCurrentConditionMeteo(Element site) {
        // on recupere chaque tag
		String iconCode = getTextValue(site,"iconCode");
		String condition = getTextValue(site,"condition");
		float temperature = getFloatValue(site,"temperature");
		float dewpoint = getFloatValue(site,"dewpoint");
		float pressure = getFloatValue(site,"pressure");
		float visibility = getFloatValue(site,"visibility");
		float relativeHumidity = getFloatValue(site,"relativeHumidity");;
		String messageMeteo = site.getAttribute("code"); 
		
		// on recupere chaque attribut du tag
		String unityTemperature = getAttributValue(site,"temperature","units");
		String unityPressure = getAttributValue(site,"pressure","units");
		String unityRelativeHumidity = getAttributValue(site,"relativeHumidity","units");
		String unityDewpoint = getAttributValue(site,"dewpoint","units");
		String unityVisibility = getAttributValue(site,"visibility","units");

		//Create a new City with the value read from the xml nodes
		CurrentConditionMeteo e = new CurrentConditionMeteo(iconCode,condition,temperature,dewpoint,pressure,visibility,
				                                            relativeHumidity,messageMeteo,unityTemperature,unityPressure,unityRelativeHumidity,unityDewpoint,unityVisibility);
        
		return e;

	}
	
	private ForecastMeteoData getForecastMeteoData(Element site) {

		String textForecastName = getTextValue(site, "period");
		String textForecastSummary = getTextValue(site, "textSummary");
		int iconCode = getIntValue(site,"iconCode");
		float temperature = getFloatValue(site,"temperature");
		String unityTemperature = getAttributValue(site,"temperature","units");

		//Create a new City with the value read from the xml nodes
		ForecastMeteoData e = new ForecastMeteoData(textForecastName,textForecastSummary,iconCode,temperature,unityTemperature);

		return e;

	}
	
	private WindsMeteoData getWindsMeteoData(Element site) {
        
		// l arbre de notre element wind
		/*<wind>
		    <speed unitType="metric" units="km/h">15</speed>
		    <gust unitType="metric" units="km/h"/>
		    <direction>NE</direction>
		    <bearing units="degrees">40</bearing>
		</wind>*/
		
		String speed = getTextValue(site,"speed");
		String direction = getTextValue(site,"direction");
		float bearing = getFloatValue(site,"bearing");
		float gust = getFloatValue(site,"gust"); // rafale
		String unitySpeed = getAttributValue(site,"speed","units");
		String unityDirection = getAttributValue(site,"direction","units");

		//Create a new City with the value read from the xml nodes
		WindsMeteoData e = new WindsMeteoData(speed,direction,bearing,gust,unitySpeed,unityDirection);

		return e;
	}


	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the text content 
	 * i.e for <site><nameFr>Montreal</nameFr></site> xml snippet if
	 * the Element points to site node and tagName is nameFr I will return Montreal  
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private String getTextValue(Element element, String tagName) {
		String textVal = null;
		NodeList nList = element.getElementsByTagName(tagName);
		if(nList != null && nList.getLength() > 0) {
			Element el = (Element)nList.item(0);
			if(el.getFirstChild() != null && el.getFirstChild().getNodeValue() != null)
				textVal = el.getFirstChild().getNodeValue();
			else
				textVal = "";
		}

		return textVal;
	}
	
	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the value of attribute
	 * i.e for <site ><nameFr units="X">Montreal</nameFr></site> xml snippet if
	 * the Element points to site node and tagName is nameFr where i take the attribute value I will return X  
	 * @param ele
	 * @param tagName
	 * @param attributName
	 * @return
	 */
	private String getAttributValue(Element element, String tagName,String attributName) {
		String textVal = null;
		NodeList nList = element.getElementsByTagName(tagName);
		if(nList != null && nList.getLength() > 0) {
			Element el = (Element)nList.item(0);
			textVal = el.getAttribute(attributName);
		}

		return textVal;
	}

	/**
	 * Calls getTextValue and returns a int value
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}
	
	/**
	 * Calls getTextValue and returns a float value
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private float getFloatValue(Element ele, String tagName) {
		//in production application you would catch the exception
		String floatStr = getTextValue(ele,tagName);
		if(floatStr != null && !floatStr.equals(""))
			return Float.parseFloat(getTextValue(ele,tagName));
		else
			return 0;
	}

}
