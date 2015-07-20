/**
 * <pre>
 * 환경설정 클래스
 * @COPYRIGHT (c) 2011 WebCash, Inc. All Right Reserved.
 *
 * @author       : WebCash
 * @Description  : 
 * @History      : 
 *
 * </pre>
 **/

package com.webcash.sws.comm.pref;

import com.webcash.sws.lib.BuildConfig;

/**
 * 환경설정클래스<BR>
 * < 운영파일(APK) 생성시 주의사항 ><BR>
 * 1.릴리즈여부(ISRELEASE)를  'true' 로 설정하여 생성하여야 합니다.<BR>
 *   - 환경설정파일 운영/테스트 여부(ISCONFIG)는 'true' 로 자동 변경됩니다.<BR>
 *   - 전문테스트 여부(ISTRANTEST)는 'false' 로 자동 변경됩니다.<BR>
 */
public class LibConf {
	
	public LibConf() {
		
	}
	
	/**
	 * 릴리즈여부
	 */
	public static boolean ISRELEASE	= !BuildConfig.DEBUG;
	
	@Deprecated
	public static void setRelease(boolean value) {
		ISRELEASE = value;
	}
	

	/**
	 * 로그태그
	 */
    public static final String LOGTAG	= "WEBCASH";

    
    /**
     * 대체문자
     */
    public static final String REGULAR_EXPR	= "&";	
    public static final String COLM_EXPR 	= ",";
    public static final String ROW_EXPR 	= ":";
    
//    
    public static final String DB_NAME	= "dangol.db";
    public static final String NEW_LINE = "\n\r";
    public static final String DATE_DELIMITER = ".";
    
    
    
}