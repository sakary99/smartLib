package com.webcash.sws.comm.db.biz;

import android.app.Activity;

import com.webcash.sws.comm.db.DbTable;

public class DG_PLACETELNO extends DbTable{
	public static final String TABLE_NAME = "DG_PLACETELNO";
	
	
	/**
	 * 내단골 연락처 테이블 컬럼
	 */
	public static class ColumnNames{
		public final static String PLACENO			= "PLACENO";		 
		public final static String PLACETELNO_NO	= "PLACETELNO_NO";		
		public final static String TELNO 			= "TELNO";
		public final static String TELNONAME 		= "TELNONAME";
	}

	public DG_PLACETELNO(Activity activity) {
		super(activity);
	}

	/**
	 * DG_PLACETELNO 테이블 삽입  
	 * @return 입력 결과 코드
	 * @throws Exception
	 */
	public long insert() throws Exception {
		sqlInsert(TABLE_NAME);
		return SQL_CODE.SQL_OK;
	}

	/**
	 * DG_PLACETELNO 테이블 수정
	 * @param where - 업데이트 WHERE 절
	 * @param args - WHERE 조건값
	 * @return 업데이트 결과 코드
	 * @throws Exception
	 */
	public long update(String where, String[] args) throws Exception {
		sqlUpdate(TABLE_NAME, where, args);
		return SQL_CODE.SQL_OK;
	}

	/**
	 * DG_PLACETELNO 테이블 삭제
	 * @param where - 업데이트 WHERE 절
	 * @param args - WHERE 조건값
	 * @return 삭제 결과 코드
	 * @throws Exception
	 */
	public long delete(String where, String[] args) throws Exception {
		sqlDelete(TABLE_NAME, where, args);
		return SQL_CODE.SQL_OK;
	}

	public void setPLACENO(String val) throws Exception {
		mContents.put(ColumnNames.PLACENO, val);
	}

	public void setPLACETELNO_NO(String val) throws Exception {
		mContents.put(ColumnNames.PLACETELNO_NO, val);
	}
	
	public void setTELNO(String val) throws Exception {
		mContents.put(ColumnNames.TELNO, val);
	}
	
	public void setTELNONAME(String val) throws Exception {
		mContents.put(ColumnNames.TELNONAME, val);
	}
}