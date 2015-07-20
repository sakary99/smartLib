package com.webcash.sws.comm.tx.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.webcash.sws.comm.debug.PrintLog;

/**
 * // PUSH 센터로 디바이스등록
 * @example <br>
	PushTran pushTran = new PushTran(this);<br>			
	TX_PUSH_PS0002_REQ reqMsg = new TX_PUSH_PS0002_REQ(this, TX_PUSH_PS0002_REQ.TXNO);<br>
	reqMsg.setAPPID(getPackageName());<br>
	reqMsg.setCOMPANYID(PushTran.PUSH_COMPANY_ID);<br>
	reqMsg.setDEVICEID(BizPref.Push.getPushID(this));<br>
	reqMsg.setMODELNAME(Build.MODEL);<br>
	reqMsg.setOS(Build.VERSION.RELEASE);<br>
	reqMsg.setPUSHID(BizPref.Push.getPushID(this));<br>
	reqMsg.setPUSHSERVERKIND("GCM");<br>
	reqMsg.setRELATIONKEY(BizPref.Config.getUserId(this));<br>
	reqMsg.setREMARK("Android");<br><br>
	
	pushTran.makeJsonData(TX_PUSH_PS0002_REQ.TXNO, reqMsg.getSendMessage());<br>			
	pushTran.execute(TX_PUSH_PS0002_REQ.TXNO);	<br>
 */

public class PushTran extends AsyncTask<String, Object, JSONObject> {
	
	public static class PushBundleKey {
		public static final String KEY_MESSAGGE = "_d";
		public static final String KEY_NOTI_ID = "_mid";
		public static final String KEY_CONTROL_CD = "_c";
		public static final String KEY_BADGE = "_badge";
	}
	
	private final String KEY_TRAN_RES_DATA = "_tran_res_data"; 
	
	public static String TAG = "PUSH";
	
	private String mPushSiteDomain = "";
	
	public interface onPushTranListener {
		public void onPushTranSuccess(String tranCd);
		public void onPushTranError(String tranCd, String errorMsg, String errorCd);
	}
	
	private String mInput = "";
	private String mTranCd;
	private onPushTranListener mListener;
	
	public PushTran(String pushSiteDomain, onPushTranListener listener) {
		mPushSiteDomain = pushSiteDomain;
		mListener = listener;
	}

	
	@Override 
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		String strResponseValue = "";

		final int TIMEOUT = 10000; // milliseconds

		// ////////////////////////////////////////////////
		// HTTP Connection 소켓 타임아웃..
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT);
		// /////////////////////////////////////////////////

		try {
			
			

			HttpPost post = new HttpPost(mPushSiteDomain);			

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("JSONData", mInput));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			
			PrintLog.printSingleLog(PushTran.TAG, "[PUSH CENTER - REQUEST] " + mTranCd );
			PrintLog.printSingleLog(PushTran.TAG, mPushSiteDomain + "?" + nameValuePairs.get(0).getName() + "=" + nameValuePairs.get(0).getValue());
			PrintLog.printSingleLog("sendData()", "_input:::[" + getClass().getName() +"]" + mPushSiteDomain + "?" + nameValuePairs.get(0).getName() + "=" + nameValuePairs.get(0).getValue());

			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(post);
			strResponseValue = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
			
			JSONObject resultObject = new JSONObject(strResponseValue);
			
			PrintLog.printSingleLog(PushTran.TAG, "[PUSH CENTER - RESPONSE] " + mTranCd );
			PrintLog.printSingleLog(PushTran.TAG, resultObject.toString());
			PrintLog.printSingleLog("sendData()", "_output:::[" + getClass().getName() +"]" + resultObject.toString());
			
			return resultObject;

		} catch (Exception e) {
			PrintLog.printSingleLog(PushTran.TAG, e.getMessage());
			e.printStackTrace();
			try {
				JSONObject resultObject = new JSONObject();
				resultObject.put("_tran_cd", mTranCd);
				resultObject.put(KEY_TRAN_RES_DATA, new JSONArray().put(new JSONObject().put("result", false).put("_error_cd", "EXP0000").put("_error_msg", "데이터 송수신 중 오류가 발생하였습니다.")));
				return resultObject;
			} catch (Exception e2) {
				PrintLog.printSingleLog(PushTran.TAG, e.getMessage());
				return null;
			}
		}

	}

	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		
		try {
		
			boolean isReault = false;
			String errorMsg = "";
			String errorCd = "";
			
			JSONArray jsonArray = result.getJSONArray(KEY_TRAN_RES_DATA);
			if( jsonArray.length() == 1) {
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				if( !jsonObject.isNull("_result") )
					isReault = jsonObject.getBoolean("_result");
				if( !jsonObject.isNull("_error_msg") )
					errorMsg = jsonObject.getString("_error_msg");
				if( !jsonObject.isNull("_error_cd") )
					errorCd = jsonObject.getString("_error_cd");
			}
						
			if (mListener != null) {
				if ( isReault )
					mListener.onPushTranSuccess(mTranCd);
				else
					mListener.onPushTranError(mTranCd, errorMsg, errorCd);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			PrintLog.printSingleLog(PushTran.TAG, e.getMessage());
		}
	}
	
	
	/*
	 * Example -->
	 	JSONData={
		"_tran_cd":"PS0002",
		"_tran_req_data":[{
		    "_pushserver_kind":"GCM",
		    "_app_id":"com.webcash.gcm",
		    "_push_id":"abcdefghijk1234",
		    "_model_name":"SHE-E120S",
		    "_os":"4.1.2",
		    "_company_id":"업체ID",
		    "_device_id":"sd340Dfj1499fw",
		    "_relation_key":"01012345678",
		    "_retrans_yn":"N",
		    "_remark":"Android"
		}]}
	 */
	public boolean makeJsonData(String tran_cd, HashMap<String, Object> tran_req_data) {
				
		JSONObject jobjectReqData = new JSONObject();
		JSONObject jobjectInput = new JSONObject();
		
		try {

			jobjectInput.put("_tran_cd", tran_cd);
			
			Iterator<String> ite = tran_req_data.keySet().iterator();
			while (ite.hasNext()) {
				String key = ite.next();

				if (tran_req_data.get(key) instanceof java.util.List) {
					// List일 경우
					JSONArray jarray = new JSONArray();
					List<HashMap<String, String>> list = (List<HashMap<String, String>>) tran_req_data.get(key);

					for (HashMap<String, String> map : list) {
						JSONObject jobj = new JSONObject();
						Iterator<String> iteMap = map.keySet().iterator();

						while (iteMap.hasNext()) {
							String keyMap = iteMap.next();
							jobj.put(keyMap, map.get(keyMap));
						}
						jarray.put(jobj);
					}
					jobjectReqData.put(key, jarray);
				} else {
					// String일 경우
					jobjectReqData.put(key, tran_req_data.get(key));
				}
			}
			
			JSONArray jsonReqArray = new JSONArray();
			jsonReqArray.put(jobjectReqData);
			
			jobjectInput.put("_tran_req_data", jsonReqArray);	

			mTranCd = tran_cd;
			mInput = jobjectInput.toString();
			
			return true;
			
		} catch (Exception e) {
			mInput = null;
			e.printStackTrace();
			return false;
		}
	}

}
