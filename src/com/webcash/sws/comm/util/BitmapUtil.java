package com.webcash.sws.comm.util;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

public class BitmapUtil {

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
	

	/**
	 * 비트맵 이미지 리사이즈
	 * @param width
	 * @param height
	 * @return
	 */
	public static int getReductInSampleSize(int width, int height)	{
		int	ret = 1;

		if( width >= 1024 && width < 2048 || height >= 1024 && height < 2048 )	{
			ret = 2;
		} else if ( width >= 2048 && width < 4096 || height >= 2048 && height < 4096 )	{
			ret = 4;
		}  else if (width >= 4096 || height >= 4096){
			ret = 8;
		}
		return	ret;
	}
	
	/**
	 * 비트맵을 메모리에 올리지 않고  가로길이를 가져옴
	 * @param fileName 
	 * @return 비트맵의 가로길이
	 */
	public static int getBitmapOfWidth( String fileName ){ 
		try { 
			BitmapFactory.Options options = new BitmapFactory.Options(); 
			options.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(fileName, options); 
			return options.outWidth; 
		} catch(Exception e) { 
			return 0; 
		} 
	} 

	/**
	 * 비트맵을 메모리에 올리지 않고 세로길이를 가져옴
	 * @param fileName 
	 * @return 비트맵의 세로길이
	 */
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

	/**
	 * 서클 ( O ) 모양으로 이미지를 자른다.
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getBitmapClippedCircle(Drawable drawable) {
		return getBitmapClippedCircle( drawableToBitmap(drawable) );
	}
	public static Bitmap getBitmapClippedCircle(Bitmap bitmap) {

        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        
        return getBitmapClippedCircle(bitmap, width, height);        
    }
	
	public static Bitmap getBitmapClippedCircle(Bitmap bitmap, int width, int height) {
		
        final Bitmap outputBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);

        final Path path = new Path();
        path.addCircle(
                  (float)(width / 2)
                , (float)(height / 2)
                , (float) Math.min(width, (height / 2))
                , Path.Direction.CCW);

        final Canvas canvas = new Canvas(outputBitmap);
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0, null);
        return outputBitmap;
    }
	
	public static Bitmap getBitmapCenterCropCircle(Bitmap bitmap, int width, int height) throws Exception {
		Bitmap bmp = scaleCenterCrop(bitmap, height, width);
		return getBitmapClippedCircle(bmp);
	}
	
	/**
	 * 모서리가 동그랗게 이미지를 자른다.
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);
	 
	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    final RectF rectF = new RectF(rect);
	    final float roundPx = 12;
	 
	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
	 
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);
	 
	    return output;
	}
	
	
	public static Bitmap decodeScaledBitmapFromSdCard(String filePath, int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(filePath, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(filePath, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {

	        // Calculate ratios of height and width to requested height and width
	        final int heightRatio = Math.round((float) height / (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);

	        // Choose the smallest ratio as inSampleSize value, this will guarantee
	        // a final image with both dimensions larger than or equal to the
	        // requested height and width.
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }

	    return inSampleSize;
	}


	/**
	 * Drawable을 Bitmap 으로 변환
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap (Drawable drawable) {
	    if (drawable instanceof BitmapDrawable) {
	        return ((BitmapDrawable)drawable).getBitmap();
	    }

	    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap); 
	    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	    drawable.draw(canvas);

	    return bitmap;
	}
	
	
	/** 이미지를 회전시킵니다. *  
	 *  @param bitmap 비트맵 이미지
	 *  @param degrees 회전 각도
	 *  @return 회전된 이미지 
	 */
	public static Bitmap rotate(Bitmap bitmap, int degrees){
		if(degrees != 0 && bitmap != null){
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) bitmap.getWidth()/2, (float) bitmap.getHeight()/2);
			try{
				Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,  bitmap.getWidth(), bitmap.getHeight(), m, true);
				if(bitmap != converted){
					bitmap.recycle();
					bitmap = converted;
				}
			}
			catch(OutOfMemoryError ex){
				// 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
			}
		}
		return bitmap;
	}
	
	 
	 /**
	 * 
	 * scaleCenterCrop
	 * 
	 * 이미지의 중앙을 기준으로 원하는 사이즈로 잘라서 Bitmap 으로 반환한다.
	 * 
	 * @param source : 원본이미지
	 * @param newHeight : 원하는 이미지사이즈의 높이 (px)
	 * @param newWidth : 원하는 이미지사이즈의 너비 (px)
	 * @return 원하는 사이즈로 중앙을 중심으로 잘린 이미지
	 * 
	 * @see  http://stackoverflow.com/questions/8112715/how-to-crop-bitmap-center-like-imageview
	 * 
	 */
	 public static Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) throws Exception {
	    int sourceWidth = source.getWidth();
	    int sourceHeight = source.getHeight();

	    // Compute the scaling factors to fit the new height and width, respectively.
	    // To cover the final image, the final scaling will be the bigger 
	    // of these two.
	    float xScale = (float) newWidth / sourceWidth;
	    float yScale = (float) newHeight / sourceHeight;
	    float scale = Math.max(xScale, yScale);

	    // Now get the size of the source bitmap when scaled
	    float scaledWidth = scale * sourceWidth;
	    float scaledHeight = scale * sourceHeight;

	    // Let's find out the upper left coordinates if the scaled bitmap
	    // should be centered in the new size give by the parameters
	    float left = (newWidth - scaledWidth) / 2;
	    float top = (newHeight - scaledHeight) / 2;

	    // The target rectangle for the new, scaled version of the source bitmap will now
	    // be
	    RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

	    // Finally, we create a new bitmap of the specified size and draw our new,
	    // scaled bitmap onto it.
	    Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
	    Canvas canvas = new Canvas(dest);
	    canvas.drawBitmap(source, null, targetRect, null);

	    return dest;
	}

}
