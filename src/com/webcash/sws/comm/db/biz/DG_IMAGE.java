package com.webcash.sws.comm.db.biz;

import android.app.Activity;

import com.webcash.sws.comm.db.DbTable;

public class DG_IMAGE extends DbTable{

	public static final String TABLE_NAME = "DG_IMAGE";	
	
	public static enum GUBUN {MESSAGE, PROFILE};	// 메세지 이미지, 프로필 이미지
	
	/**
	 * 이미지정보 테이블 컬럼
	 */
	public static class ColumnNames{
		public final static String GUBUN			= "GUBUN";		 
		public final static String NOTI_NO			= "NOTI_NO";		
		public final static String IMG_URL 			= "IMG_URL";
		public final static String IMG_PATH			= "IMG_PATH";			 
	}

	public DG_IMAGE(Activity activity) {
		super(activity);
	}
	
	public static String getGUBUN(GUBUN val) {	
		if (val == GUBUN.PROFILE)
			return "P";
		else 
			return "M";
	}
	
	public void setGUBUN(GUBUN val) throws Exception {
		if (val == GUBUN.MESSAGE)
			mContents.put(ColumnNames.GUBUN, "M");
		else if (val == GUBUN.PROFILE)
			mContents.put(ColumnNames.GUBUN, "P");
	}

	public void setNOTI_NO(String val) throws Exception {
		mContents.put(ColumnNames.NOTI_NO, val);
	}
	
	public void setIMG_URL(String val) throws Exception {
		mContents.put(ColumnNames.IMG_URL, val);
	}
	
	public void setIMG_PATH(String val) throws Exception {
		mContents.put(ColumnNames.IMG_PATH, val);
	}
	
	public String getGUBUN() throws Exception {
		return getString(ColumnNames.GUBUN);
	}

	public String getNOTI_NO() throws Exception {
		return getString(ColumnNames.NOTI_NO);
	}
	
	public String getIMG_URL() throws Exception {
		return getString(ColumnNames.IMG_URL);
	}
	
	public String getIMG_PATH() throws Exception {
		return getString(ColumnNames.IMG_PATH);
	}
	
	
	public int selectImage(String notiNo, GUBUN gubun) throws Exception {		
		String sql = "SELECT * FROM " + TABLE_NAME +" WHERE GUBUN = ? AND NOTI_NO = ? ";
		
		sqlQuery(sql, new String[]{getGUBUN(gubun), notiNo});
		return SQL_CODE.SQL_OK;
	}
	
	public String selectImagePath(String targetNo, String imgUrl, GUBUN gubun) throws Exception {		
		String sql = "SELECT * FROM " + TABLE_NAME +" WHERE GUBUN = ? AND NOTI_NO = ? AND IMG_URL = ? ";		
		sqlQuery(sql, new String[]{getGUBUN(gubun), targetNo, imgUrl});
		if (getCount() == 0)
			return null;
		else
			return getIMG_PATH();		
	}
	
	
	public String selectImageURL(String targetNo, GUBUN gubun) throws Exception {		
		String sql = "SELECT * FROM " + TABLE_NAME +" WHERE GUBUN = ? AND NOTI_NO = ? ";		
		sqlQuery(sql, new String[]{getGUBUN(gubun), targetNo});
		if (getCount() == 0)
			return null;
		else
			return getIMG_URL();		
	}
	
	
	/**
	 * DG_IMAGE 테이블 삽입  
	 */
	public long insert() throws Exception {
		sqlInsert(TABLE_NAME);
		return SQL_CODE.SQL_OK;
	}
	
	/**
	 * DG_IMAGE 테이블 삭제
	 * @param where - 업데이트 WHERE 절
	 * @param args - WHERE 조건값
	 */
	public long delete(String where, String[] args) throws Exception {
		return sqlDelete(TABLE_NAME, where, args);
		//return SQL_CODE.SQL_OK;
	}
}
