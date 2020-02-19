package com.tech.cloudnausor.ohmytennispro.utils.homepage;

import android.content.Context;

import com.tech.cloudnausor.ohmytennispro.R;


public class Utils {

	//Set all the navigation icons and always to set "zero 0" for the item is a category
	public static int[] iconNavigation = new int[] {
			R.drawable.menu_dashboard, R.drawable.menu_my_accounct, R.drawable.menu_calendar, R.drawable.menu_reservation,
		    R.drawable.menu_individual, R.drawable.menu_on_demand, R.drawable.menu_club, R.drawable.menu_stage,
		    R.drawable.menu_team_building, R.drawable.menu_tornament, R.drawable.menu_tornament, R.drawable.menu_chat,R.drawable.menu_logout,0,0,0};
	
	//get title of the item navigation
	public static String getTitleItem(Context context, int posicao){		
		String[] titulos = context.getResources().getStringArray(R.array.nav_menu_items);  
		return titulos[posicao];
	} 
	
}
