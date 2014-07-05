package com.techsolcom.android.meteo;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * 
 * @date 09/01/2012
 * @author Emmanuel Soume (TechSolCom)
 *
 */

public class ReaderCity {

	ArrayList<City> provinceQuebecMeteo = new ArrayList<City>();
	ArrayList<String> nameCityEnglishMeteo = new ArrayList<String>();
	ArrayList<String> nameCityFrenchMeteo = new ArrayList<String>();
	ArrayList<String> cityCodeMeteo = new ArrayList<String>();
	Document doc;
	String language;

	public ReaderCity(String url,String language){

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		this.language = language;

		InputStream is;
		try {
			builder = builderFactory.newDocumentBuilder();
			is = new URL(url).openStream();

			Document document = builder.parse(is);
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
	
	public ArrayList<City> getProvinceQuebecMeteo(){
		return provinceQuebecMeteo;
	}

	public ArrayList<String> getList(){
		setListNameCityEnglish(provinceQuebecMeteo);
		setListNameCityFrench(provinceQuebecMeteo);
		setListCityCode(provinceQuebecMeteo);
		
		if(language.equals("English")){
		  return nameCityEnglishMeteo;
		}
		else{
			return nameCityFrenchMeteo;
		}
		
	}

	public ArrayList<String> getListNameCityEnglish(){
		return nameCityEnglishMeteo;
	}

	public ArrayList<String> getListNameCityFrench(){
		return nameCityFrenchMeteo;
	}

	public ArrayList<String> getListcityCode(){
		return cityCodeMeteo;
	}

	public void setListNameCityEnglish(ArrayList<City> city){
		for(int i=0;i<city.size();i++){
			nameCityEnglishMeteo.add(city.get(i).getNameCityEnglish());
		}
		Collections.sort(nameCityEnglishMeteo);
	}

	public void setListNameCityFrench(ArrayList<City> city){
		for(int i=0;i<city.size();i++){
			nameCityFrenchMeteo.add(city.get(i).getNameCityFrench());
		}
		Collections.sort(nameCityFrenchMeteo);
	}

	public void setListCityCode(ArrayList<City> city){
		for(int i=0;i<city.size();i++){
			cityCodeMeteo.add(city.get(i).getCode());
		}
	}

	private void parseDocument(Document document){

		String province = "Qc";
		Element docEle = document.getDocumentElement();
		//get a nodelist of  elements
		NodeList nl = docEle.getElementsByTagName("site");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				//get the city element
				Element site = (Element)nl.item(i);
				if(province.equalsIgnoreCase(getTextValue(site,"provinceCode"))){
					City e = getCity(site);
					//add it to list
					provinceQuebecMeteo.add(e);
				}
			}
		}

	}

	private City getCity(Element site) {

		//for each <Site> element get text , int or float values of 
		//code ,nameEnglish, nameFrench and provinceCode

		String code = site.getAttribute("code");
		String nameCityEnglish = getTextValue(site,"nameEn");
		String nameCityFrench = getTextValue(site,"nameFr");
		String provinceCity = getTextValue(site,"provinceCode");

		//Create a new City with the value read from the xml nodes
		City e = new City(code,nameCityEnglish,nameCityFrench,provinceCity);
		
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
		NodeList nl = element.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	
}

