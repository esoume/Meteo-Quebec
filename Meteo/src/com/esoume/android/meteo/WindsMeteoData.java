package com.esoume.android.meteo;

/**
 * @date 11/01/2012
 * @author Emmanuel Soume (www.emmanuel-soume.ca)
 *
 */

public class WindsMeteoData {
	
	private String speed;
	private String direction;
	private float bearing;
	private float gust; // rafale
	private String unitySpeed;
	private String unityDirection;
	
	public WindsMeteoData(String speed,String direction,float bearing,float gust,String unitySpeed,String unityDirection){
		
		this.speed =  speed;
		this.direction = direction;
		this.bearing = bearing; //roulement
		this.gust = gust; // rafale
		this.unitySpeed = unitySpeed;
		this.unityDirection = unityDirection;

	}
	
	/**
	 * Obtient le code de la ville
	 * @return le code de la ville  (fr, en , ...)
	 */
	public String getSpeed() {
		return speed;
	}
	
	/**
	 * Obtient le code de la ville
	 * @return le code de la ville  (fr, en , ...)
	 */
	public String getDirection() {
		return direction;
	}
	
	/**
	 * Obtient le code de la ville
	 * @return le code de la ville  (fr, en , ...)
	 */
	public float getBearing() {
		return bearing;
	}
	
	/**
	 * Obtient le unity Temperature
	 * @return le code de la ville  (fr, en , ...)
	 */
	public float getGust() {
		return gust;
	}
	
	/**
	 * Obtient le code de la ville
	 * @return le code de la ville  (fr, en , ...)
	 */
	public String getUnitySpeed() {
		return unitySpeed;
	}
	
	/**
	 * Obtient le unity Temperature
	 * @return le code de la ville  (fr, en , ...)
	 */
	public String unityDirection() {
		return unityDirection;
	}

}
