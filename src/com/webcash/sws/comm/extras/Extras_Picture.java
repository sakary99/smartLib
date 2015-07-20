package com.webcash.sws.comm.extras;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Extras_Picture extends Extras {
	public Extras_Picture(Context ctx) {
		super(ctx);
	}

	public Extras_Picture(Context ctx, Intent intent) {
		super(ctx, intent);
	}

	public Extras_Picture(Context ctx, Bundle bundle) {
		super(ctx, bundle);
	}

	private final String PHOTOUUIDLIST 			= "PHOTOUUIDLIST";			// 사진 URL 리스트
	private final String PHOTO_CURRENTITEM 		= "PHOTO_CURRENTITEM";		// 사진 URL 리스트 선택사진 포지션
	
	public _Param Param = new _Param();
	
	public class _Param {		
		
		/**
		 * 사진 URL 리스트
		 */
		public void setPHOTOUUIDLIST(ArrayList<String> value) {
			setList(PHOTOUUIDLIST, value);
		}
		public ArrayList<String> getPHOTOUUIDLIST() {
			return getList(PHOTOUUIDLIST);
		}
		

		/**
		 * 사진 URL 리스트 선택사진 포지션
		 */
		public void setPHOTO_CURRENTITEM(int value) {
			setInt(PHOTO_CURRENTITEM, value);
		}
		public int getPHOTO_CURRENTITEM() {
			return getInt(PHOTO_CURRENTITEM);
		}
		
	}
}
