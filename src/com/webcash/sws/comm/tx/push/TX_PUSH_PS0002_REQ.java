/** 
 * <pre>
 * PUSH를 수신할 디바이스를 등록하는 송신데이터
 * @COPYRIGHT (c) 2014 WebCash, Inc. All Right Reserved.
 *
 * @author       : WebCash
 * @Description  :
 * @History      :
 *
 * </pre>
 **/
package com.webcash.sws.comm.tx.push;

import java.util.HashMap;

import org.json.JSONException;

import android.content.Context;

import com.webcash.sws.comm.define.Msg;
import com.webcash.sws.comm.tx.TxField;
import com.webcash.sws.comm.tx.TxMessage;
import com.webcash.sws.comm.tx.TxRecord;
import com.webcash.sws.comm.util.BizException;

/**
 * PUSH를 수신할 디바이스를 등록한다.
 */
public class TX_PUSH_PS0002_REQ extends TxMessage {
	
	public static final String TXNO = "PS0002";

	private static int IDX__PUSHSERVER_KIND;       //푸시 서버 종류 (APNS or GCM)
	private static int IDX__APP_ID;                //앱 ID (패키지네임 or 번들아이덴티파이어)
	private static int IDX__PUSH_ID;               //푸시 ID (GCM rid or APNS Token value)
	private static int IDX__MODEL_NAME;            //기기의 모델명
	private static int IDX__OS;                	   //모바일 운영체제 버전
	private static int IDX__COMPANY_ID;            //업체 ID
	private static int IDX__DEVICE_ID;             //기기 ID
	private static int IDX__RELATION_KEY;          //등록할 연계키
	private static int IDX__RETRANS_YN;            //재전송여부 (Y or N)
	private static int IDX__REMARK;                //비고


	public TX_PUSH_PS0002_REQ(Context ctx, String txNo) throws Exception {
		mTxNo = TXNO;
		mLayout = new TxRecord();
	
		IDX__PUSHSERVER_KIND = mLayout.addField(new TxField("_pushserver_kind","푸시 서버 종류"));
		IDX__APP_ID = mLayout.addField(new TxField("_app_id","앱 ID"));
		IDX__PUSH_ID = mLayout.addField(new TxField("_push_id","푸시 ID"));
		IDX__MODEL_NAME = mLayout.addField(new TxField("_model_name","기기의 모델명"));
		IDX__OS = mLayout.addField(new TxField("_os","모바일 운영체제 버전"));
		IDX__COMPANY_ID = mLayout.addField(new TxField("_company_id","업체 ID"));
		IDX__DEVICE_ID = mLayout.addField(new TxField("_device_id","기기 ID"));
		IDX__RELATION_KEY = mLayout.addField(new TxField("_relation_key","등록할 연계키"));
		IDX__RETRANS_YN = mLayout.addField(new TxField("_retrans_yn","재전송여부"));
		IDX__REMARK = mLayout.addField(new TxField("_remark","비고"));
	
		super.initSendMessage(ctx, txNo);
		
	}


	/**
	 * 푸시 서버 종류<p>
	 * APNS or GCM
	 */
	public void setPUSHSERVERKIND(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__PUSHSERVER_KIND).getId(), value);
	}


	/**
	 * 푸시 서버 종류<p>
	 * APNS or GCM
	 */
	public String getPUSHSERVERKIND() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__PUSHSERVER_KIND).getId());
	}


	/**
	 * 앱 ID<p>
	 * 패키지네임 or 번들아이덴티파이어
	 */
	public void setAPPID(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__APP_ID).getId(), value);
	}


	/**
	 * 앱 ID<p>
	 * 패키지네임 or 번들아이덴티파이어
	 */
	public String getAPPID() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__APP_ID).getId());
	}


	/**
	 * 푸시 ID <p>
	 * GCM rid or APNS Token value
	 */
	public void setPUSHID(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__PUSH_ID).getId(), value);
	}


	/**
	 * 푸시 ID <p>
	 * GCM rid or APNS Token value
	 */
	public String getPUSHID() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__PUSH_ID).getId());
	}


	/**
	 * 기기의 모델명
	 * @param value
	 * @throws Exception
	 */
	public void setMODELNAME(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__MODEL_NAME).getId(), value);
	}


	/**
	 * 기기의 모델명
	 * @param value
	 * @throws Exception
	 */
	public String getMODELNAME() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__MODEL_NAME).getId());
	}


	/**
	 * 모바일 운영체제 버전
	 * @param value
	 * @throws Exception
	 */
	public void setOS(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__OS).getId(), value);
	}


	/**
	 * 모바일 운영체제 버전
	 * @param value
	 * @throws Exception
	 */
	public String getOS() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__OS).getId());
	}


	/**
	 * 업체 ID
	 * @param value
	 * @throws Exception
	 */
	public void setCOMPANYID(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__COMPANY_ID).getId(), value);
	}


	/**
	 * 업체 ID
	 * @param value
	 * @throws Exception
	 */
	public String getCOMPANYID() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__COMPANY_ID).getId());
	}


	/**
	 * 기기 ID
	 * @param value
	 * @throws Exception
	 */
	public void setDEVICEID(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__DEVICE_ID).getId(), value);
	}


	/**
	 * 기기 ID
	 * @param value
	 * @throws Exception
	 */
	public String getDEVICEID() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__DEVICE_ID).getId());
	}


	/**
	 * 등록할 연계키
	 * @param value
	 * @throws Exception
	 */
	public void setRELATIONKEY(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__RELATION_KEY).getId(), value);
	}


	/**
	 * 등록할 연계키
	 * @param value
	 * @throws Exception
	 */
	public String getRELATIONKEY() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__RELATION_KEY).getId());
	}


	/**
	 * 재전송여부 <p>
	 * Y or N
	 */
	public void setRETRANSYN(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__RETRANS_YN).getId(), value);
	}


	/**
	 * 재전송여부 <p>
	 * Y or N
	 */
	public String getRETRANSYN() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__RETRANS_YN).getId());
	}


	/**
	 * 비고
	 * @param value
	 * @throws Exception
	 */
	public void setREMARK(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__REMARK).getId(), value);
	}


	/**
	 * 비고
	 * @param value
	 * @throws Exception
	 */
	public String getREMARK() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__REMARK).getId());
	}

}
