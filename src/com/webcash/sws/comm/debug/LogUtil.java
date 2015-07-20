package com.webcash.sws.comm.debug;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.text.TextUtils;

public class LogUtil {
	
	private static final String SITE_DOMAIL_URL = "http://112.187.199.29/push/psb_svc_bye/";
	
	public static void sendLogToServer(String deviceid, Exception e) {
		
		if (TextUtils.isEmpty(deviceid))
			return;
		
		SendLogToServer sendLog = new SendLogToServer();
		StringBuilder value =  new StringBuilder();
		for(int i=0;i < e.getStackTrace().length; i++) {
			value.append(e.getStackTrace()[i].toString());
		}
		
		sendLog.execute(deviceid, LogUtil.CurrDateTimeFormatted(), "Exception", value.toString());
		
	}
	
	public static void sendLogToServer(String deviceid, String datetime, String tag, String value) {
		
		if (TextUtils.isEmpty(deviceid) || TextUtils.isEmpty(datetime))
			return;
		
		SendLogToServer sendLog = new SendLogToServer();
		sendLog.execute(deviceid, datetime, tag, value);
		
	}
	
	private static class SendLogToServer extends  AsyncTask<String, Object, Boolean> {
				
		@Override 
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			boolean result = false;
			
			try {
			
				
				final int TIMEOUT = 10000; // milliseconds
				
				// ////////////////////////////////////////////////
				// HTTP Connection
				HttpParams httpParameters = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT);
				HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT);
				// /////////////////////////////////////////////////
				
				HttpPost post = new HttpPost(SITE_DOMAIL_URL);				
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("deviceid", params[0]));
				nameValuePairs.add(new BasicNameValuePair("datetime", params[1]));
				nameValuePairs.add(new BasicNameValuePair("tag", params[2]));
				nameValuePairs.add(new BasicNameValuePair("value", params[3]));				
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
				
				
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(post);
				String strResponseValue = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
				
				//Log.d(params[2], params[3]);
				PrintLog.printSingleLog(params[2], params[3]);
				
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}			
			
			return result;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			
		}
		
	}
	
	
	
	public static String CurrDate(String delem) {
		Date today;
		today = Calendar.getInstance().getTime();

		SimpleDateFormat format = new SimpleDateFormat(delem);
		String strDay = format.format(today);

		return strDay;
	}
	
	public static String CurrDateTimeFormatted() {
		return CurrDate("yyyy-MM-dd") + " " + CurrDate("HH:mm:ss SSS");
	}
	
}
