package com.webcash.sws.comm.db.biz;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.webcash.sws.comm.pref.LibConf;

public class AlterTable {
	
	@SuppressWarnings("null")
	public static void execute(Activity atvt) {
		SQLiteDatabase SQLiteDb = null;
		Cursor cursor = null;
		
		ArrayList<SQL> sqlList = new ArrayList<SQL>();
		
		//String exist = "SELECT * FROM sqlite_master WHERE tbl_name = 'DG_CONTACT' AND sql like '%PHOTOSYNCDATE%';";
		//String alter = "ALTER TABLE DG_CONTACT ADD PHOTOSYNCDATE VARCHAR2(14)";
		//sqlList.add(new SQL(exist, alter));
		
		try {
			
			if(SQLiteDb == null || !SQLiteDb.isOpen()) 
				SQLiteDb = atvt.openOrCreateDatabase(LibConf.DB_NAME, SQLiteDatabase.OPEN_READWRITE, null);
			
			for(int idx = 0; idx < sqlList.size(); idx++) {
				cursor = SQLiteDb.rawQuery(sqlList.get(idx).ExistSQL, null);
				if( cursor.getCount() == 0 ) {
					SQLiteDb.rawQuery(sqlList.get(idx).AlterSQL, null);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (SQLiteDb != null) 
				SQLiteDb.close();
		}
	}
	
	static class SQL {
		String ExistSQL;
		String AlterSQL;
		public SQL(String _exist, String _alter) {
			ExistSQL = _exist;
			AlterSQL = _alter;
		}
	}
	
	

}
