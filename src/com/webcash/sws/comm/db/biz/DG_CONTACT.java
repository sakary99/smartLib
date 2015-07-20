package com.webcash.sws.comm.db.biz;

import android.app.Activity;

import com.webcash.sws.comm.db.DbTable;
import com.webcash.sws.comm.db.DbTable.SQL_CODE;
import com.webcash.sws.comm.db.biz.DG_BUSINESSTYPE.ColumnNames;

public class DG_CONTACT extends DbTable{
	public static final String TABLE_NAME = "DG_CONTACT";
	
	
	/**
	 * 연락처동기화 테이블 컬럼
	 */
	public static class ColumnNames{
		public final static String USERNAME			= "USERNAME";		 
		public final static String TELNO			= "TELNO";		
		public final static String PHOTOUUID 		= "PHOTOUUID";
		public final static String PHOTOFILECONTENT = "PHOTOFILECONTENT";
		public final static String INDATE 			= "INDATE";
		public final static String INTIME 			= "INTIME";
	}

	public DG_CONTACT(Activity activity) {
		super(activity);
	}
	
	public boolean selectDangolYN(String telNo) throws Exception {
		String sql = "SELECT TELNO FROM DG_CONTACT WHERE TELNO = '" + telNo + "'";
		sqlQuery(sql, null);
		if( mCursor.getCount() > 0 ){
			closeCursor();
			return true;
		}else{ 
			closeCursor();
			return false;
		}
	}
	
	public int getRecordCount() throws Exception {
		sqlQuery("SELECT COUNT(*) AS CNT FROM " + TABLE_NAME, null);
		return mCursor.getInt( mCursor.getColumnIndex("CNT") );
	}

	/**
	 * DG_CONTACT 테이블 삽입  
	 * @return 입력 결과 코드
	 * @throws Exception
	 */
	public long insert() throws Exception {
		sqlInsert(TABLE_NAME);
		return SQL_CODE.SQL_OK;
	}

	/**
	 * DG_CONTACT 테이블 수정
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
	 * DG_CONTACT 테이블 삭제
	 * @param where - 업데이트 WHERE 절
	 * @param args - WHERE 조건값
	 * @return 삭제 결과 코드
	 * @throws Exception
	 */
	public long delete(String where, String[] args) throws Exception {
		sqlDelete(TABLE_NAME, where, args);
		return SQL_CODE.SQL_OK;
	}

	public void setUSERNAME(String val) throws Exception {
		mContents.put(ColumnNames.USERNAME, val);
	}

	public void setTELNO(String val) throws Exception {
		mContents.put(ColumnNames.TELNO, val);
	}
	
	public void setPHOTOUUID(String val) throws Exception {
		mContents.put(ColumnNames.PHOTOUUID, val);
	}
	
	public void setPHOTOFILECONTENT(String val) throws Exception {
		mContents.put(ColumnNames.PHOTOFILECONTENT, val);
	}
	
	public void setINDATE(String val) throws Exception {
		mContents.put(ColumnNames.INDATE, val);
	}
	
	public void setINTIME(String val) throws Exception {
		mContents.put(ColumnNames.INTIME, val);
	}
	
}