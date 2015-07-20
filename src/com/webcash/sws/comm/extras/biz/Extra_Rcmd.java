package com.webcash.sws.comm.extras.biz;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.webcash.sws.comm.extras.Extras;

public class Extra_Rcmd extends Extras {
	
	private static final String PHONE_LIST			= "PHONE_LIST";				// 주소록 선택한 번호 리스트
	
	private static final String BUSINESS_TYPE		= "BUSINESS_TYPE";			
	private static final String PLACE_NO			= "PLACE_NO";	
	private static final String TEL_NO			    = "TEL_NO";	
	private static final String TARGET_NAME			= "TARGET_NAME";
	
	
	public _Param Param = new _Param();

	public Extra_Rcmd(Activity atvt) {
		super(atvt);
	}

	public Extra_Rcmd(Activity atvt, Intent intent) {
		super(atvt, intent);
	}

	public Extra_Rcmd(Activity atvt, Bundle bundle) {
		super(atvt, bundle);
	}

	public class _Param {	

		/**
		 * BUSINESS_TYPE
		 * @param value
		 */
		public void setBusinessType(String value) {
			setString(BUSINESS_TYPE, value);
		}

		/**
		 * BUSINESS_TYPE
		 * @return 
		 */
		public String getBusinessType() {
			return getString(BUSINESS_TYPE);
		}	
		
		/**
		 * PLACE_NO
		 * @param value
		 */
		public void setPlaceNo(String value) {
			setString(PLACE_NO, value);
		}

		/**
		 * PLACE_NO
		 * @return 
		 */
		public String getPlaceNo() {
			return getString(PLACE_NO);
		}	
		
		/**
		 * TEL_NO
		 * @param value
		 */
		public void setTelNo(String value) {
			setString(TEL_NO, value);
		}

		/**
		 * TEL_NO
		 * @return 
		 */
		public String getTelNo() {
			return getString(TEL_NO);
		}	
		
		
		/**
		 * TARGET_NAME
		 * @param value
		 */
		public void setTargetName(String value) {
			setString(TARGET_NAME, value);
		}

		/**
		 * TARGET_NAME
		 * @return 
		 */
		public String getTargetName() {
			return getString(TARGET_NAME);
		}	
		
		
		/**
		 * 전화 번호 리스트
		 * @param value
		 */
		public void setPhoneList(ArrayList<String> value) {
			setList(PHONE_LIST, value);
		}

		/**
		 * 전화 번호 리스트
		 * @return 
		 */
		public  ArrayList<String> getPhoneList() {
			return getList(PHONE_LIST);
		}	
		
		

	}
}
