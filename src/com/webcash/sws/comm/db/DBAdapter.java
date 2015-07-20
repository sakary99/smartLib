package com.webcash.sws.comm.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
	private DatabaseHelper mHelper;
	private SQLiteDatabase mDb;
	
	private static final String DATABASE_NAME 	 = "TEL_NUM_TABLE.db";	// 데이터베이스 이름
	private static final int    DATABASE_VERSION = 3;					// 데이터베이스 버전
	private static String SQL_TABLE_CREATE;								// 테이블 생성하는 쿼리
	private static String TABLE_NAME;									// 테이블명
	
	/**
	 * 테이블 생성하는 쿼리
	 */
	public static final String SQL_CREATE_MEMBER = 
			" CREATE TABLE TEL_NUM_TABLE (" + 
			" TEL_NUM TEXT NOT NULL PRIMARY KEY," + 
			" CREATE_DATE TEXT NOT NULL," +
			" CREATE_TIME TEXT NOT NULL" +
			")";
	
	/*
	 * 테이블 컬럼
	 */
	public static class ColumnNames{
		public final static String TEL_NUM = "TEL_NUM";				// 전화번호
		public final static String CREATE_DATE = "CREATE_DATE";		// 생성일자
		public final static String CREATE_TIME = "CREATE_TIME";		// 생성시간
		
		public static final String[] All = {
			ColumnNames.TEL_NUM,  
			ColumnNames.CREATE_DATE,
			ColumnNames.CREATE_TIME,
		};
	}
	
	private Context mCtx = null;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_TABLE_CREATE);
			
		}
		
		@Override
		public void onOpen(SQLiteDatabase db) {
			super.onOpen(db);
		}
		

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersino) {
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		}
		
	}
	
	public DBAdapter(Context ctx, String sql, String tableName) {
		this.mCtx = ctx;
		SQL_TABLE_CREATE = sql;
		TABLE_NAME = tableName;
				
	}
	
	public DBAdapter open() throws SQLException {
		mHelper = new DatabaseHelper(mCtx);
		mDb = mHelper.getWritableDatabase();
		return this;
	}
			
	public void close() {
		mHelper.close();
	}
	
	
	/**
	 * 테이블 입력
	 * @param values
	 * @return
	 */
	public long insertTable(ContentValues values) {
		return mDb.insert(TABLE_NAME, null, values);
	}
	
	/**
	 * 테이블 삭제
	 * @param pkColumn
	 * @param pkData
	 * @return
	 */
	public boolean deleteTable(String pkColumn, long pkData) {
		return mDb.delete(TABLE_NAME, pkColumn + "=" + pkData, null) > 0;
	}
	
	/**
	 * 테이블 조회
	 * @param columns
	 * @param selection
	 * @param selectArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	public Cursor selectTable(String[] columns, String selection, String[] selectArgs, 
							  String groupBy, String having, String orderBy) {
		return mDb.query(TABLE_NAME, columns, selection, selectArgs, groupBy, having, orderBy);
	}
	
	/**
	 * 테이블 갱신
	 * @param values
	 * @param pkColumn
	 * @param pkData
	 * @return
	 */
	public boolean updateTable(ContentValues values, String pkColumn, long pkData){
		return mDb.update(TABLE_NAME, values, pkColumn + "="+pkData, null) > 0;
	}		
}
