package com.webcash.sws.comm.picture;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import com.webcash.sws.comm.debug.PrintLog;
import com.webcash.sws.comm.util.Base64;
import com.webcash.sws.comm.util.BitmapUtil;

public class PictureUtil {
	
	private static Uri mCameraCaptureUri;
	private static Activity mActivity;
	
	/**
	 * 카메라에서 이미지 가져오기 (thumnail image로 반환함) <p>
	 * startActivityForResult로 호출하기 위해 Activity를 전달 받기 때문에 Fragment는 부모Activity로 응답이 간다. <p>
	 * Fragment 사용 불가
	 */
	public static void doTakePhotoAction(Activity activity) {
		mActivity = activity;		
		mCameraCaptureUri = PictureConf.getCameraCaptureUri();
		doTakePhotoAction(activity, mCameraCaptureUri);		
	}
	
	
	/**
	 * 카메라에서 이미지 가져오기 <p>
	 * startActivityForResult로 호출하기 위해 Activity를 전달 받기 때문에 Fragment는 부모Activity로 응답이 간다. <p>
	 * Fragment 사용 불가
	 */
	public static void doTakePhotoAction(Activity activity, Uri uri) {
		// (Bitmap) data.getExtras().get("data") 은 섬네일을 가져오므로, 
		// ACTION_IMAGE_CAPTURE로 인텐트 호출시 output을 미리 정의하고 해당 이미지 파일을 불러와야 한다.
		mActivity = activity;
		mCameraCaptureUri = uri;
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraCaptureUri);		
		activity.startActivityForResult(takePictureIntent, PictureConf.ReqCd.PICTURE.CAMERA);
	}
	  
	/**
	 * 앨범에서 이미지 가져오기
	 * startActivityForResult로 호출하기 위해 Activity를 전달 받기 때문에 Fragment는 부모Activity로 응답이 간다. <p>
	 * Fragment 사용 불가
	 */
	public static void doTakeAlbumAction(Activity activity) {
		mActivity = activity;
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		activity.startActivityForResult(intent, PictureConf.ReqCd.PICTURE.GALLERY);		
	}


	/**
	 * 카메라로 사진을 찍은 후 이미지를 축소시켜 비트맵으로 반환한다. <p>
	 * Activity에서 호출시
	 */
	public static Bitmap getBitmapByCameraAtActivity(Intent intent) {
		return getBitmapByCamera(intent, mCameraCaptureUri, PictureConf.IMG_MAX_WIDTH);
	}
	
	/**
	 * 카메라로 사진을 찍은 후 이미지를 축소시켜 비트맵으로 반환한다. <p>
	 * Activity에서 호출시
	 */
	public static Bitmap getBitmapByCameraAtActivity(Intent intent, int img_max_width) {
		return getBitmapByCamera(intent, mCameraCaptureUri, img_max_width);
	}
	
	/**
	 * 카메라로 사진을 찍은 후 이미지를 축소시켜 비트맵으로 반환한다. <p>
	 * Fragment에서 호출시
	 */
	public static Bitmap getBitmapByCameraAtFragment(Intent intent, Uri fileUri) {
		return getBitmapByCamera(intent, fileUri, PictureConf.IMG_MAX_WIDTH);
	}
	
	/**
	 * 카메라로 사진을 찍은 후 이미지를 축소시켜 비트맵으로 반환한다. <p>
	 * Fragment에서 호출시
	 */
	public static Bitmap getBitmapByCameraAtFragment(Intent intent, Uri fileUri, int img_max_width) {
		return getBitmapByCamera(intent, fileUri, img_max_width);
	}
	
	/**
	 * 카메라로 사진을 찍은 후 이미지를 축소시켜 비트맵으로 반환한다.
	 */
	private static Bitmap getBitmapByCamera(Intent intent, Uri fileUri, int img_max_width) {
		try {
			
			// (Bitmap) data.getExtras().get("data") 은 섬네일을 가져온다.
			
			/* So pre-scale the target bitmap into which the file is decoded */
			BitmapFactory.Options bmOptions = getBitmapFactoryOptionValue(fileUri.getPath(), img_max_width);
			
			/* Decode the JPEG file into a Bitmap */
			Bitmap captureBmp = BitmapFactory.decodeFile(fileUri.getPath(), bmOptions);
			
			// 최대 허용사이즈로 리사이즈
			double resizeRate = BitmapUtil.getMaxTextureResizeRate(captureBmp.getWidth(), captureBmp.getHeight(), img_max_width);
			Bitmap resizeBitmap = BitmapUtil.getImageResize(captureBmp, resizeRate);
			if (captureBmp.isRecycled()) {
				captureBmp.recycle();
			}
			
			/* Bitmap Rotate */			
			ExifInterface exif = new ExifInterface(fileUri.getPath());
			int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			int exifDegree = exifOrientationToDegrees(exifOrientation);
			
			resizeBitmap = BitmapUtil.rotate(resizeBitmap, exifDegree);
			
			return resizeBitmap;
			
		} catch (Exception e) {
			e.printStackTrace();
			PrintLog.printSingleLog("dilky", "getBitmapByCamera ---> " + e.getMessage());
			return null;
		}
	}

	/**
	 * 갤러리에서 사진을 선택 후 Intent로 전달된 데이타를 비트맵으로 반환난다.<p>
	 * (Activity 에서 호출시)
	 */
	public static Bitmap getBitmapByGalleryAtActivity(Uri uri) {
		return getBitmapByGallery(mActivity, uri, PictureConf.IMG_MAX_WIDTH);
	}
	
	/**
	 * 갤러리에서 사진을 선택 후 Intent로 전달된 데이타를 비트맵으로 반환난다.<p>
	 * (Activity 에서 호출시)
	 */
	public static Bitmap getBitmapByGalleryAtActivity(Uri uri, int img_max_width) {
		return getBitmapByGallery(mActivity, uri, img_max_width);
	}

	/**
	 * 갤러리에서 사진을 선택 후 Intent로 전달된 데이타를 비트맵으로 반환난다. <p>
	 * (Fragment 에서 호출시)
	 */
	public static Bitmap getBitmapByGalleryAtFragment(Activity activity, Uri uri) {
		return getBitmapByGallery(activity, uri, PictureConf.IMG_MAX_WIDTH);
	}	
	
	/**
	 * 갤러리에서 사진을 선택 후 Intent로 전달된 데이타를 비트맵으로 반환난다. <p>
	 * (Fragment 에서 호출시)
	 */
	public static Bitmap getBitmapByGalleryAtFragment(Activity activity, Uri uri, int img_max_width) {
		return getBitmapByGallery(activity, uri, img_max_width);
	}	

	/**
	 * 갤러리에서 사진을 선택 후 Uri에 해당되는 사진 데이타를 비트맵으로 반환한다.
	 */
	public static Bitmap getBitmapByGallery(Activity activity, Uri uri, int img_max_width) {
		try {
			
			String photoPath = getPath(activity, uri);
	
			/* There isn't enough memory to open up more than a couple camera photos */
			/* So pre-scale the target bitmap into which the file is decoded */
			BitmapFactory.Options bmOptions = getBitmapFactoryOptionValue(photoPath, img_max_width);
	
			/* Decode the JPEG file into a Bitmap */
			Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
			
			// 최대 허용사이즈로 리사이즈
			double resizeRate = BitmapUtil.getMaxTextureResizeRate(bitmap.getWidth(), bitmap.getHeight(), img_max_width);
			Bitmap resizeBitmap = BitmapUtil.getImageResize(bitmap, resizeRate);
			if (bitmap.isRecycled()) {
				bitmap.recycle();
			}
			
			/* Bitmap Rotate */
			ExifInterface exif = new ExifInterface(photoPath);
			int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			int exifDegree = exifOrientationToDegrees(exifOrientation);
			resizeBitmap = BitmapUtil.rotate(resizeBitmap, exifDegree);
			
			/* Associate the Bitmap to the ImageView */
			return resizeBitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	
	private static BitmapFactory.Options getBitmapFactoryOptionValue(String photoPath, int img_max_width) throws Exception {
		
		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(photoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;
		
		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		scaleFactor = Math.min(photoW/img_max_width, photoH/img_max_width);

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;
		
		return bmOptions;
	}
	
	
	/** 이미지의 회전각도로 변환하는 메서드 <p>
	 *  {@code}
	 *   ExifInterface exif = new ExifInterface(photoFilePath); <p>
	 *   int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
	 * 	@param exifOrientation EXIF 회전각 
	 *	@return 실제 각도 
	 */
	public static int exifOrientationToDegrees(int exifOrientation){
		if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
			return 90;
		}else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
			return 180;
		}else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270){
			return 270;
		}
		return 0;
	}
	
	private static String getPath(Activity activity, Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	
	/**
	 * Bitmap을 Base64로 인코딩하여 문자열로 반환한다.
	 * @param bitmap : Base64로 인코딩할 이미지
	 * @return : Base64인코딩한 결과
	 */
	public static String getBase64EncodeByBitmap(Bitmap bitmap) {
		if (bitmap ==  null)
			return "";
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, PictureConf.BITMAP_COMPRESS_QUALITY, baos);
			byte[] image = baos.toByteArray();
			String encoded = Base64.encodeBytes(image);
			return encoded;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	/**
	 * 이미지 크롭하는 화면을 호출한다.
	 * @param activity
	 * @param imageCaptureUri
	 */
	public static void startActivityCameraCrop(Activity activity, Uri imageCaptureUri) {
		
		// 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
        // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
  
		Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageCaptureUri, "image/*");
  
        intent.putExtra("outputX", 360);
        intent.putExtra("outputY", 360);
        intent.putExtra("aspectX", 90);
        intent.putExtra("aspectY", 90);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, PictureConf.ReqCd.PICTURE.CROP);
		
	}
	
	
	/**
	 *  Uri 를 File Path 로 변환하기 ( Uri -> Path )
	 */
	public String getPathFromUri(Context context, Uri uri) throws Exception {
		Cursor c = context.getContentResolver().query(uri, null, null, null, null );
		c.moveToNext(); 
		String path = c.getString( c.getColumnIndex( "_data" ) );
		c.close();

		return path;
	}
	
	/**
	 *  File Path 를 Uri 로 변환하기 ( Path -> Uri )
	 * @param context
	 * @param path : file:///storage/emulated/0/DCIM/Camera/20141030143024.png 와 같은 형태 
	 */
	public static Uri getUriFromPath(Context context, String path) throws Exception {
		//String path= "file:///sdcard/DCIM/Camera/2013_07_07_12345.jpg";
		Uri fileUri = Uri.parse( path );
		String filePath = fileUri.getPath();
		Cursor c = context.getContentResolver().query( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
		null, "_data = '" + filePath + "'", null, null );
		c.moveToNext();
		int id = c.getInt( c.getColumnIndex( "_id" ) );
		Uri uri = ContentUris.withAppendedId( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id );
	
		return uri;
	}
	
}
