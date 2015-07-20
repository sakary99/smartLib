package com.webcash.sws.comm.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.webcash.sws.comm.pref.LibConf;

public class ComUtil {
	public static final int		CAPTURE_SDCARD = 0;
	public static final int		CAPTURE_MEMORY = 1;

	public static String conStr(String msg, String...vargs) {
		String tmp = msg;
		for(String var : vargs){
			tmp = tmp.replaceFirst(LibConf.REGULAR_EXPR, var);
		}
		return tmp;
	}

	/**
	 * View를 캡쳐한다.
	 */
	public static String captureView(View v) throws Exception {
		v.setDrawingCacheEnabled(true);
		v.buildDrawingCache();

		String filename = android.os.Environment.getExternalStorageDirectory() + "/DCIM/Camera/" + new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + ".jpg";
		Bitmap srcimg = v.getDrawingCache();

		FileOutputStream	out = new FileOutputStream(new File(filename));

		srcimg.compress(Bitmap.CompressFormat.JPEG, 100, out);
		out.close();

		return filename;
	}

	/**
	 * 비트맵을 메모리에 올리지 않고  가로길이를 가져옴
	 * @param fileName 
	 * @return 비트맵의 가로길이
	 */
	public static int getBitmapOfWidth( String fileName ){ 
		try { 
			BitmapFactory.Options options = new BitmapFactory.Options(); 
			options.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(fileName, options); 
			return options.outWidth; 
		} catch(Exception e) { 
			return 0; 
		} 
	} 

	/**
	 * 비트맵을 메모리에 올리지 않고 세로길이를 가져옴
	 * @param fileName 
	 * @return 비트맵의 세로길이
	 */
	public static int getBitmapOfHeight( String fileName ){ 
		try { 
			BitmapFactory.Options options = new BitmapFactory.Options(); 
			options.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(fileName, options); 
			return options.outHeight; 
		} catch(Exception e) { 
			return 0; 
		} 
	} 

	/**
	 * 이미지변환 
	 * <br>byte[]를 Bitmap으로 변환 </br>
	 */

	public static Bitmap byteArrayToBitmap(byte[] data) {
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inSampleSize = 10;
		Bitmap preView_image = BitmapFactory.decodeByteArray(data, 0, data.length, op);
		return preView_image;
	}
	/**
	 * 이미지변환 
	 * <br>byte[]를 Bitmap으로 변환 </br>
	 * <br>sampling Rate 조정 </br>
	 */
	public static Bitmap byteArrayToBitmap(byte[] data,int sampleRate) {
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inSampleSize = sampleRate;
		Bitmap preView_image = BitmapFactory.decodeByteArray(data, 0, data.length, op);
		return preView_image;
	}

	/**
	 * 핸드폰번호정보 를 가져온다
	 */	
	public static String getPhoneNumber(Activity acti)
	{
		try {
			TelephonyManager telephony = (TelephonyManager) acti.getSystemService(Context.TELEPHONY_SERVICE);
			String phoneNumber = telephony.getLine1Number().trim();
			if(phoneNumber == null)	return "";		

			return phoneNumber;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}


	/**
	 * 국가번호가 제외된 핸드폰번호정보 를 가져온다
	 */	
	public static String getPhoneNumberNoneNationCode(Activity acti)
	{
		try {
			TelephonyManager telephony = (TelephonyManager) acti.getSystemService(Context.TELEPHONY_SERVICE);
			String phoneNumber = telephony.getLine1Number().trim();
			if(phoneNumber == null)	return "";		

			if(phoneNumber.contains("+")){	// +82가 전화번호앞에 있는경우
				phoneNumber = "0"+phoneNumber.substring(3, phoneNumber.length());
			}

			return phoneNumber;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 핸드폰 OS정보 를 가져온다
	 */	
	public static String getOsVer(Activity acti)
	{
		TelephonyManager telephony = (TelephonyManager) acti.getSystemService(Context.TELEPHONY_SERVICE);
		return telephony.getDeviceSoftwareVersion();
	}


	/**
	 * Log를 남긴다. (공통에서만 사용할 것)
	 */
	public static void printLog(String tag, String msg, String...vargs)
	{
		//임시
		if(LibConf.ISRELEASE) return;

		for(String var : vargs){
			msg = msg.replaceFirst(LibConf.REGULAR_EXPR, var);
		}		

		Log.d(tag, msg);
	}

	/**
	 * Device 고유의 ID값을 가져온다
	 */	
	public static String getDeviceId(Activity acti)
	{
		String device_id = "";

		try 
		{
			TelephonyManager telephony = (TelephonyManager) acti.getSystemService(Context.TELEPHONY_SERVICE);
			device_id = telephony.getDeviceId();

			if(device_id == null) device_id = "";

			return device_id;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}	

	/**
	 * IP값을 가져온다
	 */
	public static String getLocalIpAddress() {
		/*
		try {
			InetAddress myIp = null;
			myIp = InetAddress.getByName("LocalHost");
			return myIp.getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
		 */

		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) 
			{
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) 
				{
					InetAddress inetAddress = enumIpAddr.nextElement();

					if (!inetAddress.isLoopbackAddress()) 
					{
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 맥어드레스값을 가져온다
	 */
	public static String getMacAddress(Activity acti) {
		try {
			WifiManager wifiManager = (WifiManager) acti.getSystemService(acti.WIFI_SERVICE);    	
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();

			return wifiInfo.getMacAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 화면해상도를 가져온다.
	 */
	public static String getDisplay(Activity acti) {
		try {		
			return getDisplayWidth(acti) + "x" + getDisplayHeight(acti); 
		} catch (Exception e) {
			e.printStackTrace();
		}	

		return "";
	}

	public static int getDisplayWidth(Activity acti){
		try {		
			Display defaultDisplay = ((WindowManager)acti.getSystemService(acti.WINDOW_SERVICE)).getDefaultDisplay();
			return defaultDisplay.getWidth(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int getDisplayHeight(Activity acti){
		try {		
			Display defaultDisplay = ((WindowManager)acti.getSystemService(acti.WINDOW_SERVICE)).getDefaultDisplay();
			return defaultDisplay.getHeight(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 특정 어플의 설치 여부를 가져온다.
	 */
	public static boolean isApplicationInstall(Context ctx, String packname) {
		List<PackageInfo> appinfo = ctx.getPackageManager().getInstalledPackages(0);

		for(int i = 0 ; i <appinfo.size() ; i ++){
			PackageInfo pi = appinfo.get(i);

			if(packname.equals(pi.packageName))	return true;
		}

		return false;
	}

	/**
	 * 어플의 캐시메모리 삭제
	 * @return
	 */
	private static void clearApplicationCache(Activity actv, java.io.File dir){
		if(dir==null)
			dir =  actv.getCacheDir();
		else;
		if(dir==null)
			return;
		else;
		java.io.File[] children = dir.listFiles();
		try{
			for(int i=0;i<children.length;i++)
				if(children[i].isDirectory())
					clearApplicationCache(actv, children[i]);
				else children[i].delete();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 내부메모리의 경로의 파일폴더 경로를 돌려준다.
	 * @param context
	 * 
	 */
	public static String getInternalMemroyPath(Context context)
	{
		return ((File)context.getFilesDir()).getAbsolutePath();
	}

	/**
	 * 폰에 저장된 db 파일을 SDCard로 dump
	 * @param acvt
	 */
	public static void getDbDump(Activity atvt, String dbName, String packageName) {

		File f=new File("/data/data/" + packageName + "/databases/" + dbName);
		FileInputStream fis=null;
		FileOutputStream fos=null;

		try {
			fis = new FileInputStream(f);
			fos = new FileOutputStream("/mnt/sdcard/dump_" + dbName);
			while(true) {
				int i = fis.read();
				if(i!=-1) {
					fos.write(i);
				}else {
					break;
				}
			}
			fos.flush();
			Toast.makeText(atvt, "DB dump OK", Toast.LENGTH_LONG).show();
		} catch(Exception e) {
			e.printStackTrace();
			Toast.makeText(atvt, "DB dump ERROR", Toast.LENGTH_LONG).show();
		} finally {
			try {
				fos.close();
				fis.close();
			}
			catch(IOException ioe) {

			}
		}		
	}


	/**
	 * 네트워크 연결상태를 반환한다.
	 * @return false : 네트워크 연결안됨, true : 네트워크 연결됨.
	 */
	public static boolean getNetworkStatus(Activity atvt) {
		ConnectivityManager Connectivity = (ConnectivityManager)atvt.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = Connectivity.getActiveNetworkInfo(); //인터넷 연결상태
		if (netInfo == null || !netInfo.isConnectedOrConnecting())
			return false;
		else
			return true;
	}
	

	/**
	 * WiFi 연결상태를 반환한다.
	 * @return false : WiFi 연결안됨, true : WiFi 연결됨.
	 */
	public static boolean getWiFiAvailability(Context context) {
		
		ConnectivityManager connectManager= (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		try {
			if( connectManager == null )
				return false;

			NetworkInfo info = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			return (info.isAvailable() && info.isConnected());
			
		} catch(Exception e) {
			return false;
		}		
	}


	/**
	 * URL 이미지(비트맵) 리턴 
	 */
	public static Bitmap getBitmapFromURL(String fullPath ) {

		if(fullPath == null || fullPath.equals("")) {
			return null;
		}

		Bitmap imgBitmap = null;        
		HttpURLConnection conn = null;      
		BufferedInputStream bis = null;

		try {
			URL url = new URL(fullPath + ".png");            
			conn = (HttpURLConnection)url.openConnection();           
			conn.connect();

			int nSize = conn.getContentLength();            
			bis = new BufferedInputStream(conn.getInputStream(), nSize);           
			imgBitmap = BitmapFactory.decodeStream(bis);

		} catch (Exception e) {
			return null;
		} finally{
			if(bis != null) {               

				try {
					bis.close();
				} catch (IOException e) {}         
			}            
			if(conn != null ) 
			{               
				conn.disconnect();       
			}
		}
		return imgBitmap;
	}

	/**
	 * URL 이미지(비트맵) 리턴 
	 */
	public static Bitmap getBitmapFromURL2(String photoURL ) {

		if(photoURL == null || photoURL.equals("")) {
			return null;
		}

		Bitmap imgBitmap = null;        
		HttpURLConnection conn = null;      
		BufferedInputStream bis = null;

		try {
			URL url = new URL(photoURL);            
			conn = (HttpURLConnection)url.openConnection();           
			conn.connect();

			int nSize = conn.getContentLength();            
			bis = new BufferedInputStream(conn.getInputStream(), nSize);           
			imgBitmap = BitmapFactory.decodeStream(bis);

		} catch (Exception e) {
			return null;
		} finally{
			if(bis != null) {               

				try {
					bis.close();
				} catch (IOException e) {}         
			}            
			if(conn != null ) 
			{               
				conn.disconnect();       
			}
		}
		return imgBitmap;
	}

	/**
	 * 비트맵 이미지 리사이즈
	 * @param width
	 * @param height
	 * @return
	 */
	public static int getReductInSampleSize(int width, int height)	{
		int	ret = 1;

		if(width >= 3550 || height >= 3550) {
			ret = 16;		
		}else if( width >= 2592 && width < 3550  || height >= 2592  &&  height < 3550)	{
			ret = 8;			
		}else if( width >= 1280 && width <2592 || height >= 1280 && height < 2592 )	{
			ret = 6;
		} else if( width >= 640 && width < 1280 || height >= 640 && height < 1280 )	{
			ret = 2;
		} 
		return	ret;
	}

	/**
	 * 구글맵 웹서비스를 이용한 좌표값 주소변환
	 * @param context
	 * @param latitude
	 * @param longitude
	 * @return String
	 * @throws Exception
	 */

	public static String searchAddressByCoord(Context context, double latitude, double longitude) throws Exception {
		String addressXml = null;

		String displayLanguage = context.getResources().getConfiguration().locale.getDisplayLanguage();
		String googleUrl = "http://www.google.com/maps/api/geocode/json?latlng=%s,%s&sensor=true&language=%s";
		// 단말의 언어가 한국어로 설정되어 있으면 주소를 한글로 설정하고, 이외는 영어로 설정한다.
		String language = "한국어".equals(displayLanguage) ? "ko" : "en";
		googleUrl = String.format(googleUrl, String.valueOf(latitude), String.valueOf(longitude), language);

		DefaultHttpClient httpClient = null;
		LineNumberReader reader = null;
		try {
			httpClient =new DefaultHttpClient();
			HttpGet mGetMethod = new HttpGet(googleUrl);
			HttpResponse mHttpRes = httpClient.execute(mGetMethod);

			if (mHttpRes.getStatusLine().getStatusCode() != 200) return null;

			reader = new LineNumberReader(new InputStreamReader(mHttpRes.getEntity().getContent()));
			StringBuilder httpResStr = new StringBuilder();
			String readStr;

			while ((readStr = reader.readLine()) != null) {
				httpResStr.append(readStr);
			}

			addressXml = httpResStr.toString();

			if(addressXml == null || addressXml.length() == 0) return null;

			JSONObject obj = new JSONObject(addressXml);

			String status = obj.getString("status");

			if(!"OK".equals(status)) return null;

			JSONArray array = obj.getJSONArray("results");
			JSONObject result = array.getJSONObject(0);
			addressXml= result.getString("formatted_address");

		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (httpClient != null) {
				httpClient = null;
			}
			if (reader != null) {
				reader.close();
				reader = null;
			}
		}
		printLog("daon", "searchAddressByCoord addressXml " + addressXml);
		return addressXml;
	}

	/**
	 * 구글맵 웹서비스를 이용한 좌표값 주소변환
	 * @param context
	 * @param latitude
	 * @param longitude
	 * @return String[]
	 * @throws Exception
	 */

	public static List<Address> searchListAddressByCoord(Context context, double latitude, double longitude) throws Exception {
		ArrayList<Address> strAddress = new ArrayList<Address>();
		String addressXml = null;

		String displayLanguage = context.getResources().getConfiguration().locale.getDisplayLanguage();
		String googleUrl = "http://www.google.com/maps/api/geocode/json?latlng=%s,%s&sensor=true&language=%s";
		// 단말의 언어가 한국어로 설정되어 있으면 주소를 한글로 설정하고, 이외는 영어로 설정한다.
		String language = "한국어".equals(displayLanguage) ? "ko" : "en";
		googleUrl = String.format(googleUrl, String.valueOf(latitude), String.valueOf(longitude), language);

		DefaultHttpClient httpClient = null;
		LineNumberReader reader = null;
		try {
			httpClient =new DefaultHttpClient();
			HttpGet mGetMethod = new HttpGet(googleUrl);
			HttpResponse mHttpRes = httpClient.execute(mGetMethod);

			if (mHttpRes.getStatusLine().getStatusCode() != 200) return null;

			reader = new LineNumberReader(new InputStreamReader(mHttpRes.getEntity().getContent()));
			StringBuilder httpResStr = new StringBuilder();
			String readStr;

			while ((readStr = reader.readLine()) != null) {
				httpResStr.append(readStr);
			}

			addressXml = httpResStr.toString();

			if(addressXml == null || addressXml.length() == 0) return null;

			JSONObject obj = new JSONObject(addressXml);

			String status = obj.getString("status");

			if(!"OK".equals(status)) return null;

			JSONArray array = obj.getJSONArray("results");
			JSONObject result = array.getJSONObject(0);
			JSONObject loc = result.getJSONObject("geometry").getJSONObject("location");
			addressXml= result.getString("formatted_address");
			double lat = Double.parseDouble(loc.getString("lat"));
			double lng = Double.parseDouble(loc.getString("lng"));

			printLog("daon", "searchAddressByCoord addressXml " + addressXml);
			printLog("daon", "searchCoordByAddress lat " + lat);
			printLog("daon", "searchCoordByAddress lng " + lng);

			Address addr = new Address(Locale.KOREA);
			addr.setAddressLine(0, addressXml);
			addr.setLatitude(lat);
			addr.setLongitude(lng);
			strAddress.add(addr);
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (httpClient != null) {
				httpClient = null;
			}
			if (reader != null) {
				reader.close();
				reader = null;
			}
		}

		return strAddress;
	}



	/**
	 * 구글맵 웹서비스를 이용한 주소값 좌표변환
	 * @param context
	 * @param address
	 * @return List<Address>
	 * @throws Exception
	 */
	public static List<Address> searchListCoordByAddress(Context context, String address) throws Exception {
		ArrayList<Address> strAddress = new ArrayList<Address>();
		String addressXml = null;

		String displayLanguage = context.getResources().getConfiguration().locale.getDisplayLanguage();
		String googleUrl = "http://www.google.com/maps/api/geocode/json?address=%s&sensor=true&language=%s";
		// 단말의 언어가 한국어로 설정되어 있으면 주소를 한글로 설정하고, 이외는 영어로 설정한다.
		String language = "한국어".equals(displayLanguage) ? "ko" : "en";
		googleUrl = String.format(googleUrl, String.valueOf(address), language);

		DefaultHttpClient httpClient = null;
		LineNumberReader reader = null;
		try {
			httpClient =new DefaultHttpClient();
			HttpGet mGetMethod = new HttpGet(googleUrl);
			HttpResponse mHttpRes = httpClient.execute(mGetMethod);

			if (mHttpRes.getStatusLine().getStatusCode() != 200) return null;

			reader = new LineNumberReader(new InputStreamReader(mHttpRes.getEntity().getContent()));
			StringBuilder httpResStr = new StringBuilder();
			String readStr;

			while ((readStr = reader.readLine()) != null) {
				httpResStr.append(readStr);
			}

			addressXml = httpResStr.toString();

			if(addressXml == null || addressXml.length() == 0) return null;

			JSONObject obj = new JSONObject(addressXml);

			String status = obj.getString("status");

			if(!"OK".equals(status)) return null;

			JSONArray array = obj.getJSONArray("results");
			JSONObject result = array.getJSONObject(0);
			JSONObject loc = result.getJSONObject("geometry").getJSONObject("location");
			addressXml= result.getString("formatted_address");
			double lat = Double.parseDouble(loc.getString("lat"));
			double lng = Double.parseDouble(loc.getString("lng"));

			printLog("daon", "searchAddressByCoord addressXml " + addressXml);
			printLog("daon", "searchCoordByAddress lat " + lat);
			printLog("daon", "searchCoordByAddress lng " + lng);

			Address addr = new Address(Locale.KOREA);
			addr.setAddressLine(0, addressXml);
			addr.setLatitude(lat);
			addr.setLongitude(lng);
			strAddress.add(addr);

		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (httpClient != null) {
				httpClient = null;
			}
			if (reader != null) {
				reader.close();
				reader = null;
			}
		}

		return strAddress;
	}



	/**
	 * 소프트키보드 숨기기
	 */
	public static void softkeyboardHide(Context ctx, EditText et) {
		if (et == null) return;
		InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
	}

	/**
	 * 소프트키보드 보이기
	 */
	public static void softkeyboardShow(final Context ctx, final EditText et) {

		//		TimerTask timerTask = new TimerTask() {
		//			public void run() {
		//				InputMethodManager inputMethodManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
		//				inputMethodManager.showSoftInput( et, InputMethodManager.SHOW_FORCED );
		//			}
		//		};
		//		Timer timer = new Timer();
		//		timer.schedule( timerTask, 500 );


		InputMethodManager inputMethodManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput( et, InputMethodManager.SHOW_FORCED );

	}


	/**
	 * 앱의 현재 버전을 가져온다
	 */
	public static String getCurrentAppVer(Context context, String packageName) throws Exception {

		return context.getPackageManager().getPackageInfo(packageName, 0).versionName.trim();

	}

	/**
	 * 홈스크린에 바로가기 만들기
	 */
	public static void addShortcutToHomeScreen(Context context, String launcherActivitClassName, String shortcutName, Bitmap icon) {

		//Adding shortcut for MainActivity 
		//on Home screen
		Intent shortcutIntent = new Intent();
		shortcutIntent.setClassName(context, launcherActivitClassName);

		shortcutIntent.setAction(Intent.ACTION_MAIN);

		Intent addIntent = new Intent();
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);

		addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		context.getApplicationContext().sendBroadcast(addIntent);

	}

	/**
	 * 앱 업데이트 페이지로 이동한다.
	 * @param context
	 * @param url : http, https, market 모두 가능 <br> Null 이거나 빈값이면 패키지명으로 Play Store 에서 검색한다.
	 * 
	 */
	public static void goToAppUpdate(Context context, String url) {
		try {
			Uri updateUri;
			if(TextUtils.isEmpty(url) == false && url.indexOf("http") >= 0 ) {
				updateUri = Uri.parse(url);
			} else {
				updateUri = Uri.parse("market://details?id=" + context.getPackageName());
			}
			Intent intent = new Intent(Intent.ACTION_VIEW, updateUri );
			context.startActivity(intent);
		} catch (Exception e) {	
			// When Play Store is not installed on the target device.
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));						
		}
	}


	/**
	 * 디바이스에 전화걸기 기능이 있는지 확인하고, 전화화면을 호출한다.
	 * @param context
	 * @param phoneNumber : 전화번호
	 */
	public static void CallPhone(Context context, String phoneNumber) {

		try {

			if( context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY) == false )
				return;

			if (TextUtils.isEmpty(phoneNumber))
				return;

			String callNumber = Convert.getNumber(phoneNumber);
			if (callNumber.length() >= 10) {
				// 전화걸기
				Intent callIntent = new Intent(Intent.ACTION_DIAL);
				callIntent.setData(Uri.parse("tel:"+callNumber));
				context.startActivity(callIntent);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/**
	 * 휴대폰번호인지 판단한다.
	 * @param s (폰번호)
	 * @return true : 휴대폰번호 임, false : 휴대폰번호가 아님
	 */
	public static boolean isPhoneNumber(String s) {
		if(s == null) return false;
		s = s.replace(" ", "");
		if(s.contains("-")) {
			Pattern telephone = Pattern.compile("^01(?:0|1[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$");

			Matcher m = telephone.matcher(s); 

			if (m.matches()){
				return true;
			} else {
				return false;
			}
		}
		else {
			if( (s.startsWith("010") || s.startsWith("016") || s.startsWith("017") || s.startsWith("018") || s.startsWith("019"))
					&& (s.length() >=10 && s.length() <=11)
				) return true;
			else return false;
		}

	}
	

	public static void setBadge(Context context, String strCount) {
		if (TextUtils.isEmpty(strCount)) {
			setBadge(context, 0);
		} else {
			try {
				setBadge(context, Integer.parseInt(strCount));
			} catch (Exception e) {
				//
			}
		}
	}

	public static void setBadge(Context context, int count) {
	    String launcherClassName = getLauncherClassName(context);
	    if (launcherClassName == null) {
	        return;
	    }
	    Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
	    intent.putExtra("badge_count", count);
	    intent.putExtra("badge_count_package_name", context.getPackageName());
	    intent.putExtra("badge_count_class_name", launcherClassName);
	    context.sendBroadcast(intent);
	}

	public static String getLauncherClassName(Context context) {

	    PackageManager pm = context.getPackageManager();

	    Intent intent = new Intent(Intent.ACTION_MAIN);
	    intent.addCategory(Intent.CATEGORY_LAUNCHER);

	    List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
	    for (ResolveInfo resolveInfo : resolveInfos) {
	        String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
	        if (pkgName.equalsIgnoreCase(context.getPackageName())) {
	            String className = resolveInfo.activityInfo.name;
	            return className;
	        }
	    }
	    return null;
	}
	


}