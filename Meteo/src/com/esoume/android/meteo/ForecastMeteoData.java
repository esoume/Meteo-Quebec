package com.esoume.android.meteo;

/**
 * @date 11/01/2012
 * @author Emmanuel Soume (www.emmanuel-soume.ca)
 *
 */

public class ForecastMeteoData {
	
	private String textForecastName;
	private String textForecastSummary;
	private int iconCode;
	private float temperature;
	private String unityTemperature;

	public ForecastMeteoData(String textForecastName, String textForecastSummary,int iconCode,float temperature,String unityTemperature){
		
		this.textForecastName = textForecastName;
		this.textForecastSummary = textForecastSummary;
		this.iconCode = iconCode;
		this.temperature = temperature;
		this.unityTemperature = unityTemperature;

	}
	
	/**
	 * Obtient le code de la ville
	 * @return le code de la ville  (fr, en , ...)
	 */
	public String getTextForecastName() {
		return textForecastName;
	}

	/**
	 * Obtient le sommaire
	 * @return le sommaire  (fr, en , ...)
	 */	
	public String getTextForecastSummary() {
		return textForecastSummary;
	}

	/**
	 * Obtient le code de la ville
	 * @return le code de la ville  (fr, en , ...)
	 */
	public int getIconCode() {
		return iconCode;
	}
	
	/**
	 * Obtient le code de la ville
	 * @return le code de la ville  (fr, en , ...)
	 */
	public float getTemperature() {
		return temperature;
	}
	
	/**
	 * Obtient le unity Temperature
	 * @return le code de la ville  (fr, en , ...)
	 */
	public String getUnityTemperature() {
		return unityTemperature;
	}

}
