package com.webcash.sws.comm.tx.push;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;

import com.webcash.sws.comm.tx.TxField;
import com.webcash.sws.comm.tx.TxMessage;
import com.webcash.sws.comm.tx.TxRecord;

public class TX_PUSH_COMM_RES extends TxMessage {
	private static int IDX_RESULT;                // 전송결과
	private static int IDX_ERROR_MSG;             // 에러메세지
	private static int IDX_ERROR_CD;              // 에러코드

	
	public TX_PUSH_COMM_RES(Context ctx, Object obj, String txNo) throws Exception {
		mTxNo = txNo;
		mLayout = new TxRecord();
	
		IDX_RESULT = mLayout.addField(new TxField("_result","전송결과"));
		IDX_ERROR_MSG = mLayout.addField(new TxField("_error_msg","에러메세지"));
		IDX_ERROR_CD = mLayout.addField(new TxField("_error_cd","에러코드"));
	
		super.initRecvMessage(ctx, obj, txNo);
	}


	/**
	 * 전송ID
	 */
	public String getResult() throws JSONException, Exception {
		return getString(mLayout.getField(IDX_RESULT).getId());
	}


}
