package com.esoume.android.meteo;


/**
 * Un objet qui decrit les parametres d une ville utilisee pour la meteo
 * @date 08/01/2012
 * @author Emmanuel Soume (www.emmanuel-soume.ca)
 *
 */
public  class City {
    /** le code de la ville*/
	String code;
	
	/** le nom de la ville en anglais*/
	String nameCityEnglish;
	
	/** le nom de la ville en francais*/
	String nameCityFrench;
	
	/** la province de la ville*/
	String provinceCity;

	/**
	 * Le constructeur de l'objet langue. 
	 * Il est protege car seule la classe CityMeteo
	 * ou ses amis peuvent l'instancier. 
	 * @param code le code de la ville
	 * @param nom le nom de la ville
	 */
	protected City(String code, String nameCityEnglish, String nameCityFrench, String provinceCity) {
		this.code = code;
		this.nameCityEnglish = nameCityEnglish;
		this.nameCityFrench = nameCityFrench;
		this.provinceCity = provinceCity;
	}
    
	/**
	 * Obtient le code de la ville
	 * @return le code de la ville  (fr, en , ...)
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Obtient le nom de la ville
	 * @return le nom de la ville  (Montreal , Laval, ...)
	 */
	public String getNameCityEnglish() {
		return nameCityEnglish;
	}

	/**
	 * Obtient le nom de la ville
	 * @return le nom de la ville  (Montreal , Laval, ...)
	 */
	public String getNameCityFrench() {
		return nameCityFrench;
	}
	
	/**
	 * Obtient le nom de la province
	 * @return le nom de la ville  (Montreal , Laval, ...)
	 */
	public String getProvinceCity() {
		return provinceCity;
	}
	
	public String toString() {
		return "code "+code+" ville "+nameCityFrench+"province "+provinceCity;
	}

}