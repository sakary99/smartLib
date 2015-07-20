package com.webcash.sws.comm.db.biz;

import android.app.Activity;
import android.os.Environment;

import com.webcash.sws.comm.db.DbTable;
import com.webcash.sws.comm.db.biz.DG_BUSINESSTYPE.ColumnNames;

public class DG_BUSINESSTYPEDETAIL extends DbTable{
	public static final String TABLE_NAME = "DG_BUSINESSTYPEDETAIL";
	
	
	/**
	 * 업종상세 테이블 컬럼
	 */
	public static class ColumnNames{
		public final static String BUSINESSTYPENO			= "BUSINESSTYPENO";		 
		public final static String BUSINESSTYPEDETAILNO		= "BUSINESSTYPEDETAILNO";		
		public final static String BUSINESSTYPEDETAILNAME 	= "BUSINESSTYPEDETAILNAME";
		public final static String DISPLAYORDER 			= "DISPLAYORDER";
	}

	public DG_BUSINESSTYPEDETAIL(Activity activity) {
		super(activity);
	}
	
	public long select(String bizTypeNo) throws Exception {
		return sqlQuery(" SELECT * FROM " + TABLE_NAME    + System.getProperty("line.separator") 
				+ " WHERE BUSINESSTYPENO = '" + bizTypeNo + "'" + System.getProperty("line.separator") 
				+ " ORDER BY DISPLAYORDER ASC",  null);
	}

	/**
	 * DG_BUSINESSTYPEDETAIL 테이블 삽입  
	 * @return 입력 결과 코드
	 * @throws Exception
	 */
	public long insert() throws Exception {
		sqlInsert(TABLE_NAME);
		return SQL_CODE.SQL_OK;
	}

	/**
	 * DG_BUSINESSTYPEDETAIL 테이블 수정
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
	 * DG_BUSINESSTYPEDETAIL 테이블 삭제
	 * @param where - 업데이트 WHERE 절
	 * @param args - WHERE 조건값
	 * @return 삭제 결과 코드
	 * @throws Exception
	 */
	public long delete(String where, String[] args) throws Exception {
		sqlDelete(TABLE_NAME, where, args);
		return SQL_CODE.SQL_OK;
	}

	public void setBUSINESSTYPENO(String val) throws Exception {
		mContents.put(ColumnNames.BUSINESSTYPENO, val);
	}
	
	public void setBUSINESSTYPEDETAILNO(String val) throws Exception {
		mContents.put(ColumnNames.BUSINESSTYPEDETAILNO, val);
	}
	
	public void setBUSINESSTYPEDETAILNAME(String val) throws Exception {
		mContents.put(ColumnNames.BUSINESSTYPEDETAILNAME, val);
	}
	
	public void setDISPLAYORDER(String val) throws Exception {
		mContents.put(ColumnNames.DISPLAYORDER, val);
	}
	
	
	
	public String getBUSINESSTYPENO() throws Exception {
		return getString(ColumnNames.BUSINESSTYPENO);
	}

	public String getBUSINESSTYPEDETAILNO() throws Exception {
		return getString(ColumnNames.BUSINESSTYPEDETAILNO);
	}
	
	public String getBUSINESSTYPEDETAILNAME() throws Exception {
		return getString(ColumnNames.BUSINESSTYPEDETAILNAME);
	}
	
	public String getDISPLAYORDER() throws Exception {
		return getString(ColumnNames.DISPLAYORDER);
	}
}