package com.webcash.sws.comm.pref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Pref {
	
	protected static String getString(Context ctx, String pkey, String key, String def) {
		String value = "";
		
		SharedPreferences pref = ctx.getSharedPreferences(pkey, Activity.MODE_PRIVATE);
		if(pref == null) return value;
		value = pref.getString(key, def);
		return value;
	}
	
	protected static void setString(Context ctx, String pkey, String key, String val) {
		SharedPreferences Pref = ctx.getSharedPreferences(pkey, Activity.MODE_PRIVATE);
    	SharedPreferences.Editor editor = Pref.edit();
    	editor.putString(key, val);
    	editor.commit();
	}	
	
	protected static int getInt(Context ctx, String pkey, String key, int def) {
		int value = 0;
		
		SharedPreferences pref = ctx.getSharedPreferences(pkey, Activity.MODE_PRIVATE);
		if(pref == null) return value;
		value = pref.getInt(key, def);
		return value;
	}
	
	protected static void setInt(Context ctx, String pkey, String key, int val) {
		SharedPreferences Pref = ctx.getSharedPreferences(pkey, Activity.MODE_PRIVATE);
    	SharedPreferences.Editor editor = Pref.edit();
    	editor.putInt(key, val);
    	editor.commit();
	}	
	
	protected static boolean getBoolean(Context ctx, String pkey, String key, boolean def) {
		boolean value = false;		
		SharedPreferences pref = ctx.getSharedPreferences(pkey, Activity.MODE_PRIVATE);
		if(pref == null) return value;
		value = pref.getBoolean(key, def);
		return value;
	}
	
	protected static void setBoolean(Context ctx, String pkey, String key, boolean val) {
		SharedPreferences Pref = ctx.getSharedPreferences(pkey, Activity.MODE_PRIVATE);
    	SharedPreferences.Editor editor = Pref.edit();
    	editor.putBoolean(key, val);
    	editor.commit();
	}
	
	
	protected static boolean clearPreferences(Context ctx, String pkey) {
		try {
			SharedPreferences Pref = ctx.getSharedPreferences(pkey, Activity.MODE_PRIVATE);
	    	SharedPreferences.Editor editor = Pref.edit();
	    	editor.clear();
	    	editor.commit();
	    	return true;
		} catch (Exception e) {
			return false;
		}
	}
}
