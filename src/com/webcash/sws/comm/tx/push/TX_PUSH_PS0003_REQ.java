/** 
 * <pre>
 * 푸시 ID(GCM rid or APNS Token value)가 변경 됐을 때 디바이스 수정  송신부
 * @COPYRIGHT (c) 2014 WebCash, Inc. All Right Reserved.
 *
 * @author       : WebCash
 * @Description  :
 * @History      :
 *
 * </pre>
 **/
package com.webcash.sws.comm.tx.push;

import org.json.JSONException;

import android.content.Context;

import com.webcash.sws.comm.tx.TxField;
import com.webcash.sws.comm.tx.TxMessage;
import com.webcash.sws.comm.tx.TxRecord;

/**
 * 푸시 ID(GCM rid or APNS Token value)가 변경 됐을 때 디바이스 수정
 */
public class TX_PUSH_PS0003_REQ extends TxMessage {
	public static final String TXNO = "PS0003";

	private static int IDX__PUSHSERVER_KIND;       //푸시 서버 종류(APNS or GCM)
	private static int IDX__APP_ID;                //앱 ID(패키지네임 or 번들아이덴티파이어)
	private static int IDX__BEFORE_PUSH_ID;        //변경 전 푸시 ID(GCM rid or APNS Token value)
	private static int IDX__AFTER_PUSH_ID;         //변경 후 푸시 ID(GCM rid or APNS Token value)
	private static int IDX__COMPANY_ID;            //업체 ID
	private static int IDX__DEVICE_ID;             //기기 ID(UDID값. 디바이스에 대한 유일한 값.)
	private static int IDX__RELATION_KEY;          //등록할 연계키
	private static int IDX__RETRANS_YN;            //재전송여부(Y or N)
	private static int IDX__REMARK;                //비고

	
	public TX_PUSH_PS0003_REQ(Context ctx, String txNo) throws Exception {
		mTxNo = TXNO;
		mLayout = new TxRecord();
	
		IDX__PUSHSERVER_KIND = mLayout.addField(new TxField("_pushserver_kind","푸시 서버 종류(APNS or GCM)"));
		IDX__APP_ID = mLayout.addField(new TxField("_app_id","앱 ID(패키지네임 or 번들아이덴티파이어)"));
		IDX__BEFORE_PUSH_ID = mLayout.addField(new TxField("_before_push_id","변경 전 푸시 ID(GCM rid or APNS Token value)"));
		IDX__AFTER_PUSH_ID = mLayout.addField(new TxField("_after_push_id","변경 후 푸시 ID(GCM rid or APNS Token value)"));
		IDX__COMPANY_ID = mLayout.addField(new TxField("_company_id","업체 ID"));
		IDX__DEVICE_ID = mLayout.addField(new TxField("_device_id","기기 ID(UDID값. 디바이스에 대한 유일한 값.)"));
		IDX__RELATION_KEY = mLayout.addField(new TxField("_relation_key","등록할 연계키"));
		IDX__RETRANS_YN = mLayout.addField(new TxField("_retrans_yn","재전송여부(Y or N)"));
		IDX__REMARK = mLayout.addField(new TxField("_remark","비고"));
	
	
		super.initSendMessage(ctx, txNo);
	}


	/**
	 * 푸시 서버 종류(APNS or GCM)
	 */
	public void setPUSHSERVERKIND(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__PUSHSERVER_KIND).getId(), value);
	}


	/**
	 * 푸시 서버 종류(APNS or GCM)
	 */
	public String getPUSHSERVERKIND() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__PUSHSERVER_KIND).getId());
	}


	/**
	 * 앱 ID(패키지네임 or 번들아이덴티파이어)
	 */
	public void setAPPID(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__APP_ID).getId(), value);
	}


	/**
	 * 앱 ID(패키지네임 or 번들아이덴티파이어)
	 */
	public String getAPPID() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__APP_ID).getId());
	}


	/**
	 * 변경 전 푸시 ID(GCM rid or APNS Token value)
	 */
	public void setBEFOREPUSHID(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__BEFORE_PUSH_ID).getId(), value);
	}


	/**
	 * 변경 전 푸시 ID(GCM rid or APNS Token value)
	 */
	public String getBEFOREPUSHID() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__BEFORE_PUSH_ID).getId());
	}


	/**
	 * 변경 후 푸시 ID(GCM rid or APNS Token value)
	 */
	public void setAFTERPUSHID(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__AFTER_PUSH_ID).getId(), value);
	}


	/**
	 * 변경 후 푸시 ID(GCM rid or APNS Token value)
	 */
	public String getAFTERPUSHID() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__AFTER_PUSH_ID).getId());
	}


	/**
	 * 업체 ID
	 */
	public void setCOMPANYID(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__COMPANY_ID).getId(), value);
	}


	/**
	 * 업체 ID
	 */
	public String getCOMPANYID() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__COMPANY_ID).getId());
	}


	/**
	 * 기기 ID(UDID값. 디바이스에 대한 유일한 값.)
	 */
	public void setDEVICEID(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__DEVICE_ID).getId(), value);
	}


	/**
	 * 기기 ID(UDID값. 디바이스에 대한 유일한 값.)
	 */
	public String getDEVICEID() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__DEVICE_ID).getId());
	}


	/**
	 * 등록할 연계키
	 */
	public void setRELATIONKEY(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__RELATION_KEY).getId(), value);
	}


	/**
	 * 등록할 연계키
	 */
	public String getRELATIONKEY() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__RELATION_KEY).getId());
	}


	/**
	 * 재전송여부(Y or N)
	 */
	public void setRETRANSYN(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__RETRANS_YN).getId(), value);
	}


	/**
	 * 재전송여부(Y or N)
	 */
	public String getRETRANSYN() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__RETRANS_YN).getId());
	}


	/**
	 * 비고
	 */
	public void setREMARK(String value) throws JSONException, Exception {
		mSendMessage.put(mLayout.getField(IDX__REMARK).getId(), value);
	}


	/**
	 * 비고
	 */
	public String getREMARK() throws Exception {
		return (String) mSendMessage.get(mLayout.getField(IDX__REMARK).getId());
	}


}
