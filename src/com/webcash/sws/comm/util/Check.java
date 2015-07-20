package com.webcash.sws.comm.util;

import android.app.Activity;

import com.webcash.sws.comm.pref.LibConf;
import com.webcash.sws.comm.ui.DlgAlert;

public class Check {
	public static enum NullYn {
		Yes,
		No
	}
	
	public static enum Oper {
		Nothing,
		LessThan,
		GreaterThan,
		Equal,
		LessThanEqual,
		GreaterThanEqual
	}

	public static enum MsgYn {
		Show,
		Hide
	}
	
	public static enum MsgType {
		Insert,
		Select
	}
	
	public static enum JOSA {
		YL,
		YN
	}
	
	public static boolean hasFinalConsonant(int ch){
		return (ch-16)%28!=0;
	}
	
	public static boolean hasFinalConsonant(char ch){
		return hasFinalConsonant((int)ch);
	}
	
	
	public static String getJOSA(String str, JOSA cls){
		String josa;
		if(str.trim().equals("")) return str;
		if(str == null) return "";
		
		if(cls.equals(JOSA.YL)) {
			josa = hasFinalConsonant(str.trim().charAt(str.length()-1)) ? "을" : "를";
		} else {
			josa = hasFinalConsonant(str.trim().charAt(str.length()-1)) ? "은" : "는";
		}
		return josa;
	}
	
	public static String msgCombine(String str, String...vargs){
		String msg = str;
		
		for(String var : vargs){
			msg = msg.replaceFirst(LibConf.REGULAR_EXPR, var);
		}
		return msg;
	}
		
	public static boolean string(Activity atvt, String val, int minLen, int maxLen, String minErrMsg, String maxErrMsg) throws BizException, Exception {
		return string(atvt, val, minLen, maxLen, minErrMsg, maxErrMsg,true);
	}
	
	public static boolean string(Activity atvt, String val, int minLen, int maxLen, String minErrMsg, String maxErrMsg, boolean throwException) throws BizException, Exception {
		int valLen = 0;
		String errMsg = null;

		if(val == null)
			throw new BizException(minErrMsg);
		
		valLen = val.trim().length();
		
		if(valLen < minLen)
			errMsg = minErrMsg;
		else if(valLen > maxLen)
			errMsg = maxErrMsg;
		else return true;
		
		if(errMsg == null)
			errMsg = "오류";
		
		if(throwException)
			throw new BizException(errMsg);
		
		DlgAlert.Error(atvt, errMsg);

		return false;
	}
	
	public static boolean nullCheck(Activity atvt, String val, int minLen, String minErrMsg) throws BizException, Exception {
		return nullCheck(atvt, val, minLen, minErrMsg,true);
	}
	
	public static boolean nullCheck(Activity atvt, String val, int minLen, String minErrMsg, boolean throwException) throws BizException, Exception {
		int valLen = 0;
		String errMsg = null;

		if(val == null)
			throw new BizException(minErrMsg);
		
		valLen = val.trim().length();
		
		if(valLen == minLen)
			errMsg = minErrMsg;
		else return true;
		
		if(errMsg == null)
			errMsg = "오류";
		
		if(throwException)
			throw new BizException(errMsg);
		
		DlgAlert.Error(atvt, errMsg);

		return false;
	}
	
	/**
	 * 입력금액 유효성을 검사한다.
	 * @param value
	 * @param errMsg
	 * @param vargs
	 * @return
	 * @throws BizException
	 */
	public static boolean amount(Activity atvt, String val, long minVal, long maxVal, String minErrMsg, String maxErrMsg) throws BizException, Exception {
		return amount(atvt, val, minVal, maxVal, minErrMsg, maxErrMsg,true);
	}
	
	public static boolean amount(Activity atvt, String val, long minVal, long maxVal, String minErrMsg, String maxErrMsg, boolean throwException) throws BizException, Exception {
		long longVal;
		if(null == val || val.equals(""))
			longVal = 0;
		else
			longVal = Long.parseLong(val);
		
		String errMsg = null;
		
		if(longVal < minVal)
			errMsg = minErrMsg;
		else if(longVal > maxVal)
			errMsg = maxErrMsg;
		else return true;
		
		if(errMsg == null)
			errMsg = "오류";
		
		if(throwException)
			throw new BizException(errMsg);
		
		DlgAlert.Error(atvt, errMsg);

		return false;
	}
	
	/**
	 * 입력날짜 유효성을 검사한다.
	 * @param value
	 * @param errMsg
	 * @param vargs
	 * @return
	 * @throws BizException
	 */
	public static boolean isDate(Activity atvt, String val, String nullErrMsg, String invErrMsg, boolean throwException) throws BizException, Exception {
		String errMsg = null;
		
		if(val == null){
			errMsg = nullErrMsg;
		} else if(val.length() < 1 || val.length() > 8 ){
			errMsg = invErrMsg;
		} else return true;

		if(errMsg == null)
			errMsg = "오류";
		
		if(throwException)
			throw new BizException(errMsg);
		
		DlgAlert.Error(atvt, errMsg);

		return false;
	}
	
	public static boolean date(Activity atvt, String val, long minVal, long maxVal, String minErrMsg, String maxErrMsg, boolean throwException) throws BizException, Exception {
		long intVal = 0;
		intVal = Integer.parseInt(val);
		String errMsg = null;
		
		if(intVal < minVal)
			errMsg = minErrMsg;
		else if(intVal <= maxVal)
			errMsg = maxErrMsg;
		else return true;
		
		if(errMsg == null)
			errMsg = "오류";
		
		if(throwException)
			throw new BizException(errMsg);
		
		DlgAlert.Error(atvt, errMsg);

		return false;
	}
	
	/**
	 * 문자코드유효성을 검사한다.
	 * @param value
	 * @param errMsg
	 * @param vargs
	 * @return
	 * @throws BizException
	 */
	public static boolean code(String value, String nullErrMsg, String invErrMsg, String... vargs) throws BizException {
		return code(value, false, 0, nullErrMsg, 0, invErrMsg, true, vargs);
	}
	public static boolean code(String value, boolean nullAllow, String nullErrMsg, String invErrMsg, String... vargs) throws BizException {
		return code(value, nullAllow, 0, nullErrMsg, 0, invErrMsg, true, vargs);
	}
	public static boolean code(String value, int nullErrCode, String nullErrMsg, int invErrCode, String invErrMsg, String... vargs) throws BizException {
		return code(value, false, nullErrCode, nullErrMsg, invErrCode, invErrMsg, true, vargs);
	}
	public static boolean code(String value, boolean nullAllow, int nullErrCode, String nullErrMsg, int invErrCode, String invErrMsg, String... vargs) throws BizException {
		return code(value, nullAllow, nullErrCode, nullErrMsg, invErrCode, invErrMsg, true, vargs);
	}
	public static boolean code(String value, boolean nullAllow, int nullErrCode, String nullErrMsg, int invErrCode, String invErrMsg, boolean throwBizException, String... vargs) throws BizException {
		if((value == null || value == "") && nullAllow) {
			throw new BizException(nullErrCode, nullErrMsg);
		}
		
		for (String var : vargs) {
			if(value.equals(var)) return true;
		}
		
		if(throwBizException)
			throw new BizException(invErrCode, invErrMsg);
		
		return false;
	}
	
	/**
	 * 숫자코드유효성을 검사한다.
	 * @param value
	 * @param errMsg
	 * @param vargs
	 * @return
	 * @throws BizException
	 */
	public static boolean code(int value, String errMsg, int... vargs) throws BizException {
		for (int var : vargs) {
			if(value == var) return true;
		}
		throw new BizException(errMsg);
	}
	
	/**
	 * 지출방법코드를 검사한다.
	 * @param value
	 * @param errMsg
	 * @param vargs
	 * @return
	 * @throws BizException
	 */
	public static boolean code(int value, String errMsg, int varg) throws BizException {
			if(value != varg) return true;
		throw new BizException(errMsg);
	}
	
	/**
	 * 숫자가 0인지 검사
	 * @param value
	 * @param errMsg
	 * @return
	 * @throws BizException
	 */
	public static boolean isZero(String value, String errMsg) throws BizException {
		if(value == null) throw new BizException(errMsg);
		if(Integer.parseInt(value) == 0) throw new BizException(errMsg);
		return true;
	}
	
	/**
	 * 문자열 길이 및 필수입력여부를 체크한다.
	 * @param val
	 * @param len
	 * @param oper
	 * @param nl
	 * @param atvt
	 * @param msgyn
	 * @param fldnm
	 * @return true-정상, false-오류
	 */
	public static boolean checkValLen(String val, Integer len, Oper oper, NullYn nl, Activity atvt, MsgYn msgyn, String fldnm, MsgType msgtype) {
		Integer vlen = val.trim().length();
		String msg = null;
		
		if(nl.equals(NullYn.No) && vlen == 0) {
			msg = msgtype.equals(MsgType.Insert) ? "&& 입력하세요." : "&& 선택하세요.";
			msg = msgCombine(msg, fldnm, getJOSA(fldnm, JOSA.YL));
		} else if(oper.equals(Oper.LessThanEqual) && len < vlen) {
			msg = msgCombine("자리를 초과할 수 없습니다.", fldnm, getJOSA(fldnm, JOSA.YN), len.toString());
		} else {
			return true;
		}

		if(msgyn.equals(MsgYn.Show)) {
			DlgAlert.Error(atvt, "오류");
		}

		return false;
	}
	
	public static boolean checkValNN(String val, Activity atvt, String fldnm) {
		return checkValLen(val, 0, Oper.Nothing, NullYn.No, atvt, MsgYn.Show, fldnm, MsgType.Insert);
	}
	public static boolean checkValNN(String val, Activity atvt, String fldnm, MsgType msgtype) {
		return checkValLen(val, 0, Oper.Nothing, NullYn.No, atvt, MsgYn.Show, fldnm, msgtype);
	}
	
	/**
	 * 
	 * @param val
	 * @param BizMsg
	 * @return
	 * @throws BizException
	 */
	public static boolean isNumber(String val, String formatErrMsg) throws BizException, Exception{
		if(formatErrMsg == null) formatErrMsg = "오류";
		
		if(null == val) return false;
		
		if(val.length() > 12) throw new BizException(0, formatErrMsg); 
				
		for(int idx = 0; idx < val.length(); idx++) {
			if(Character.isDigit(val.charAt(idx))) continue;
			else throw new BizException(0, formatErrMsg);
		}
		return true;
	}

}
