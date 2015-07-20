package com.webcash.sws.comm.extras.biz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.webcash.sws.comm.extras.Extras;

public class Extra_Mbr extends Extras {
	private static final String USER_STAT			= "USER_STAT";			// 사용자 상태
	private static final String PASSED_CERT_DATA	= "PASSED_CERT_DATA";	// 선택된 인증서
	private static final String COUNT_CERT_DATA		= "COUNT_CERT_DATA";	// 인증서 개수
	private static final String URL_DATA			= "URL_DATA";			// URL
	private static final String USER_PHONE_NO		= "USER_PHONE_NO";		// 본인전화번호
	private static final String SMS_KEY				= "SMS_KEY";			// SMS순번
	private static final String USERNAME			= "USERNAME";			// 사용자성명
	
	private static final String TEXTTITLE			= "TEXTTITLE";			// 내용타이틀
	private static final String AGREEMENTTEXT		= "AGREEMENTTEXT";		// 약관동의내용
	private static final String AGREEMENTURL		= "AGREEMENTURL";		// 약관동의URL
	private static final String INFOTEXT			= "INFOTEXT";			// 개인정보수집내용
	private static final String INFOURL				= "INFOURL";			// 개인정보수집URL
	private static final String LBSTEXT				= "LBSTEXT";			// 위치기반서비스이용약관
	private static final String SHOWTEXT			= "SHOWTEXT";			// 보여주는약관Text
	
	private static final String DUPUSER_YN			= "DUPUSER_YN";			// 사용자 계정 중복등록(같은 번호, 다른 이름)
	private static final String DANGOL_UPDATE		= "DANGOL_UPDATE";	// 내단골 정보 업데이트
	
	public _Param Param = new _Param();

	public Extra_Mbr(Activity atvt) {
		super(atvt);
	}

	public Extra_Mbr(Activity atvt, Intent intent) {
		super(atvt, intent);
	}

	public Extra_Mbr(Activity atvt, Bundle bundle) {
		super(atvt, bundle);
	}

	public class _Param {		
//		/**
//		 * 사용자 상태
//		 * @param value
//		 */
//		public void setUserStat(String value) {
//			setString(USER_STAT, value);
//		}
//
//		/**
//		 * 선택된 인증서
//		 * @return 
//		 */
//		public String getUserStat() {
//			return getString(USER_STAT);
//		}
		
		/**
		 * 선택된 인증서
		 * @param value
		 */
		public void setPassedCertData(int value) {
			setInt(PASSED_CERT_DATA, value);
		}

		/**
		 * 선택된 인증서
		 * @return 
		 */
		public int getPassedCertData() {
			return getInt(PASSED_CERT_DATA);
		}
		
		/**
		 * 인증서 개수
		 * @param value
		 */
		public void setCountCertData(int value) {
			setInt(COUNT_CERT_DATA, value);
		}

		/**
		 * 인증서 개수
		 * @return 
		 */
		public int getCountCertData() {
			return getInt(COUNT_CERT_DATA);
		}
		
		/**
		 * URL
		 * @param value
		 */
		public void setURLData(String value) {
			setString(URL_DATA, value);
		}

		/**
		 * URL
		 * @return 
		 */
		public String getURLData() {
			return getString(URL_DATA);
		}
		
		/**
		 * 본인전화번호
		 * @param value
		 */
		public void setUserPhoneNo(String value) {
			setString(USER_PHONE_NO, value);
		}

		/**
		 * 본인전화번호
		 * @return 
		 */
		public String getUserPhoneNo() {
			return getString(USER_PHONE_NO);
		}
		
		/**
		 * 본인전화번호
		 * @param value
		 */
		public void setSmsKey(String value) {
			setString(SMS_KEY, value);
		}

		/**
		 * 본인전화번호
		 * @return 
		 */
		public String getSmsKey() {
			return getString(SMS_KEY);
		}
		
		/**
		 * 사용자성명
		 * @param value
		 */
		public void setUserName(String value) {
			setString(USERNAME, value);
		}

		/**
		 * 사용자성명
		 * @return 
		 */
		public String getUserName() {
			return getString(USERNAME);
		}

		/**
		 * 내용타이틀
		 * @param value
		 */
		public void setTextTitle(String value) {
			setString(TEXTTITLE, value);
		}

		/**
		 * 내용타이틀
		 * @return 
		 */
		public String getTextTitle() {
			return getString(TEXTTITLE);
		}		
		
		/**
		 * 약관동의내용
		 * @param value
		 */
		public void setAgreementText(String value) {
			setString(AGREEMENTTEXT, value);
		}

		/**
		 * 약관동의내용
		 * @return 
		 */
		public String getAgreementText() {
			return getString(AGREEMENTTEXT);
		}

		/**
		 * 약관동의URL
		 * @param value
		 */
		public void setAgreementUrl(String value) {
			setString(AGREEMENTURL, value);
		}

		/**
		 * 약관동의URL
		 * @return 
		 */
		public String getAgreementUrl() {
			return getString(AGREEMENTURL);
		}

		/**
		 * 개인정보수집내용
		 * @param value
		 */
		public void setInfoText(String value) {
			setString(INFOTEXT, value);
		}

		/**
		 * 개인정보수집내용
		 * @return 
		 */
		public String getInfoText() {
			return getString(INFOTEXT);
		}

		/**
		 * 개인정보수집URL
		 * @param value
		 */
		public void setInfoUrl(String value) {
			setString(INFOURL, value);
		}

		/**
		 * 개인정보수집URL
		 * @return 
		 */
		public String getInfoUrl() {
			return getString(INFOURL);
		}
		
		/**
		 * 위치기반서비스이용약관
		 * @param value
		 */
		public void setLBSTEXT(String value) {
			setString(LBSTEXT, value);
		}
		
		/**
		 * 위치기반서비스이용약관
		 * @return 
		 */
		public String getLBSTEXT() {
			return getString(LBSTEXT);
		}
		
		/**
		 * 보여주는약관Text
		 * @param value
		 */
		public void setSHOWTEXT(String value) {
			setString(SHOWTEXT, value);
		}
		
		/**
		 * 보여주는약관Text
		 * @return 
		 */
		public String getSHOWTEXT() {
			return getString(SHOWTEXT);
		}
		
		public void setDupuserYN(String value)
		{
			setString(DUPUSER_YN, value);
		}
		
		public String getDupuserYN()
		{
			return getString(DUPUSER_YN);
		}
		
		
		/**
		 *  내 단골 업데이트 여부
		 * @param isUpdate
		 */
		public void setDangolUpdate(boolean isUpdate) {
			
			setBoolean(DANGOL_UPDATE, isUpdate);
		}
		
		public boolean isDangolUpdate() {
			
			return getBoolean(DANGOL_UPDATE);
		}
		
		
	}
}
