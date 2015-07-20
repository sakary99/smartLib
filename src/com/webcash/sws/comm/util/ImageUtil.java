package com.webcash.sws.comm.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

public class ImageUtil {
	
	
	/** Get Bitmap's Width **/
	 public static int getBitmapOfWidth( String fileName ){
	    try {
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeFile(fileName, options);	// BitmapFactory.decodeFile("/sdcard/image.jpg");
	        return options.outWidth;
	    } catch(Exception e) {
	    return 0;
	    }
	 }
	 
	 /** Get Bitmap's height **/
	 public static int getBitmapOfHeight( String fileName ){
	  
	    try {
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeFile(fileName, options);
	  
	        return options.outHeight;
	    } catch(Exception e) {
	        return 0;
	   }
	 }
	
	
	/**
	 * 이미지 사이즈가 최대사이즈(2048)를 초과하는 경우 최대사이즈로 만들기 위해 축소되어야 하는 비율을 반환한다.
	 * @param width  : 이미지 가로 길이
	 * @param height : 이미지 세로 길이
	 * @return 축소비율을 소수점으로 반환하며, 축소되지 않는 경우 1을 반환한다.
	 */
	public static double getMaxTextureResizeRate(int width, int height) {
		final int BITMAP_TEXTURE_MAX = 2048;
		return getMaxTextureResizeRate(width, height, BITMAP_TEXTURE_MAX);		
	}
	
	/**
	 * 이미지 사이즈가 지정한 사이즈를 초과하는 경우 지정한 사이즈로 만들기 위해 축소되어야 하는 비율을 반환한다.
	 * @param width  : 이미지 가로 길이
	 * @param height : 이미지 세로 길이
	 * @param maxSize : 최대사이즈
	 * @return 축소비율을 소수점으로 반환하며, 축소되지 않는 경우 1을 반환한다.
	 */
	public static double getMaxTextureResizeRate(int width, int height, int maxSize) {
		
		double horizontalOverRate = width > maxSize? (width - maxSize) / (double)width : 0;
		double verticalOverRate   = height > maxSize? (height - maxSize) / (double)height : 0;
		double resizeRate = 1;
		if (horizontalOverRate == 0 && verticalOverRate == 0)
			return 1;
		else if (horizontalOverRate > verticalOverRate)
			resizeRate = horizontalOverRate;
		else
			resizeRate = verticalOverRate;
		
		return resizeRate;		
	}
	
	public static Bitmap getImageResize(Bitmap bitmap, double rate) {
		if (rate >= 1) {
			return bitmap;
		} else {
			
			int dstWidth = (bitmap.getWidth() - (int)Math.round(bitmap.getWidth() * rate));
			int dstHeight = (bitmap.getHeight() - (int)Math.round(bitmap.getHeight() * rate));
			
			return Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, true);
		}
	}
	
	public static Bitmap getLastImageOfMediaStore(Context context) {
		
		final String[] IMAGE_PROJECTION = { MediaStore.Images.ImageColumns.DATA, 
											MediaStore.Images.Thumbnails.DATA };
		
		final Uri uriImages = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;        
		//final Uri uriImagesthum = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
		try{
			final Cursor cursorImages = context.getContentResolver().query(uriImages, IMAGE_PROJECTION, null, null, null);
			if(cursorImages != null && cursorImages.moveToLast()){         
				String strDateTop = cursorImages.getString(0);
				cursorImages.close();
		  
				File file = new File(strDateTop);
				Uri uri = Uri.fromFile(file);
				return Images.Media.getBitmap(context.getContentResolver(), uri);
			} 
			return null;
		 }catch(Exception e){
			 return null;
		 }
	}
}
