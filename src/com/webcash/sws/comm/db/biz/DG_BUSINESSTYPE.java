package com.webcash.sws.comm.db.biz;

import android.app.Activity;

import com.webcash.sws.comm.db.DbTable;
import com.webcash.sws.comm.db.biz.DG_DANGOL.ColumnNames;

public class DG_BUSINESSTYPE extends DbTable{
	public static final String TABLE_NAME = "DG_BUSINESSTYPE";
	public static final String TABLE_NAME_DETAIL = "DG_BUSINESSTYPEDETAIL";
	
	
	/**
	 * 업종 테이블 컬럼
	 */
	public static class ColumnNames{
		public final static String BUSINESSTYPENO			= "BUSINESSTYPENO";		 
		public final static String BUSINESSTYPENAME			= "BUSINESSTYPENAME";		
		public final static String DISPLAYORDER 			= "DISPLAYORDER";
			 
		public final static String BUSINESSTYPEDETAILNO		= "BUSINESSTYPEDETAILNO";		
		public final static String BUSINESSTYPEDETAILNAME 	= "BUSINESSTYPEDETAILNAME";		
	}

	public DG_BUSINESSTYPE(Activity activity) {
		super(activity);
	}
	
	public int getRecordCount() throws Exception {
		sqlQuery("SELECT COUNT(*) AS CNT FROM " + TABLE_NAME + " ORDER BY DISPLAYORDER ASC", null);
		return mCursor.getInt( mCursor.getColumnIndex("CNT") );
	}
	
	public long select() throws Exception {
		return sqlQuery(" SELECT * FROM " + TABLE_NAME, null);
	}

	/**
	 * DG_BUSINESSTYPE 테이블 삽입  
	 * @return 입력 결과 코드
	 * @throws Exception
	 */
	public long insert() throws Exception {
		sqlInsert(TABLE_NAME);
		return SQL_CODE.SQL_OK;
	}
	
	/**
	 * DG_BUSINESSTYPEDETAIL 테이블 삽입  
	 * @return 입력 결과 코드
	 * @throws Exception
	 */
	public long insertDetail( )throws Exception {
		sqlInsert(TABLE_NAME_DETAIL);
		return SQL_CODE.SQL_OK;
	}

	/**
	 * DG_BUSINESSTYPE 테이블 수정
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
	 * DG_BUSINESSTYPE 테이블 삭제
	 * @param where - 업데이트 WHERE 절
	 * @param args - WHERE 조건값
	 * @return 삭제 결과 코드
	 * @throws Exception
	 */
	public long delete(String where, String[] args) throws Exception {
		sqlDelete(TABLE_NAME, where, args);
		return SQL_CODE.SQL_OK;
	}

	/**
	 * DG_BUSINESSTYPEDETAIL 테이블 삭제
	 */
	public long deleteDetail(String where, String[] args) throws Exception {
		sqlDelete(TABLE_NAME_DETAIL, where, args);
		return SQL_CODE.SQL_OK;
	}
	
	public void setBUSINESSTYPENO(String val) throws Exception {
		mContents.put(ColumnNames.BUSINESSTYPENO, val);
	}

	public void setBUSINESSTYPENAME(String val) throws Exception {
		mContents.put(ColumnNames.BUSINESSTYPENAME, val);
	}
	
	public void setDISPLAYORDER(String val) throws Exception {
		mContents.put(ColumnNames.DISPLAYORDER, val);
	}
	
	public void setBUSINESSTYPEDETAILNO(String val) throws Exception {
		mContents.put(ColumnNames.BUSINESSTYPEDETAILNO, val);
	}
	
	public void setBUSINESSTYPEDETAILNAME(String val) throws Exception {
		mContents.put(ColumnNames.BUSINESSTYPEDETAILNAME, val);
	}
	
	
	
	public String getBUSINESSTYPENO() throws Exception {
		return getString(ColumnNames.BUSINESSTYPENO);
	}

	public String getBUSINESSTYPENAME() throws Exception {
		return getString(ColumnNames.BUSINESSTYPENAME);
	}
	
	public String getDISPLAYORDER() throws Exception {
		return getString(ColumnNames.DISPLAYORDER);
	}
	
}