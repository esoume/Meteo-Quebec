package com.esoume.android.meteo;

import com.esoume.android.meteo.R;

/**
 * @date 08/01/2012
 * @author Emmanuel Soume (www.emmanuel-soume.ca)
 *
 */
public class IconMeteo {
	
	static int[] drawableMeteo = new int[]{R.drawable.icon00,R.drawable.icon01,R.drawable.icon02,R.drawable.icon03,R.drawable.icon04,R.drawable.icon05,R.drawable.icon06,
			R.drawable.icon07,R.drawable.icon08,R.drawable.icon09,R.drawable.icon10,R.drawable.icon11,R.drawable.icon12,R.drawable.icon13,
			R.drawable.icon14,R.drawable.icon15,R.drawable.icon16,R.drawable.icon17,R.drawable.icon18,R.drawable.icon19,R.drawable.icon20,
			R.drawable.icon21,R.drawable.icon22,R.drawable.icon23,R.drawable.icon24,R.drawable.icon25,R.drawable.icon26,R.drawable.icon27,
			R.drawable.icon28,R.drawable.icon30,R.drawable.icon31,R.drawable.icon32,R.drawable.icon33,
			R.drawable.icon34,R.drawable.icon35,R.drawable.icon36,R.drawable.icon37,R.drawable.icon38,R.drawable.icon39,R.drawable.icon40,
			R.drawable.icon41,R.drawable.icon42,R.drawable.icon43,R.drawable.icon44,R.drawable.icon45};
	
	public static int getDrawableMeteo(int iconCode){
		return drawableMeteo[iconCode];
	}
	
	public static int getNumberIcon(){
		return drawableMeteo.length;
	}

}
