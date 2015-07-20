package com.webcash.sws.comm.tran;

public interface BizInterface {
	public void msgTrSend(String tranCd);
	public void msgTrRecv(String tranCd, Object obj);
	public void msgTrError(String tranCd, Object obj) throws Exception;
	public void msgTrNotFound(String tranCd, Object obj);
	public void msgTrCancel(String tranCd);
//	// TODO : 최은경
//	// 취소한 경우도 받아야 하는지 여부 확인요망
//	
//	// [2014-03-13 : dilky ] 전문 취소 인터페이스 생성
//	public interface BizInterfaceCancel {
//		public void msgTrCancel(String tranCd);
//	}
}


