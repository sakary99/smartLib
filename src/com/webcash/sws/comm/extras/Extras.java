package com.webcash.sws.comm.extras;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.webcash.sws.comm.util.LocationItem;

public class Extras {
	protected Context mContext;
	protected Bundle mBundle;
	
	protected Extras(Context ctx) {
		mContext = ctx;
		mBundle = new Bundle();
	}
	
	protected Extras(Context ctx, Intent intent) {
		mContext = ctx;
		if (intent.getExtras() != null)
			mBundle = intent.getExtras();
		else
			mBundle = new Bundle();
	}
	
	protected Extras(Context ctx, Bundle bundle) {
		mContext = ctx;
		if (bundle != null)
			mBundle =bundle;
		else
			mBundle = new Bundle();
	}
	
	public Bundle getBundle() {
		return mBundle;
	}
	
	public Intent getIntent() {
		Intent intent = new Intent();
		intent.putExtras(mBundle);
		return intent;
	}
	
	protected void setString(String key, String value) {
		mBundle.putString(key, value);
	}
	
	protected String getString(String key) {
		if(!mBundle.containsKey(key)) return "";
		return mBundle.getString(key);
	}
	
	protected void setInt(String key, int value) {
		mBundle.putInt(key, value);
	}
	
	protected int getInt(String key) {
		return mBundle.getInt(key);
	}
	
	protected void setLong(String key, long value) {
		mBundle.putLong(key, value);
	}
	
	protected long getLong(String key) {
		return mBundle.getLong(key);
	}
	
	protected void setDouble(String key, double value) {
		mBundle.putDouble(key, value);
	}
	
	protected double getDouble(String key) {
		return mBundle.getDouble(key);
	}

	
	protected void setBoolean(String key, Boolean value) {
		mBundle.putBoolean(key, value);
	}
	
	protected Boolean getBoolean(String key) {
		if(!mBundle.containsKey(key)) return false;
		return mBundle.getBoolean(key);
	}
	
	protected void setList(String key, ArrayList<String> value) {
		mBundle.putStringArrayList(key, value);
	}
	
	protected ArrayList<String> getList(String key) {
		if(!mBundle.containsKey(key)) return null;
		return mBundle.getStringArrayList(key);
	}
	
	protected void setList2(String key, ArrayList<LocationItem> value) {
		mBundle.putSerializable(key, value);
	}
	
	@SuppressWarnings("unchecked")
	protected ArrayList<LocationItem> getList2(String key) {
		if(!mBundle.containsKey(key)) return null;
		return (ArrayList<LocationItem>)mBundle.getSerializable(key);
	}

}
