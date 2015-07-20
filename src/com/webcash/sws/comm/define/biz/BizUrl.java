package com.webcash.sws.comm.define.biz;


public class BizUrl {
	
	public static class App {
		public static final String CERT_ERR_URL = "com.webcash.sws.ui.Main_Menu";	
	}
	
	public static class Web {
		public static String TIKER_URL			= "/smart/gateway/tikersend.jsp"; 		// 티커바 주소
		public static String GRID_EVENT_URL		= "/smart/gateway/eventsend.jsp"; 		// 그리드 이벤트 주소
		public static String COVER_EVENT_URL	= "/smart/gateway/eventsendc.jsp"; 		// 커버 이벤트 주소
		public static String NEWUSER_URL 		= "/smart/cus/smtCus001_01.jsp";		// 신규회원가입URL
	}
}
