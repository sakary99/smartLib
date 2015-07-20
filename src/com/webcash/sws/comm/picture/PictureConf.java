package com.webcash.sws.comm.picture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.net.Uri;
import android.os.Environment;

public class PictureConf {
	public static class ReqCd {
		
		public static class PICTURE {
			public static final int CAMERA 	= 9998;	// 카메라
			public static final int GALLERY = 9999;	// 갤러리
			public static final int CROP	= 9997;	// 사진자르기
		}		
	}
	
	/**
	 * 이미지 최대 사이즈
	 */
	public static final int IMG_MAX_WIDTH = 1024;
	
	/**
	 * Bitmap 이미지 압축률 <br>
	 * 범위 : 0-100 ( 0 : small size, 100 : max quality )
	 */
	public static int BITMAP_COMPRESS_QUALITY = 0; 
	
	/**
	 * 이미지 확장자명
	 */
	public static final String IMAGE_EXTENSION = ".png";
	
	
	public static Uri getCameraCaptureUri() {
		String fileName = Environment.getExternalStorageDirectory() + "/DCIM/Camera/"+ new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime())+ IMAGE_EXTENSION;
		return Uri.fromFile( new File(fileName));
	}
	
}
