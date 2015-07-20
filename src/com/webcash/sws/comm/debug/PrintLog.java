package com.webcash.sws.comm.debug;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.webcash.sws.comm.define.Msg;
import com.webcash.sws.comm.pref.LibConf;
import com.webcash.sws.comm.util.ComUtil;

public class PrintLog {
	
	
	private static String CurrDate(String delem) {
		Date today;
		today = Calendar.getInstance().getTime();

		SimpleDateFormat format = new SimpleDateFormat(delem);
		String strDay = format.format(today);

		return strDay;
	}
	
	private static String getPrompt(Context ctx) {
		try {
			if(ctx == null) return "";
			int pos = ctx.getClass().toString().lastIndexOf(".");
			String prompt = ctx.getClass().toString().substring(pos+1) + "|";
			return prompt;
		} catch(Exception e) {
			printException(ctx, e);
			return "";
		}
	}
	
	public static void printReqMsg(Context ctx, String tranCd, HashMap<String, Object> reqMsg) {
		String key, buf;
		if(LibConf.ISRELEASE) return;
		try {
			buf = LibConf.NEW_LINE + "[ REQUEST MESSAGE SEND ]";
			Log.d(LibConf.LOGTAG, buf);
			
			buf = ComUtil.conStr("TRANCD=[&]", tranCd);
			Log.d(LibConf.LOGTAG, buf);
			
			if(reqMsg == null) return;
			
			for(Iterator<String> iter = reqMsg.keySet().iterator(); iter.hasNext(); ) {
				key = iter.next();
				Log.d(LibConf.LOGTAG, "    (0)" + key + "=[" + reqMsg.get(key).toString() + "]");
			}
		} catch(Exception e) {
			Log.d(LibConf.LOGTAG, "[ printReqMsg error ]");
		}
	}
	
	public static void printResMsg(Context ctx, String tranCd, Object obj) {
		printResMsg(ctx, tranCd, obj, null);
	}
	
	public static void printResMsg(Context ctx, String tranCd, Object obj, String className) {
		JSONArray jarr, jarr2; 
		JSONObject jobj, jobj2;
		String key, buf;
		try {
			if(LibConf.ISRELEASE) return;
			
			jarr = (JSONArray) obj;
			
			buf = LibConf.NEW_LINE + "[ RESPONSE MESSAGE RECEIVE ]";
			Log.d(LibConf.LOGTAG, buf);
			
			buf = ComUtil.conStr("TRANCD=[&]", tranCd);
			Log.d(LibConf.LOGTAG, buf);
			
			if(className != null) {
				buf = ComUtil.conStr("CLASS NAME=[&]", className);
				Log.d(LibConf.LOGTAG, buf);
			}
			
			for(int i = 0; i < jarr.length(); i++) {
				jobj = jarr.getJSONObject(i);
				
				for(Iterator<String> iter = jobj.keys(); iter.hasNext(); ) {
					key = iter.next();
					
					Log.d(LibConf.LOGTAG, "    (" + i + ")" + key + "=[" + jobj.get(key).toString() + "]");

/*					if(jobj.get(key).toString().length() <= 2 || ! jobj.get(key).toString().substring(0, 2).equals("[{")) {
						continue;
					}
					
					jarr2 = jobj.getJSONArray(key);
					for(int j = 0; j < jarr2.length(); j++) {
						jobj2 = jarr2.getJSONObject(j);

						for(Iterator<String> iter2 = jobj2.keys(); iter2.hasNext();) {
							key = iter2.next();
							Log.d(Conf.LOGTAG, "        (" + j + ")" + key + "=[" + jobj2.get(key).toString() + "]");
						}
					}*/
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printStartActivity(Context ctx, Intent intent) {
		String key;
		try {
			if(LibConf.ISRELEASE) return;
			
			if(ctx == null) return;
			if(intent == null) return;
			
			Log.d(LibConf.LOGTAG, "\r\n[ START ACTIVITY ]");
			
			if(intent.getComponent() != null) {
				String nm = intent.getComponent().toString();
				Log.d(LibConf.LOGTAG, "ACTIVITY=[" + nm + "]");
			}
			
			Bundle bundle = intent.getExtras();
			if(bundle == null) return;
			for(Iterator<String> iter = intent.getExtras().keySet().iterator(); iter.hasNext(); ) {
				key = iter.next();
				Log.d(LibConf.LOGTAG, key + "=[" + bundle.get(key).toString() + "]");
			}

		} catch(Exception e) {
			Log.d(LibConf.LOGTAG, "[ printStartActivity error ]");
		}
	}
	
	public static void printGetIntent(Context ctx, Intent intent) {
		String key;
		try {
			if(LibConf.ISRELEASE) return;
			
			if(ctx == null) return;
			if(intent == null) return;
			
			Log.d(LibConf.LOGTAG, "\r\n[ GET INTENT ]");
			
			if(intent.getComponent() != null) {
				String nm = intent.getComponent().toString();
				Log.d(LibConf.LOGTAG, "ACTIVITY=[" + nm + "]");
			}
			
			Bundle bundle = intent.getExtras();
			if(bundle == null) return;
			
			for(Iterator<String> iter = intent.getExtras().keySet().iterator(); iter.hasNext(); ) {
				key = iter.next();
				Log.d(LibConf.LOGTAG, key + "=[" + bundle.get(key).toString() + "]");
			}

		} catch(Exception e) {
			Log.d(LibConf.LOGTAG, "[ printGetIntent error ]");
		}
	}
	
	public static void printResultIntent(Context ctx, Intent intent) {
		String key;
		try {
			if(LibConf.ISRELEASE) return;
			
			if(ctx == null) return;
			if(intent == null) return;
			
			Log.d(LibConf.LOGTAG, "\r\n[ ACTIVITY RESULT ]");
			
			if(intent.getComponent() != null) {
				String nm = intent.getComponent().toString();
				Log.d(LibConf.LOGTAG, "ACTIVITY=[" + nm + "]");
			}
			
			Bundle bundle = intent.getExtras();
			if(bundle == null) return;
			
			for(Iterator<String> iter = intent.getExtras().keySet().iterator(); iter.hasNext(); ) {
				key = iter.next();
				Log.d(LibConf.LOGTAG, key + "=[" + bundle.get(key).toString() + "]");
			}

		} catch(Exception e) {
			Log.d(LibConf.LOGTAG, "[ printResultIntent error ]");
		}
	}
	
	public static void printException(Context ctx, String msg, Exception ex) {
		try {
			if(LibConf.ISRELEASE) return;
			
			Log.d(LibConf.LOGTAG, ComUtil.conStr("[ & ]", ex.getClass().toString()));
			Log.d(LibConf.LOGTAG, ComUtil.conStr("    CONTEXT=[&]", ctx.toString()));
			Log.d(LibConf.LOGTAG, ComUtil.conStr("    MESSAGE=[&]", msg));
			Log.d(LibConf.LOGTAG, ComUtil.conStr("    &", ex.getMessage()));
			for(int i=0;i < ex.getStackTrace().length; i++) {
				Log.d(LibConf.LOGTAG, ex.getStackTrace()[i].toString());
			}
		} catch(Exception e) {
			Log.d(LibConf.LOGTAG, "[ printException error ]");
			e.printStackTrace();
		}
	}


	public static void printException(Context ctx, Exception ex) {
		try {
			if(LibConf.ISRELEASE) return;

			Log.d(LibConf.LOGTAG, ComUtil.conStr("\r\n[ & ]", ex.getClass().toString()));
			Log.d(LibConf.LOGTAG, ComUtil.conStr("    &", ex.getMessage()));

			for(int i=0;i < ex.getStackTrace().length; i++) {
				Log.d(LibConf.LOGTAG, "    " + ex.getStackTrace()[i].toString());
			}
		} catch(Exception e) {}
	}
	
	/**
	 * Log를 남긴다.
	 */
	public static void printLog(Context ctx, String msg, String...vargs) {
		if(LibConf.ISRELEASE) return;
		
		for(String var : vargs){
			msg = msg.replaceFirst(LibConf.REGULAR_EXPR, var);
		}		
		
		Log.d(LibConf.LOGTAG, msg);
	}
	
	public static void printSingleLog(Context ctx, String msg, String var) {
		if(LibConf.ISRELEASE) return;
		
		Log.d(msg,var);
		
	}
	
	public static void printSingleLog(String msg, String var) {
		if(LibConf.ISRELEASE) return;
		
		Log.d(msg,var);
	}
	
	
	public static class DB {
		public static void Exp(Context atvt, SQLException ex) {
			Toast.makeText(atvt, Msg.Err.Sql.Default, Toast.LENGTH_SHORT);
			printException(atvt, ex);
		}
	}
	
	
	

	/**
	 * 오류가 발생한 경우 사용하도록 한다.
	 * <p>
	 * WEBCASH 폴더에 yyyyMMdd.log 파일명으로 저장된다.
	 * <p>
	 * Exception 객체의 오류 메시지를 SD카드에 저장한다.
	 * 
	 * @param e
	 */
	public static void saveSdCardErrorLog(Exception e) {

		if(LibConf.ISRELEASE) return;

		String[] message = new String[e.getStackTrace().length];
		for (int i = 0; i < e.getStackTrace().length; i++) {
			message[i] = e.getStackTrace()[i].toString();
		}
		saveSdCardErrorLog(message);
	}

	/**
	 * 오류가 발생한 경우 사용하도록 한다.
	 * <p>
	 * WEBCASH 폴더에 yyyyMMdd.log 파일명으로 저장된다.
	 * <p>
	 * 
	 * @param message
	 */
	public static void saveSdCardErrorLog(String... message) {

		if(LibConf.ISRELEASE) return;

		String path = Environment.getExternalStorageDirectory() + "/WEBCASH";
		File logFile = new File(path);
		if (!logFile.exists()) {
			logFile.mkdir();
		}

		String fileName = "/" + CurrDate("yyyy-MM-dd") + ".err";
		logFile = new File(path + fileName);
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException exp) {
				exp.printStackTrace();
			}
		}
		try {
			// BufferedWriter for performance, true to set append to file flag
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
					true));
			buf.newLine();
			buf.newLine();
			buf.append("[").append(CurrDate("HH:mm:ss SSS")).append("]");

			for (int i = 0; i < message.length; i++) {
				buf.newLine();
				buf.append(message[i]);
			}
			buf.close();
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}

	/**
	 * SD카드에 WEBCASH 폴더에 로그를 저장한다.
	 * 
	 * @param tag
	 *            : 파일명
	 * @param message
	 *            : 저장할 로그
	 */
	public static void saveSingleLog(String tag, String message) {
		if(LibConf.ISRELEASE) return;
		
		try {

			File logFile = getLogFile(tag);

			if (logFile == null)
				return;

			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
					true));
			buf.append("[").append(CurrDate("HH:mm:ss SSS")).append("]");
			buf.newLine();
			buf.append(message);
			buf.newLine();
			buf.newLine();

			buf.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * SD카드에 WEBCASH 폴더에 당일 날짜의 파일명으로 로그를 저장한다.
	 * 
	 * @param message
	 *            : 저장할 메시지
	 */
	public static void saveSdCard(String... message) {

		if(LibConf.ISRELEASE) return;

		String fileName = "/" + CurrDate("yyyy-MM-dd") + ".log";

		try {

			File logFile = getLogFile(fileName);

			if (logFile == null)
				return;

			// BufferedWriter for performance, true to set append to file flag
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
					true));
			for (int i = 0; i < message.length; i++) {
				buf.append("[").append(CurrDate("HH:mm:ss SSS")).append("]");
				buf.newLine();
				buf.append(message[i]);
				buf.newLine();
			}
			buf.newLine();
			buf.close();
		} catch (IOException exp) {
			exp.printStackTrace();
		}

	}

	private static File getLogFile(String fileName) {

		String path = Environment.getExternalStorageDirectory() + "/WEBCASH";
		File logFile = new File(path);
		if (!logFile.exists()) {
			logFile.mkdir();
		}

		if (fileName.indexOf("/") < 0)
			fileName = "/" + fileName;
		if (fileName.indexOf(".") < 0)
			fileName = fileName + ".log";

		logFile = new File(path + fileName);
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
				return logFile;
			} catch (IOException exp) {
				exp.printStackTrace();
				return null;
			}
		}
		return logFile;
	}
	
	
	
}