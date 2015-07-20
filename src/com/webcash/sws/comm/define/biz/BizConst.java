package com.webcash.sws.comm.define.biz;

/**
 * startActivityForResult로 값을 받을때 
 * Request_Code에 대해서 정리와 정의 입니다. 
 * @author hjcom
 *
 */
public class BizConst {
	
	public static enum LoginType {ID, CERT};
	
	public static class PushCode{
		public static final String PUSHKEY = "CodeNum";		// Push Key
		public static final String PUSHCODE_0001= "0001";	// 메세지 Push
		public static final String PUSHCODE_0002= "0002";	// 강제업데이트 Push
		public static final String PUSHCODE_0003= "0003";	// 공유요청 Push
		public static final String PUSHCODE_0004= "0004";	// 공유승인 Push
		public static final String PUSHCODE_0005= "0005";	// 스토리단골 지정공개 Push
	}
	
	
	//중첩되는 activity 생성을 막기 위해 
	public static final String	ACTIVITY_PREVIOUS = "ACTIVITY_PREVIOUS"; // 이전 액티비티
	public static final String	REMIT_COMMIT = "REMIT_COMMIN"; // 이체완료여부
	
}
