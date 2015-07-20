package com.webcash.sws.comm.define;

public class Msg {
	public class Err {
		public class SdCard {
			public static final String SDCARD_NOT_EXIST = "SD카드 장착유무를 확인해주세요";			
			public static final String FILE_NOT_EXIST = "파일이 존재하지않습니다.";			
			public static final String DIRECTORY_NOT_EXIST = "경로가 올바르지 않습니다.";			
		}

		public class TxMessage {
			public static final String TXNO_INVALID = "유효하지 않은 전문번호 입니다.";
		}
		
		public class Sql {
			public static final String Default 					= "DB처리중 오류가 발생하였습니다";
			public static final String PRIMARY_KEY_NOT_FOUND 	= " 필수 입력값입니다.";
			public static final String TYPE_NOT_VALID			= "형식이 유효하지 않습니다.";
			public static final String NUM_TYPE_NOT_VALID 		= "입력 숫자가 유효하지 않습니다.";
		}
	}

	public class Exp {
		public static final String DEFAULT = "오류가 발생하였습니다";
	}

	public class Alt {
		public static final String IS_EXIST = "종료하시겠습니까?";
		public static final String IS_LOGOUT = "로그아웃하시겠습니까?";
		public static final String BIZPLAY_IS_LOGOUT = "";
		public static final String NETWORK_NOT_AVAILABLE = "인터넷 연결이 불안정합니다. 연결 후 이용하시기 바랍니다";
	}

	public class Ntc {

		public static final String AUTO_LOGOUT = "일정시간 동안 사용자로 부터의 요청이 없어 연결을 종료합니다.";
		public static final String UPDATE_NOTICE = "현재 버전에서는 해당 메뉴를 이용할 수 없습니다. 최신버전으로 업데이트 하시기 바랍니다.";
		public static final String NOT_YET = "준비 중 입니다.";

		public static final String NOT_AVAILABLE ="죄송합니다.\n고객님은 본 서비스를 이용하실수 없습니다.";
		
		public static final String ROOTING_NOTICE =  "루팅된 안드로이드폰에서는 서비스를 이용할 수 없습니다.";
		public static final String CONTACT_LIST_LOADING = "연락처 목록을 가져오는 중입니다.";
		
	}

	public static class BtnTitle {
		public static final String BTN_YES				= "예";
		public static final String BTN_NO				= "아니요";
		public static final String BTN_OK 				= "확인";
		public static final String BTN_CANCEL 			= "취소";
		public static final String BTN_SKIP				= "다음에";
		public static final String REPEAT_ALL_BTN 		= "전체 삭제";
		public static final String REPEAT_ONE_BTN 		= "현재 건 삭제";

	}
	
	public class MESSAGE {
		public static final String DELALL_YN			= "메시지를 전부 삭제할까요?";
		public static final String PHOTO_MAXTWO			= "사진은 2장만 첨부 가능합니다.";		
	}
}
