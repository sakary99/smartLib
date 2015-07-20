package com.webcash.sws.comm.db.biz;

import android.app.Activity;
import android.content.ContentValues;

import com.webcash.sws.comm.db.DbTable;
import com.webcash.sws.comm.util.BizException;

public class DG_DANGOL extends DbTable{
	public static final String TABLE_NAME = "DG_DANGOL";
	public static final String TABLE_NAME_PLACETELNO = "DG_PLACETELNO";
	
	
	/**
	 * 내단골 테이블 컬럼
	 */
	public static class ColumnNames{
		public final static String PLACENO				= "PLACENO";		 
		public final static String PLACENAME			= "PLACENAME";		
		public final static String POSTNO 				= "POSTNO";
		public final static String SIDO 				= "SIDO";
		public final static String GUGUN 				= "GUGUN";
		public final static String ADDRESS1             = "ADDRESS1";
		public final static String ADDRESS2		   		= "ADDRESS2";
		public final static String PHOTOFILECONTENT	   	= "PHOTOFILECONTENT";
		public final static String BUSINESSTYPENAME	   	= "BUSINESSTYPENAME";
		public final static String BUSINESSTYPENO	   	= "BUSINESSTYPENO";
		public final static String BUSINESSTYPEDETAILNAME  = "BUSINESSTYPEDETAILNAME";
		public final static String BUSINESSTYPEDETAILNO	   = "BUSINESSTYPEDETAILNO";
		public final static String LATITUDE		   		= "LATITUDE";
		public final static String LONGITUDE		   	= "LONGITUDE";
		public final static String MEMO			   		= "MEMO";
		public final static String VISITORCOUNT		   	= "VISITORCOUNT";
		public final static String DANGOLCOUNT		   	= "DANGOLCOUNT";
		public final static String TELNOCNT		   		= "TELNOCNT";
		public final static String INSERTDATE		   	= "INSERTDATE";
		public final static String INSERTTIME		   	= "INSERTTIME";
		public final static String GRPCODE		   		= "GRPCODE";
		public final static String TELNO		   		= "TELNO";
		public final static String ORDERDIS		   		= "ORDERDIS";
		public final static String PLACETELNO_NO		= "PLACETELNO_NO";		
		public final static String TELNONAME 			= "TELNONAME";
		
		
		public static final String[] All = {
			ColumnNames.PLACENO,
			ColumnNames.PLACENAME,  
			ColumnNames.POSTNO,
			ColumnNames.SIDO,
			ColumnNames.GUGUN,
			ColumnNames.ADDRESS1,  
			ColumnNames.ADDRESS2,	 			
			ColumnNames.PHOTOFILECONTENT,
			ColumnNames.BUSINESSTYPENAME,
			ColumnNames.BUSINESSTYPENO,
			ColumnNames.BUSINESSTYPEDETAILNAME,
			ColumnNames.BUSINESSTYPEDETAILNO,
			ColumnNames.LATITUDE,
			ColumnNames.LONGITUDE,
			ColumnNames.MEMO,
			ColumnNames.VISITORCOUNT,
			ColumnNames.DANGOLCOUNT,
			ColumnNames.TELNOCNT,
			ColumnNames.INSERTDATE,
			ColumnNames.INSERTTIME
		};
		
		public static final String[] DG_PLACE_TEL_All = {
			ColumnNames.PLACENO,
			ColumnNames.PLACETELNO_NO,  
			ColumnNames.TELNO,
			ColumnNames.TELNONAME,
		};
	}

	public DG_DANGOL(Activity activity) {
		super(activity);
	}
	
	public int getRecordCount() throws Exception {
		sqlQuery("SELECT COUNT(*) AS CNT FROM " + TABLE_NAME, null);
		return mCursor.getInt( mCursor.getColumnIndex("CNT") );
	}
	
	/**
	 * DG_DANGOL 테이블 조회  
	 */
	public long select(String where, String[] args, String groupBy, String having, String orderBy) throws BizException, Exception {		
		return sqlSelect(TABLE_NAME, ColumnNames.All, where, args, groupBy, having, orderBy);
	}
	
	/**
	 * 내 단골 가져오기 (거리순)
	 * @param keyWord : 검색 키워드. 전체검색을 원할 경우 NULL 을 넘긴다.
	 */
	public long selectDistance(String keyWord) throws BizException, Exception {
		
		String WHERE = "";
		if (keyWord != null)
			WHERE = " WHERE PLACENAME LIKE '%" + keyWord +"%' ";
				
		String sql = " SELECT " 
			+ " 	   PLACENO, PLACENAME, "
			+ "	       COALESCE( "
			+ "	           REPLACE(ADDRESS1, SIDO, (SELECT DISPLAYSIDONAME FROM DG_AREASHORTDISPLAY WHERE SIDO = BASESIDONAME) ), " 
			+ "	           ADDRESS1 "
			+ "	       ) AS ADDRESS1, "       
			+ "	       ADDRESS2, "       
			+ "	       POSTNO, "
			+ "	       PHOTOFILECONTENT, "
			+ "	       LATITUDE, "
			+ "	       LONGITUDE, "
			+ "	       VISITORCOUNT, "       
			+ "	       DANGOLCOUNT, "        
			+ "	       TELNOCNT, "
			+ "	       BUSINESSTYPENO, "       
			+ "	       BUSINESSTYPENAME, "       
			+ "	       BUSINESSTYPEDETAILNAME, "
			+ "	       BUSINESSTYPEDETAILNO, "
			+ "	       MEMO, "
			+ "	       ( SELECT TELNO FROM " 
			+ "	           ( SELECT TELNO, PLACENO FROM DG_PLACETELNO "
			+ "	             ORDER BY PLACENO DESC, PLACETELNO_NO ASC) A "
			+ "	         WHERE DAN.PLACENO = A.PLACENO "        
			+ "	         LIMIT 1) AS TELNO "
			+ "	FROM DG_DANGOL DAN "
			+ WHERE
			+ "	ORDER BY DAN.LATITUDE ASC ";

		sqlQuery(sql, null);
		return SQL_CODE.SQL_OK;
	}

	/**
	 * 내 단골 가져오기 (지역순)
	 * @param area : 현재 위치의 지명 - 서울특별시, 경기도 등
	 * @param keyWord : 검색 키워드. 전체검색을 원할 경우 NULL 을 넘긴다.
	 * @return
	 */
	public long selectArea(String area, String keyWord) throws Exception {
		String WHERE = "";
		if (keyWord != null)
			WHERE = " WHERE PLACENAME LIKE '%" + keyWord +"%' ";
		
		String sql = 
			  "SELECT PLACENO, "
			+ "       PLACENAME, "
			+ "       COALESCE( "
			+ "            REPLACE(ADDRESS1, DAN.SIDO, (SELECT DISPLAYSIDONAME FROM DG_AREASHORTDISPLAY WHERE DAN.SIDO = BASESIDONAME) )," 
			+ "            ADDRESS1"
			+ "       ) AS ADDRESS1,"
			+ "       ADDRESS2, "
			+ "       LATITUDE, "
			+ "       LONGITUDE,"
			+ "       BUSINESSTYPENAME,"       
			+ "       BUSINESSTYPEDETAILNAME,"
			+ "       MEMO,"			
			+ "       CASE WHEN A.ABBREVIATIONNAME IS NULL THEN '주소 미지정' "
			+ "       ELSE COALESCE( ( SELECT A.DISPLAYSIDONAME "
			+ "                        FROM ( SELECT BASESIDONAME, DISPLAYSIDONAME " 
			+ "                               FROM DG_AREASHORTDISPLAY "
			+ "                               WHERE DAN.SIDO = BASESIDONAME " 
			+ "                       ) A  "
			+ "                    ), DAN.SIDO ) || ' ' || DAN.GUGUN "
			+ "       END AS GRPCODE, "
			+ "       ( SELECT TELNO FROM"
			+ "                      ( SELECT TELNO, PLACENO FROM DG_PLACETELNO"
			+ "                        ORDER BY PLACENO DESC, PLACETELNO_NO ASC) A"
			+ "         WHERE DAN.PLACENO = A.PLACENO"        
			+ "         LIMIT 1) AS TELNO "
			+ "FROM DG_DANGOL DAN "
			+ "LEFT JOIN ( SELECT A.DISPLAYSEQ, B.SIDO, B.ABBREVIATIONNAME, A.BASESIDONAME" 
			+ "            FROM DG_AREADISPLAYORDER A"
			+ "            LEFT JOIN (SELECT SIDO, ABBREVIATIONNAME FROM DG_POSTNO WHERE GUGUN IS NULL ) B on B.ABBREVIATIONNAME = A.DISPLAYSIDONAME"
			+ ") A ON  DAN.SIDO = A.SIDO  AND A.BASESIDONAME = ? "
			+ WHERE
			+ "ORDER BY A.DISPLAYSEQ ASC, GRPCODE ASC, UPPER(PLACENAME) ASC";	

		String[] args = new String[]{area};
		sqlQuery(sql, args);
		return SQL_CODE.SQL_OK;
	}
	
	/**
	 * 내 단골 가져오기 (업종순)
	 * @param keyWord : 검색 키워드. 전체검색을 원할 경우 NULL 을 넘긴다.
	 * @return
	 */
	public long selectBizType(String keyWord) throws Exception {
		String WHERE = "";
		if (keyWord != null)
			WHERE = " WHERE PLACENAME LIKE '%" + keyWord +"%' ";
		
		String sql = 
			"SELECT	"
		  + "		BUSINESSTYPENAME || COALESCE(' > ' || BUSINESSTYPEDETAILNAME, '')  AS GRPCODE, "    
		  + "		PLACENO, "    
		  + "		PLACENAME, "
		  + "		COALESCE( "
		  + "		    REPLACE(ADDRESS1, SIDO, (SELECT DISPLAYSIDONAME FROM DG_AREASHORTDISPLAY WHERE SIDO = BASESIDONAME) ), " 
		  + "		    ADDRESS1 "
		  + "		) AS ADDRESS1, "
		  + "	    ADDRESS2, "    
		  + "	    BUSINESSTYPENAME, "
		  + "	    BUSINESSTYPEDETAILNAME, "    
		  + "	    LATITUDE, "    
		  + "	    LONGITUDE, "    
		  + "	    MEMO, "    
		  + "	    TELNO, "    
		  + "	    ' ' AS DISTANCE "
		  + "FROM(  SELECT   "
		  + "	      	DAN.PLACENO, "
		  + "	        DAN.PLACENAME, "
		  + "	        ADDRESS1, "
		  + "	        ADDRESS2, "
		  + "           SIDO, "
		  + "	        BUSINESSTYPENAME, "         
		  + "	        BUSINESSTYPEDETAILNAME, "        
		  + "	        LATITUDE, "
		  + "	        LONGITUDE, "
		  + "	        MEMO, "
		  + "	        ( SELECT TELNO FROM "
		  + "	                      ( SELECT TELNO, PLACENO FROM DG_PLACETELNO "
		  + "	                        ORDER BY PLACENO DESC, PLACETELNO_NO ASC) A "
		  + "	         WHERE DAN.PLACENO = A.PLACENO "        
		  + "	         LIMIT 1) AS TELNO, "         
		  + "	         ( SELECT  DISPLAYORDER FROM DG_BUSINESSTYPE bus where BUS.BUSINESSTYPENO=DAN.BUSINESSTYPENO ) AS BUSINESSTYPEORDER, "
		  + "	         ( SELECT DE.DISPLAYORDER " 
		  + "	           FROM   DG_BUSINESSTYPEDETAIL DE " 
		  + "	           WHERE  DAN.BUSINESSTYPENO=DE.BUSINESSTYPENO " 
		  + "	           AND    DAN.BUSINESSTYPEDETAILNO=DE.BUSINESSTYPEDETAILNO ) AS BUSINESSTYPEDETAILORDER "
		  + "		FROM  DG_DANGOL DAN "
		  + ") A "
		  + WHERE
		  + "ORDER BY BUSINESSTYPEORDER ASC , BUSINESSTYPEDETAILORDER ASC  , UPPER(PLACENAME) ASC ";
		sqlQuery(sql, null);
		return SQL_CODE.SQL_OK;
	}
	
	/**
	 * 내 단골 가져오기 (가나다순)
	 * @param keyWord : 검색 키워드. 전체검색을 원할 경우 NULL 을 넘긴다.
	 * @return
	 */
	public long selectSpell(String keyWord) throws Exception {
		String WHERE = "";
		if (keyWord != null)
			WHERE = " WHERE PLACENAME LIKE '%" + keyWord +"%' ";
		
		String sql = 
			" SELECT "
		  + " 		PLACENO, "
		  + " 		PLACENAME, "		  
		  + " 		COALESCE( "
		  + " 		        REPLACE(ADDRESS1, SIDO, (SELECT DISPLAYSIDONAME FROM DG_AREASHORTDISPLAY WHERE SIDO = BASESIDONAME) ), " 
		  + " 		        ADDRESS1 "
		  + " 		) AS ADDRESS1, "		  
		  + " 		ADDRESS2, "
		  + " 		MEMO, "
		  + " 		BUSINESSTYPENAME, "
		  + " 		LATITUDE, "
		  + " 		LONGITUDE, "  
		  + " 		CASE WHEN PLACENAME < 'ㄱ' THEN " 
		  + " 		          CASE WHEN ( SUBSTR(PLACENAME, 1, 1) >='A' and SUBSTR(PLACENAME, 1, 1) <='Z' ) "  
		  + " 		                 OR ( SUBSTR(PLACENAME, 1, 1) >='a' and SUBSTR(PLACENAME, 1, 1) <='z' ) " 
		  + " 		          THEN UPPER( SUBSTR(PLACENAME, 1 , 1)) "
		  + " 		          ELSE '기타' END "
		  + " 		     WHEN 'ㄱ' <= PLACENAME and PLACENAME <= 'ㅎ' THEN PLACENAME "
		  + " 		     WHEN PLACENAME < '나' THEN 'ㄱ' "
		  + " 		     WHEN PLACENAME < '다' THEN 'ㄴ' "
		  + " 		     WHEN PLACENAME < '라' THEN 'ㄷ' "
		  + " 		     WHEN PLACENAME < '마' THEN 'ㄹ' "
		  + " 		     WHEN PLACENAME < '바' THEN 'ㅁ' "
		  + " 		     WHEN PLACENAME < '사' THEN 'ㅂ' "
		  + " 		     WHEN PLACENAME < '아' THEN 'ㅅ' "
		  + " 		     WHEN PLACENAME < '자' THEN 'ㅇ' "
		  + " 		     WHEN PLACENAME < '차' THEN 'ㅈ' "
		  + " 		     WHEN PLACENAME < '카' THEN 'ㅊ' "
		  + " 		     WHEN PLACENAME < '타' THEN 'ㅋ' "
		  + " 		     WHEN PLACENAME < '파' THEN 'ㅌ' "
		  + " 		     WHEN PLACENAME < '하' THEN 'ㅍ' "
		  + " 		     ELSE 'ㅎ' "
		  + " 		     END AS GRPCODE, "
		  + " 		CASE WHEN PLACENAME < 'ㄱ' THEN "  
		  + " 		          CASE WHEN ( SUBSTR(PLACENAME, 1 , 1) >='A' AND SUBSTR(PLACENAME, 1, 1) <='Z' ) "  
		  + " 		                 OR ( SUBSTR(PLACENAME, 1 , 1) >='a' and SUBSTR(PLACENAME, 1, 1) <='z' ) " 
		  + " 		          THEN PLACENAME "
		  + " 		          ELSE PLACENAME *100 END "
		  + " 		     ELSE 0  END AS ORDERDIS,       "
		  + " 		( SELECT TELNO FROM "
		  + " 		                ( SELECT TELNO, PLACENO FROM DG_PLACETELNO "
		  + " 		                  ORDER BY PLACENO DESC, PLACETELNO_NO ASC) A "
		  + " 		   WHERE DAN.PLACENO = A.PLACENO "        
		  + " 		   LIMIT 1) AS TELNO, "
		  + " 		(  SELECT DE.BUSINESSTYPEDETAILNAME " 
		  + " 		   FROM DG_BUSINESSTYPEDETAIL DE " 
		  + " 		   WHERE DAN.BUSINESSTYPENO=DE.BUSINESSTYPENO " 
		  + " 		   AND   DAN.BUSINESSTYPEDETAILNO=DE.BUSINESSTYPEDETAILNO ) AS BUSINESSTYPEDETAILNAME "
		  + " FROM DG_DANGOL DAN "
		  + WHERE
		  + " ORDER BY ORDERDIS ASC, GRPCODE ASC, UPPER(PLACENAME) ASC ";	
		sqlQuery(sql, null);
		return SQL_CODE.SQL_OK;
	}
	
	/**
	 * PlaceNo 에 해당하는 단골 정보 Select
	 * @param placeNo
	 * @return
	 * @throws Exception
	 */
	public long selectMyDangol(String placeNo) throws Exception {
		
		String sql = "SELECT * FROM " + TABLE_NAME +" AS A "
				+ "LEFT JOIN " + TABLE_NAME_PLACETELNO + " AS B "  
				+ "WHERE A.PLACENO = B.PLACENO AND A.PLACENO = ? LIMIT 1";

		String[] args = new String[]{placeNo};
		sqlQuery(sql, args);
		return SQL_CODE.SQL_OK;
	}
	/**
	 *  PlaceNo 에 해당하는 단골 전화 번호 정보 Select
	 * @param placeNo
	 * @return
	 * @throws Exception
	 */
	public long selectPlaceTelInfo(String placeNo) throws Exception {
		
		String sql = "SELECT * FROM " + TABLE_NAME_PLACETELNO +" WHERE PLACENO = ?";
		
		String[] args = new String[]{placeNo};
		sqlQuery(sql, args);
		return SQL_CODE.SQL_OK;
	}
	
	
	/**
	 * DG_DANGOL 테이블 삽입  
	 * @return 입력 결과 코드
	 * @throws Exception
	 */
	public long insert() throws Exception {
		sqlInsert(TABLE_NAME);
		return SQL_CODE.SQL_OK;
	}
	
	/**
	 * DG_PLACETELNO 테이블 삽입  
	 * @return 입력 결과 코드
	 * @throws Exception
	 */
	public long insertPlaceTelNo(ContentValues value) throws Exception {
		sqlInsert(TABLE_NAME_PLACETELNO, value);
		return SQL_CODE.SQL_OK;		
	}

	/**
	 * DG_DANGOL 테이블 수정
	 * @param where - 업데이트 WHERE 절
	 * @param args - WHERE 조건값
	 * @return 업데이트 결과 코드
	 * @throws Exception
	 */
	public long update(ContentValues value, String where, String[] args) throws Exception {
		
		sqlUpdate(TABLE_NAME, value, where, args);
		return SQL_CODE.SQL_OK;
	}
	
	/**
	 * DG_PLACETELNO 테이블 수정
	 * @param where - 업데이트 WHERE 절
	 * @param args - WHERE 조건값
	 * @return 업데이트 결과 코드
	 * @throws Exception
	 */
	public long updatePlaceTelNo(ContentValues value, String where, String[] args) throws Exception {
		
		sqlUpdate(TABLE_NAME_PLACETELNO, value, where, args);
		return SQL_CODE.SQL_OK;
	}


	/**
	 * DG_DANGOL 테이블 삭제
	 * @param where - 업데이트 WHERE 절
	 * @param args - WHERE 조건값
	 * @return 삭제 결과 코드
	 * @throws Exception
	 */
	public long delete(String where, String[] args) throws Exception {
		sqlDelete(TABLE_NAME, where, args);
		return SQL_CODE.SQL_OK;
	}
	
	public long deletePlaceTelNo(String where, String[] args) throws Exception {
		sqlDelete(TABLE_NAME_PLACETELNO, where, args);
		return SQL_CODE.SQL_OK;
	}


	/**
	 * 
	 * @param val
	 * @throws Exception
	 */
	public void setPLACENO(String val) throws Exception {
		mContents.put(ColumnNames.PLACENO, val);
	}

	public void setPLACENAME(String val) throws Exception {
		mContents.put(ColumnNames.PLACENAME, val);
	}
	
	public void setPOSTNO(String val) throws Exception {
		mContents.put(ColumnNames.POSTNO, val);
	}
	
	public void setSIDO(String val)throws Exception {
		mContents.put(ColumnNames.SIDO, val);
	}
	
	public void setGUGUN(String val)throws Exception {
		mContents.put(ColumnNames.GUGUN, val);
	}
	
	public void setADDRESS1(String val) throws Exception {
		mContents.put(ColumnNames.ADDRESS1, val);
	}
	
	public void setADDRESS2(String val) throws Exception {
		mContents.put(ColumnNames.ADDRESS2, val);
	}
	
	public void setPHOTOFILECONTENT(String val) throws Exception {
		mContents.put(ColumnNames.PHOTOFILECONTENT, val);
	}
	
	public void setBUSINESSTYPENAME(String val) throws Exception {
		mContents.put(ColumnNames.BUSINESSTYPENAME, val);
	}
	
	public void setBUSINESSTYPENO(String val) throws Exception {
		mContents.put(ColumnNames.BUSINESSTYPENO, val);
	}
	
	public void setBUSINESSTYPEDETAILNAME(String val) throws Exception {
		mContents.put(ColumnNames.BUSINESSTYPEDETAILNAME, val);
	}
	
	public void setBUSINESSTYPEDETAILNO(String val) throws Exception {
		mContents.put(ColumnNames.BUSINESSTYPEDETAILNO, val);
	}
	
	public void setLATITUDE(String val) throws Exception {
		mContents.put(ColumnNames.LATITUDE, val);
	}
	
	public void setLONGITUDE(String val) throws Exception {
		mContents.put(ColumnNames.LONGITUDE, val);
	}
	
	public void setMEMO(String val) throws Exception {
		mContents.put(ColumnNames.MEMO, val);
	}
	
	public void setVISITORCOUNT(String val) throws Exception {
		mContents.put(ColumnNames.VISITORCOUNT, val);
	}
	
	public void setDANGOLCOUNT(String val) throws Exception {
		mContents.put(ColumnNames.DANGOLCOUNT, val);
	}
	
	public void setTELNOCNT(String val) throws Exception {
		mContents.put(ColumnNames.TELNOCNT, val);
	}
	
	public void setINSERTDATE(String val) throws Exception {
		mContents.put(ColumnNames.INSERTDATE, val);
	}
	
	public void setINSERTTIME(String val) throws Exception {
		mContents.put(ColumnNames.INSERTTIME, val);
	}
	
	
	
	public void setPLACETELNO_NO(String val) throws Exception {
		mContents.put(ColumnNames.PLACETELNO_NO, val);
	}
	
	public void setTELNONAME(String val) throws Exception {
		mContents.put(ColumnNames.TELNONAME, val);
	}
	
	
	/*
	 * 
	 */
	public String getPLACENO() throws Exception {
		return getString(ColumnNames.PLACENO);
	}

	public String getPLACENAME() throws Exception {
		return getString(ColumnNames.PLACENAME);
	}
	
	public String getPOSTNO() throws Exception {
		return getString(ColumnNames.POSTNO);
	}
	
	public String getSIDO() throws Exception {
		return getString(ColumnNames.SIDO);
	}
	
	public String getGUGUN() throws Exception {
		return getString(ColumnNames.GUGUN);
	}
	
	public String getADDRESS1() throws Exception {
		return getString(ColumnNames.ADDRESS1);
	}
	
	public String getADDRESS2() throws Exception {
		return getString(ColumnNames.ADDRESS2);
	}
	
	public String getPHOTOFILECONTENT() throws Exception {
		return getString(ColumnNames.PHOTOFILECONTENT);
	}
	
	public String getBUSINESSTYPENAME() throws Exception {
		return getString(ColumnNames.BUSINESSTYPENAME);
	}
	
	public String getBUSINESSTYPENO() throws Exception {
		return getString(ColumnNames.BUSINESSTYPENO);
	}
	
	public String getBUSINESSTYPEDETAILNAME() throws Exception {
		return getString(ColumnNames.BUSINESSTYPEDETAILNAME);
	}
	
	public String getBUSINESSTYPEDETAILNO() throws Exception {
		return getString(ColumnNames.BUSINESSTYPEDETAILNO);
	}
	
	public double getLATITUDE() throws Exception {
		return getDouble(ColumnNames.LATITUDE);
	}
	
	public double getLONGITUDE() throws Exception {
		return getDouble(ColumnNames.LONGITUDE);
	}
	
	public String getMEMO() throws Exception {
		return getString(ColumnNames.MEMO);
	}
	
	public String getVISITORCOUNT() throws Exception {
		return getString(ColumnNames.VISITORCOUNT);
	}
	
	public String getDANGOLCOUNT() throws Exception {
		return getString(ColumnNames.DANGOLCOUNT);
	}
	
	public String getTELNOCNT() throws Exception {
		return getString(ColumnNames.TELNOCNT);
	}
	
	public String getINSERTDATE() throws Exception {
		return getString(ColumnNames.INSERTDATE);
	}
	
	public String getINSERTTIME() throws Exception {
		return getString(ColumnNames.INSERTTIME);
	}
	
	
	public String getGRPCODE() throws Exception {
		return getString(ColumnNames.GRPCODE);
	}	
	
	public String getTELNO() throws Exception {
		return getString(ColumnNames.TELNO);
	}	
	
	public String getORDERDIS() throws Exception {
		return getString(ColumnNames.ORDERDIS);
	}
	
	
	
	public String getTELNONAME() throws Exception {
		return getString(ColumnNames.TELNONAME);
	}
	
	public String getPLACETELNO_NO() throws Exception {
		return getString(ColumnNames.PLACETELNO_NO);
	}
	
}
