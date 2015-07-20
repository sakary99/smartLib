package com.webcash.sws.comm.extras.biz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.webcash.sws.comm.extras.Extras;

public class Extra_Cfg extends Extras {
	
	private static final String BACKUPPASSWORD	= "BACKUPPASSWORD";	// 백업패스워드

	public _Param Param = new _Param();

	public Extra_Cfg(Activity atvt) {
		super(atvt);
	}

	public Extra_Cfg(Activity atvt, Intent intent) {
		super(atvt, intent);
	}

	public Extra_Cfg(Activity atvt, Bundle bundle) {
		super(atvt, bundle);
	}

	public class _Param {		
		
		/**
		 * 백업패스워드
		 * @param value
		 */
		public void setBackupPassword(String value) {
			setString(BACKUPPASSWORD, value);
		}

		/**
		 * 백업패스워드
		 * @return 
		 */
		public String getBackupPassword() {
			return getString(BACKUPPASSWORD);
		}
	}
}