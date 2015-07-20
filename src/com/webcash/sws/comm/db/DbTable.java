/**
 * <pre>
 * 단골 2.0
 * @COPYRIGHT (c) 2012 WebCash, Inc. All Right Reserved.
 *
 * @author       : WebCash
 * @Description  : 디비 컨트롤
 * @History      :
 *
 * </pre>
 **/
package com.webcash.sws.comm.db;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.webcash.sws.comm.debug.PrintLog;
import com.webcash.sws.comm.define.Msg;
import com.webcash.sws.comm.pref.LibConf;
import com.webcash.sws.comm.util.BizException;
import com.webcash.sws.comm.util.Check;
import com.webcash.sws.comm.util.Check.JOSA;

public class DbTable {
	protected Activity mActivity;
	protected DbColumn[] mLayout = null;
	private SQLiteDatabase mDb = null;
	protected Cursor mCursor = null;
	public ContentValues mContents;

	/**
	 * 디비처리 결과메세지<BR>
	 * SQL_OK - 정상처리<BR>
	 * SQL_ERROR - 비정상처리<BR>
	 * DATA_NOT_FOUND - 조회 결과 없음<BR>
	 * TOO_MANY_ROWS - 조회값 초과<BR>
	 * NULL_CONTENT_VALUES - 입력값 NULL<BR>
	 */
	public class SQL_CODE {
		public static final int SQL_OK = 1;
		public static final int SQL_ERROR = -1;
		public static final int DATA_NOT_FOUND = 0;
		public static final int TOO_MANY_ROWS = -2;
		public static final int NULL_CONTENT_VALUES = -3;
	}

	/**
	 * 생성자 <BR>
	 * @param activity - 액티비티<BR>
	 */
	public  DbTable(Activity activity){
		if(null == activity) return;

		mActivity = activity;
		mContents = new ContentValues();
		
	}

	/**
	 * DB OPEN
	 * @throws Exception
	 */
	public synchronized void open() throws Exception {
		PrintLog.printSingleLog("dilky", "DbTable Open Activity --> " + mActivity.getClass().getName() );
		if(mDb == null || !mDb.isOpen()) 
			mDb = mActivity.openOrCreateDatabase(LibConf.DB_NAME, SQLiteDatabase.OPEN_READWRITE, null);
	}
	
	/**
	 * DB OPEN 검증
	 * @throws Exception
	 */
	public synchronized  boolean isOpen() throws Exception {
		return mDb.isOpen();
	}
	
	/**
	 * DB CLOSE
	 * @throws Exception
	 */
	public synchronized  void close() throws Exception{
		if(mDb.isOpen()) {
			mDb.close();
			closeCursor();
		}
		
	}
	
	public void beginTransaction() {
		if(mDb.isOpen()) mDb.beginTransaction();
	}
	
	public void endTransaction() {
		if(mDb.isOpen()) mDb.endTransaction();
	}
	
	/**
	 * beginTransaction, endTransaction 사이에 setCommit 을 호출해주어야 commit 된다.
	 * setCommit 호출하지않고 endTransaction 호출시 Rollback된다.
	 */
	public void setCommit() {
		if(mDb.isOpen()) mDb.setTransactionSuccessful();
	}

	/**
	 * CONTENT VALUE 초기화
	 * @throws Exception
	 */
	public void clearContents() throws Exception {
		if(null == mContents) return;
		mContents.clear();
	}

	/**
	 * CONTENT VALUE에서 key로 value 조회
	 * @param key
	 * @return key에 해당하는 value
	 */
	protected String getContentValue(String key) {
		return mContents.getAsString(key);
	}

	/**
	 * 업데이트 쿼리 시 컬럼값 검증 (컬럼의 길이, 타입, 필수값 체크)
	 * @param columns
	 * @return 결과 코드
	 * @throws BizException
	 * @throws Exception
	 */
	protected boolean verifyUpdate(DbColumn[] columns) throws BizException, Exception{
		int minLen, maxLen, type;
		String val;

		for(int idx = 0; idx < columns.length; idx++) {
			maxLen = columns[idx].getLength();
			type = columns[idx].getType();
			
			if(mContents.containsKey(columns[idx].getId()))
				val = getContentValue(columns[idx].getId());
			else continue;
			
			if(columns[idx].isPk()) minLen = 1;
			else minLen = 0;

			switch (type) {
			case DbColumn.TYPE_INT:
				Check.isNumber(val, null);
				break;

			case DbColumn.TYPE_TEXT:
				Check.string(mActivity, val, minLen, maxLen, null, null);
				break;

			case DbColumn.TYPE_DATE:
				Check.isDate(mActivity, val, null, null, true);
				break;

			default:
				break;
			}
		}
		return true;
	}


	/**
	 * 입력 시 입력값 검증 필수값 NULL 체크
	 * @param columns
	 * @return 결과코드
	 * @throws BizException
	 * @throws Exception
	 */
	protected boolean verifyInsert(DbColumn[] columns) throws BizException, Exception{
		String val = null;

		// 필수값 입력 체크
		for(int idx = 0; idx < columns.length; idx++) {
			// 일련번호일땐 넘김
			if(columns[idx].getType() == DbColumn.TYPE_AUTO) continue;
			
			// 컬럼 속성이 필수값이거나 notNull일때
			if(columns[idx].isPk() || columns[idx].isNotNull()) {
				val = getContentValue(columns[idx].getId());
				
				// 값이 null 일 경우 예외 던짐 
				if(val == null || "".equals(val)) {
					String josa = Check.getJOSA(columns[idx].getName(), JOSA.YN);
					throw new BizException(0, columns[idx].getName() + josa + Msg.Err.Sql.PRIMARY_KEY_NOT_FOUND);
				}
			}
		}
		return verifyUpdate(columns);
	}

	/**
	 * 입력 쿼리 
	 * @param table 입력 테이블 명
	 * @return 결과 코드
	 * @throws Exception
	 */
	protected long sqlInsert(String table) throws Exception{
		if(null == mContents) return SQL_CODE.NULL_CONTENT_VALUES;
		return mDb.insert(table, null, mContents);
	}
	
	protected long sqlInsert(String table, ContentValues value) throws Exception{
		if(null == value) return SQL_CODE.NULL_CONTENT_VALUES;
		return mDb.insert(table, null, value);
	}

	/**
	 * 업데이트 쿼리
	 * @param table 업데이트 테이블 명
	 * @param where WHERE절
	 * @param args WHERE절 조건값
	 * @return 결과 코드
	 * @throws Exception
	 */
	protected int sqlUpdate(String table, String where, String[] args) throws Exception {
		return mDb.update(table, mContents, where, args);
	}
	
	protected long sqlUpdate(String table, ContentValues value , String where, String[] args) throws Exception{
		if(null == value) 
			return SQL_CODE.NULL_CONTENT_VALUES;
		return mDb.update(table, value, where, args);
	}
	
	

	/**
	 * delete
	 * @param table 삭제 테이블 명
	 * @param where WHERE 절
	 * @param args WHERE절 조건값
	 * @return 결과 코드
	 * @throws Exception
	 */
	protected int sqlDelete(String table, String where, String[] args) throws Exception {
		return mDb.delete(table, where, args);
	}

	/**
	 * select
	 * @param table 조회 테이블 명
	 * @param columns 조회 컬럼
	 * @param selection 조회 조건
	 * @param args 조회 조건값
	 * @param groupBy 
	 * @param having
	 * @param orderBy
	 * @return 결과코드
	 * @throws Exception
	 */
	protected long sqlSelect(String table, String[] columns, String selection, String[] args, String groupBy, String having, String orderBy) throws Exception {
		mCursor = mDb.query(table, columns, selection, args, groupBy, having, orderBy);
		mCursor.moveToFirst();
		return SQL_CODE.SQL_OK;
	}

	/**
	 * query
	 * @param sql 실행할 쿼리
	 * @param args WHERE 절 조건값
	 * @return 결과 코드
	 * @throws Exception
	 */
	public long sqlQuery(String sql, String[] args) throws Exception {
		mCursor = mDb.rawQuery(sql, args);
		mCursor.moveToFirst();		
		return SQL_CODE.SQL_OK;
	}	

	/**
	 * Cursor row count
	 * @return 조회 row count
	 * @throws Exception
	 */
	public int getCount() throws Exception {
		return mCursor.getCount();
	}

	/**
	 * Cursor moveToNext
	 * @throws Exception
	 */
	public void moveNext() throws Exception {
		mCursor.moveToNext();
	}

	/**
	 * Cursor moveToFirst
	 * @throws Exception
	 */
	public void moveFirst() throws Exception {
		mCursor.moveToFirst();
	}

	/**
	 * Cursor isAfterLast
	 * @return Cursor isAfterLast
	 * @throws Exception
	 */
	public Boolean isLast() throws Exception{
		return mCursor.isAfterLast();
	}

	/**
	 * Cursor close
	 * @throws Exception
	 */
	public void closeCursor() throws Exception {
		if (mCursor != null) mCursor.close();
	}
	

	/**
	 * 커서에서 컬럼값에 해당하는 문자열 값 조회
	 * @param colName
	 * @return 문자열 값
	 */
	protected String getString(String colName) {
		int idx = mCursor.getColumnIndexOrThrow(colName);
		return mCursor.getString(idx);
	}

	/**
	 * 커서에서 컬럼값에 해당하는 int 값 조회
	 * @param colName
	 * @return 숫자 값 
	 */
	protected int getInt(String colName) {
		int idx = mCursor.getColumnIndexOrThrow(colName);
		return mCursor.getInt(idx);
	}
	
	/**
	 * 커서에서 컬럼값에 해당하는 long 값 조회
	 * @param colName
	 * @return 숫자 값
	 */
	protected long getLong(String colName) {
		int idx = mCursor.getColumnIndexOrThrow(colName);
		return mCursor.getLong(idx);
	}

	/**
	 * 커서에서 컬럼값에 해당하는 double 값 조회
	 * @param colName
	 * @return 숫자 값
	 */
	protected double getDouble(String colName) {
		int idx = mCursor.getColumnIndexOrThrow(colName);
		return mCursor.getDouble(idx);
	}
	
}