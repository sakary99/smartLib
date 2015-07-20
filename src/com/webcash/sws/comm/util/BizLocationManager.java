package com.webcash.sws.comm.util;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;


public class BizLocationManager {
	private static LocationManager mGpsLocManager;
	private static LocationManager mNetworkLocManager;

	private static LocationListener mGpsLocL;
	private static LocationListener mNetworkLocL;
	
	private static Location currentLocation;
	
	static Context mCtx;
	
	static int mGpsStatus;
	static public boolean mIsremoved = true;
	
	static List<String> mProviders;
	
	public static BizLocationListener mLocationCaller;
	
	public static interface BizLocationListener {
		public void onBizLocationChanged(Location location);
	}
	
	public BizLocationManager(Context ctx) {
		mCtx = ctx;
		initGps();
		updateCurrentLocation();
	}
	
	public BizLocationManager(Context ctx, BizLocationListener caller) {
		mCtx = ctx;
		mLocationCaller = caller;
		initGps();
		updateCurrentLocation();
	}
	
	public static boolean ismIsremoved() {
		return mIsremoved;
	}
	
	public static List<String> getmProviders() {
		return mProviders;
	}
	public static LocationManager getmNetworkLocManager() {
		return mNetworkLocManager;
	}
	
	private static void initGps() {
		if(mGpsLocManager == null) 
			mGpsLocManager = (LocationManager)mCtx.getSystemService(Context.LOCATION_SERVICE);
		if(mNetworkLocManager == null) 
			mNetworkLocManager = (LocationManager)mCtx.getSystemService(Context.LOCATION_SERVICE);
		if(mGpsLocL == null)
			mGpsLocL = new MyGpsLocationListener();
		if(mNetworkLocL == null)
			mNetworkLocL = new MyNetwrokLocationListener();
	}
	
	/**
	 * 네트워크(GPS)상태를 가져온다.
	 * @return true:GPS사용, false:GPS미사용
	 */
	public static boolean getNetworkState(){
		LocationManager lm = (LocationManager)mCtx.getSystemService(Context.LOCATION_SERVICE);  
		// 위성 GPS 
		if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			return lm.isProviderEnabled(LocationManager.GPS_PROVIDER); 
		}
		// 네트워크 GPS
		if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
			return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		}
		
		return false;
	}
	
	
	public synchronized static boolean updateCurrentLocation(){
		if(!getNetworkState()) 
			return false;
		mProviders = mNetworkLocManager.getProviders(true);
		
		if(mProviders.contains(LocationManager.GPS_PROVIDER) && mProviders.contains(LocationManager.NETWORK_PROVIDER)) {
			mGpsLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mGpsLocL);		
			mNetworkLocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mNetworkLocL);		
		}
		else if(mProviders.contains(LocationManager.GPS_PROVIDER)) 
			mGpsLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mGpsLocL);	
		else if(mProviders.contains(LocationManager.NETWORK_PROVIDER)) 
			mNetworkLocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mNetworkLocL);
		mIsremoved = false;
		return true;
	}

	public static Location getCurrentLocation() {
		if(mIsremoved) {
			updateCurrentLocation();
			return null;
		} else {
			updateCurrentLocation();
		}
		
		return currentLocation;
	}

	private static class MyGpsLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {			
			if (mLocationCaller != null) 
				mLocationCaller.onBizLocationChanged(location);
			
			currentLocation = location;
		}
		@Override
		public void onProviderDisabled(String provider){}
		@Override
		public void onProviderEnabled(String provider){}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			mGpsStatus = status;
		}
	}

	private static class MyNetwrokLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			if(mGpsStatus == LocationProvider.AVAILABLE) 
				return;
			
			if (mLocationCaller != null) 
				mLocationCaller.onBizLocationChanged(location);
			
			currentLocation = location;
		}
		@Override
		public void onProviderDisabled(String provider){}
		@Override
		public void onProviderEnabled(String provider){}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {}
	}
	
	public static void removeListener() {
		if(mGpsLocManager != null) 
			mGpsLocManager.removeUpdates(mGpsLocL);
		if(mNetworkLocManager != null) 
			mNetworkLocManager.removeUpdates(mNetworkLocL);
		mIsremoved = true;
	}
}
