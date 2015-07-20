package com.webcash.sws.comm.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Environment;
import android.view.Gravity;
import android.widget.TextView;

import com.webcash.sws.comm.debug.LogUtil;
import com.webcash.sws.comm.debug.PrintLog;
import com.webcash.sws.comm.define.Msg.BtnTitle;
import com.webcash.sws.comm.util.BizException;
import com.webcash.sws.comm.util.ComUtil;
import com.webcash.sws.comm.util.Convert;
import com.webcash.sws.lib.BuildConfig;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class DlgAlert {
	
	private static boolean mLogSave = false;
	public static void setLogSave(boolean value) { mLogSave = value; }
	public static boolean isLogSave() { return mLogSave;  }
	
	private static Activity mParent = null;
	private static Object mObject = null;
	private static String mOkMethod = null;
	private static String mAllMethod = null;
	private static String mOneMethod = null;
	private static String mCancelMethod = null;
	private static final String alertTitle = "알림";
	private static final String errorTitle = "경고";
	private static final String regularExpr = "&";
	public static final String mAlertTitle = alertTitle;
	private static Object[] mParams = null;
	
	private static final int DEFAULT_DIALOG_INDEX = -9;

	public static AlertDialog.Builder getDialogCreate (Activity atvt) {
		AlertDialog.Builder ad;
		
		if (Integer.parseInt(Build.VERSION.SDK) >=  Build.VERSION_CODES.HONEYCOMB) {
			ad = new AlertDialog.Builder(atvt, AlertDialog.THEME_HOLO_LIGHT);	
		} else {
			ad = new AlertDialog.Builder(atvt);
		}
		return ad;
	}
	
	public static void Alert(Activity atvt, String msg) {
		Alert(atvt, msg, null, null);
	}
	public static void Alert(Activity atvt, String msg, String method) {
		Alert(atvt, msg, atvt, method);
	}
	
	public static void Alert(Activity atvt, String msg, final int DialogIndex, final onOkMethodListener okListener ) {
		Alert(atvt, msg, DialogIndex, okListener, true);
	}
	
	public static void Alert(Activity atvt, String msg, final int DialogIndex, final onOkMethodListener okListener, boolean Cancelable ) {
		mParent = atvt;

		AlertDialog.Builder ad = getDialogCreate(atvt);

		ad.setTitle(alertTitle);
		ad.setMessage(msg);
		ad.setCancelable(Cancelable);
		ad.setNeutralButton(BtnTitle.BTN_OK, new DialogInterface.OnClickListener() {   
			public void onClick(DialogInterface dialog, int whichButton) {
				if(okListener != null) {
					try {
						okListener.onClickAlertOkButton(DialogIndex);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		AlertDialog dialog = ad.show();
		
		TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
		messageText.setGravity(Gravity.CENTER);
	}
	
	public static void AlertOk(Activity atvt, String msg, final onOkMethodListener okListener) {
		Alert(atvt, msg, DEFAULT_DIALOG_INDEX, okListener, true);
	}

	
	
	public static void Alert(Activity atvt, String msg, Object obj, String method){
		mParent = atvt;
		mObject = obj;
		mOkMethod = method;

		AlertDialog.Builder ad = getDialogCreate(atvt);

		ad.setTitle(alertTitle);
		ad.setMessage(msg);
		ad.setNeutralButton(BtnTitle.BTN_OK, new DialogInterface.OnClickListener() {   
			public void onClick(DialogInterface dialog, int whichButton) {
				if(mOkMethod != null) {
					try {
						Class<?> cls = mObject.getClass();
						Method method = cls.getMethod(mOkMethod);
						method.invoke(mObject);
					} catch(Exception e) {
						PrintLog.printException(mParent, e);
					}
				}
			}
		});
		ad.show();
	}
	
	
	
	/**
	 * 알림메세지창을 활성화 한후, 확인버튼 클릭시 해당액티비티의 메소드를 호출한다
	 * @param atvt
	 * @param msg
	 * @param okMethod
	 */
	public static void AlertOk(Activity atvt, String msg, String okMethod){
		 AlertOk(atvt, msg, atvt, okMethod);
	}
	
	/**
	 * 알림메세지창을 활성화 한후, 확인버튼 클릭시 해당액티비티의 메소드를 호출한다
	 * @param atvt
	 * @param msg
	 * @param okObject
	 * @param okMethod
	 */
	private static void AlertOk(Activity atvt, String msg, Activity okObject, String okMethod) {
		mParent = atvt;
		mObject = okObject;
		mOkMethod = okMethod;

		AlertDialog.Builder ad = getDialogCreate(atvt);

		ad.setTitle(alertTitle);
		ad.setMessage(msg);
		ad.setNeutralButton(BtnTitle.BTN_OK, new DialogInterface.OnClickListener() {   
			public void onClick(DialogInterface dialog, int whichButton) {
				if(mOkMethod != null) {
					try {
						Class<?> cls = mObject.getClass();
						Method method = cls.getMethod(mOkMethod);
						method.invoke(mObject);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		ad.show();
		
	}
	
	/**
	 * 알림메세지창을 활성화 한후, 확인버튼 클릭시 해당액티비티의 메소드를 호출한다
	 * @param atvt
	 * @param msg
	 * @param okObject
	 * @param okMethod
	 */
	public static void AlertOkBackFalse(Activity atvt, String msg, Activity okObject, String okMethod) {
		mParent = atvt;
		mObject = okObject;
		mOkMethod = okMethod;

		AlertDialog.Builder ad = getDialogCreate(atvt);
	
		ad.setCancelable(false);
		ad.setTitle(alertTitle);
		ad.setMessage(msg);
		ad.setNeutralButton(BtnTitle.BTN_OK, new DialogInterface.OnClickListener() {   
			public void onClick(DialogInterface dialog, int whichButton) {
				if(mOkMethod != null) {
					try {
						Class<?> cls = mObject.getClass();
						Method method = cls.getMethod(mOkMethod);
						method.invoke(mObject);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		ad.show();
		
	}
	
	/**
	 * 알림메시지창을 활성화 한후, 버튼1메시지, 버튼2메시지, 클릭시 해당액티비티의 메소드를 호출한다. 
	 * @param atvt - 액티비티
	 * @param msg - 알림메시지
	 * @param okMsg - 버튼1메시지
	 * @param noMsg - 버튼2메시지
	 * @param okMethod - 메소드명
	 * @param cancelMethod - 메소드명
	 */
	public static void AlertOkCancle(Activity atvt, String msg, String okMsg, String noMsg, String okMethod, String cancelMethod ){
		AlertOkCancel(atvt, msg, atvt, okMsg, noMsg, okMethod, cancelMethod);
	}
	
	
	/**
	 * 알림메세지창을 활성화 한후, 확인버튼 클릭시 해당액티비티의 메소드를 호출한다
	 * 
	 * @param atvt - 액티비티
	 * @param msg - 알림메세지
	 * @param mtdnm - 메소드명
	 */
	public static void AlertOkCancel(Activity atvt, String msg, String okMethod, String cancelMethod){
		AlertOkCancel(atvt, msg, atvt, okMethod, cancelMethod);
	}
	
	public interface onOkMethodListener {
		public void onClickAlertOkButton(int DialogIndex);
	}
	public interface onCancelMethodListener {
		public void onClickAlertCancelButton(int DialogIndex);
	}
	
	public static void AlertOkCancel(Activity atvt, String msg, final int DialogIndex, final onOkMethodListener okListener, final onCancelMethodListener cancelListener){
		AlertOkCancel(atvt, alertTitle, msg, DialogIndex, BtnTitle.BTN_OK, okListener, BtnTitle.BTN_CANCEL, cancelListener, true);
	}
	
	public static void AlertOkCancel(Activity atvt, String title, String msg, final int DialogIndex, final onOkMethodListener okListener, final onCancelMethodListener cancelListener){				
		AlertOkCancel(atvt, title, msg, DialogIndex, BtnTitle.BTN_OK, okListener, BtnTitle.BTN_CANCEL, cancelListener, true);		
	}
	
	public static void AlertOkCancel(Activity atvt, String msg, final int DialogIndex, String okBtnName, final onOkMethodListener okListener, String cancelBtnName, final onCancelMethodListener cancelListener){
		AlertOkCancel(atvt, alertTitle, msg, DialogIndex, okBtnName, okListener, cancelBtnName, cancelListener, true);
	}
	
	public static void AlertOkCancel(Activity atvt, String title, String msg, final int DialogIndex, String okBtnName, final onOkMethodListener okListener, String cancelBtnName, final onCancelMethodListener cancelListener, boolean Cancelable){
		
		mParent = atvt;
		AlertDialog.Builder ad = getDialogCreate(atvt);

		ad.setTitle(title);
		ad.setMessage(msg);
		ad.setCancelable(Cancelable);
		ad.setNeutralButton(okBtnName, new DialogInterface.OnClickListener() {   
			public void onClick(DialogInterface dialog, int whichButton) {
				if(okListener != null) {
					try {
						okListener.onClickAlertOkButton(DialogIndex);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		ad.setNegativeButton(cancelBtnName, new DialogInterface.OnClickListener() {   
			public void onClick(DialogInterface dialog, int whichButton) {
				if(cancelListener != null) {
					try {
						cancelListener.onClickAlertCancelButton(DialogIndex);
					} catch(Exception e) {
						e.printStackTrace();
					}
				} 
			}
		});
		AlertDialog dialog = ad.show();
		
		TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
		messageText.setGravity(Gravity.CENTER);
		
	}

	/**
	 * 알림메시지창을 활성화 한후, 버튼1메시지, 버튼2메시지, 클릭시 해당액티비티의 메소드를 호출한다. 
	 * @param atvt - 액티비티
	 * @param msg - 메시지
	 * @param okObject - 액티비티
	 * @param okMsg - 버튼1메시지
	 * @param noMsg - 버튼2메시지
	 * @param okMethod - 메소드명
	 * @param cancelMethod - 메소드명
	 */
	public static void AlertOkCancel(Activity atvt, String msg, Object okObject, String okMsg, String noMsg, String okMethod, String cancelMethod){
		mParent = atvt;
		mObject = okObject;
		mOkMethod = okMethod;
		mCancelMethod = cancelMethod;
		String okMessage =  okMsg;
		String noMessage =  noMsg;

		AlertDialog.Builder ad = getDialogCreate(atvt);

		ad.setTitle(alertTitle);
		ad.setMessage(msg);
		ad.setNeutralButton(okMessage, new DialogInterface.OnClickListener() {   
			public void onClick(DialogInterface dialog, int whichButton) {
				if(mOkMethod != null) {
					try {
						Class<?> cls = mObject.getClass();
						Method method = cls.getMethod(mOkMethod);
						method.invoke(mObject);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		ad.setNegativeButton(noMessage, new DialogInterface.OnClickListener() {   
			public void onClick(DialogInterface dialog, int whichButton) {
				if(mCancelMethod != null) {
					try {
						Class<?> cls = mObject.getClass();
						Method method = cls.getMethod(mCancelMethod);
						method.invoke(mObject);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		ad.show();
	}
	
	/**
	 * 알림메세지창을 활성화 한후, 확인버튼 클릭시 해당액티비티의 메소드를 호출한다
	 * 
	 * @param atvt - 액티비티
	 * @param msg - 알림메세지
	 * @param mtdnm - 메소드명
	 */
	public static void AlertOkCancel(Activity atvt, String msg, Object okObject, String okMethod, String cancelMethod){
		mParent = atvt;
		mObject = okObject;
		mOkMethod = okMethod;
		mCancelMethod = cancelMethod;

		AlertDialog.Builder ad = getDialogCreate(atvt);

		ad.setTitle(alertTitle);
		ad.setMessage(msg);
		ad.setNeutralButton(BtnTitle.BTN_OK, new DialogInterface.OnClickListener() {   
			public void onClick(DialogInterface dialog, int whichButton) {
				if(mOkMethod != null) {
					try {
						Class<?> cls = mObject.getClass();
						Method method = cls.getMethod(mOkMethod);
						method.invoke(mObject);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		ad.setNegativeButton(BtnTitle.BTN_CANCEL, new DialogInterface.OnClickListener() {   
			public void onClick(DialogInterface dialog, int whichButton) {
				if(mCancelMethod != null) {
					try {
						Class<?> cls = mObject.getClass();
						Method method = cls.getMethod(mCancelMethod);
						method.invoke(mObject);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		ad.show();
	}
	
	/**
	 * 알림메세지창을 활성화 한후, 확인버튼 클릭시 해당액티비티의 메소드를 호출한다
	 * 
	 * @param atvt - 액티비티
	 * @param msg - 알림메세지
	 * @param mtdnm - 메소드명
	 */
	public static void AlertOkCancelBackFalse(Activity atvt, String msg, Object okObject, String okMethod, String cancelMethod){
		mParent = atvt;
		mObject = okObject;
		mOkMethod = okMethod;
		mCancelMethod = cancelMethod;

		AlertDialog.Builder ad = getDialogCreate(atvt);

		ad.setTitle(alertTitle);
		ad.setCancelable(false);
		ad.setMessage(msg);
		ad.setNeutralButton(BtnTitle.BTN_OK, new DialogInterface.OnClickListener() {   
			public void onClick(DialogInterface dialog, int whichButton) {
				if(mOkMethod != null) {
					try {
						Class<?> cls = mObject.getClass();
						Method method = cls.getMethod(mOkMethod);
						method.invoke(mObject);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		ad.setNegativeButton(BtnTitle.BTN_CANCEL, new DialogInterface.OnClickListener() {   
			public void onClick(DialogInterface dialog, int whichButton) {
				if(mCancelMethod != null) {
					try {
						Class<?> cls = mObject.getClass();
						Method method = cls.getMethod(mCancelMethod);
						method.invoke(mObject);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		ad.show();
	}
	
	/**
	 * 알림메세지창을 활성화 한후, 확인버튼 클릭시 해당액티비티의 메소드를 호출한다
	 * 
	 * @param atvt - 액티비티
	 * @param msg - 알림메세지
	 * @param mtdnm - 메소드명
	 */
	public static void AlertAllOneCancel(Activity atvt, String msg, String allMethod, String oneMethod, String cancelMethod) {
		AlertAllOneCancel(atvt, msg, atvt, allMethod, oneMethod, cancelMethod);
	}
	
	public static void AlertAllOneCancel(Activity atvt, String msg, Object obj, String allMethod, String oneMethod, String cancelMethod){
		mParent = atvt;
		mObject = obj;
		mAllMethod = allMethod;
		mOneMethod = oneMethod;
		mCancelMethod = cancelMethod;

		AlertDialog.Builder ad = getDialogCreate(atvt);

		ad.setTitle(alertTitle);
		ad.setMessage(msg);
		ad.setPositiveButton(BtnTitle.REPEAT_ALL_BTN, new DialogInterface.OnClickListener() {   
			public void onClick(DialogInterface dialog, int whichButton) {
				if(mAllMethod != null) {
					try {
						Class<?> cls = mObject.getClass();
						Method method = cls.getMethod(mAllMethod);
						method.invoke(mObject);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		ad.setNeutralButton(BtnTitle.REPEAT_ONE_BTN, new DialogInterface.OnClickListener() {   
			public void onClick(DialogInterface dialog, int whichButton) {
				if(mOneMethod != null) {
					try {
						Class<?> cls = mObject.getClass();
						Method method = cls.getMethod(mOneMethod);
						method.invoke(mObject);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		ad.setNegativeButton(BtnTitle.BTN_CANCEL, new DialogInterface.OnClickListener() {   
			public void onClick(DialogInterface dialog, int whichButton) {
				if(mCancelMethod != null) {
					try {
						Class<?> cls = mObject.getClass();
						Method method = cls.getMethod(mCancelMethod);
						method.invoke(mObject);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		ad.show();
	}

	
	/**
	 * 알림메세지창을 활성화 한후, 확인버튼 클릭시 해당액티비티를 종료한다
	 * 
	 * @param atvt
	 *            - 액티비티
	 * @param msg
	 *            - 알림메세지
	 */
	public static void AlertAfterFinish(Activity atvt, String msg) {
		mParent = atvt;

		AlertDialog.Builder ad = getDialogCreate(atvt);

		ad.setTitle(alertTitle);
		ad.setMessage(msg);
		ad.setCancelable(false);
		ad.setNeutralButton(BtnTitle.BTN_OK,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				mParent.finish();
			}
		});
		ad.show();
	}

	public static void Error(Activity atvt, BizException e) {
		PrintLog.printException(atvt, e.getErrMsg(), e);
		String var = e.getStackTrace()[0].toString();
		Error(atvt, e.getErrMsg(), var);
	}
	
	public static void Error(Activity atvt, String msg, Exception e) {
		PrintLog.printException(atvt, msg, e);
		e.printStackTrace();
		
		if (BuildConfig.DEBUG ) {
			
			LogUtil.sendLogToServer(ComUtil.getPhoneNumber(atvt), e);
			
		}
		
		if (  mLogSave ) {
			String errMsg = e.getLocalizedMessage() + "\n\r";
			errMsg += e.getMessage();
			for(int i = 0; i < e.getStackTrace().length; i++) {
				errMsg += e.getStackTrace()[i] + "\n\r";
			}
			
			String path = Environment.getExternalStorageDirectory() + "/WEBCASH";
			File logFile = new File(path);
			if (!logFile.exists()) {
				logFile.mkdir();
			}
			
			String fileName = "/" +Convert.ComDate.today() + "_error.log";
			logFile = new File(path + fileName);
			if (!logFile.exists())
			{
		      try
		      {
		         logFile.createNewFile();
		      } 
		      catch (IOException exp)
		      {
		    	  exp.printStackTrace();
		      }
		   }
		   try
		   {
		      //BufferedWriter for performance, true to set append to file flag
		      BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true)); 		      
		      buf.append("\n\r\n\r" + Convert.ComDate.currDateTimeMillisend() + "\n\r");
		      buf.append(errMsg);
		      buf.newLine();
		      buf.close();
		   }
		   catch (IOException exp)
		   {
			   exp.printStackTrace();
		   }
						
		}
		
		
		String var = e.getStackTrace()[0].toString();
		Error(atvt, msg, var);
	}
	
	public static void Error(Activity atvt, String msg, String... vargs) {
		mParent = atvt;

		for (String var : vargs) {
			msg = msg.replaceFirst(regularExpr, var);
		}
		
		AlertDialog.Builder ad = getDialogCreate(atvt);
		
		ad.setTitle(errorTitle).setMessage(msg)
		.setNeutralButton(BtnTitle.BTN_OK,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
					int whichButton) {
			}
		}).show();
		
	}


	// 이체 실거래시 오류일경우 메시지 출력후 이체 첫화면으로 이동한다.
	public static void tranError(Activity atvt, String msg)
	{
//		mParent = atvt;
//		AlertDialog.Builder ad = new AlertDialog.Builder(mParent);
//		ad.setTitle("경고");
//		ad.setMessage(msg);
//		ad.setCancelable(false);
//		ad.setNeutralButton(android.R.string.ok,
//			new DialogInterface.OnClickListener() {   
//				public void onClick(DialogInterface dialog, int whichButton) {
//					Intent intent;
//					intent = new Intent(mParent, Rmt_0101.class);
//					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);		 
//					mParent.startActivity(intent);
//				}
//			})
//		.show();
	}
}
