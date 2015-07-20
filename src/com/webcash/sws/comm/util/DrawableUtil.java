package com.webcash.sws.comm.util;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.webcash.sws.comm.define.Msg;
import com.webcash.sws.comm.ui.DlgAlert;

public class DrawableUtil {

	public static class InnerMemory {
	
		/**
		 * 내부메모리에 저장한 이미지를 java 코드로 Selector기능을 표현한다.
		 * @param atvt : 액티비티
		 * @param view : 뷰
		 * @param imgNormalIcon : enabled, focused 이미지
		 * @param imgSelectedIcon : pressed 이미지
		 * @return
		 */
		public static boolean setSelector(Context context, View view, String imgNormalIcon, String imgSelectedIcon) {
			
			try {
					Drawable imgNormal = getDrawableByName((Activity)context, imgNormalIcon);
					Drawable imgDown = getDrawableByName((Activity)context, imgSelectedIcon);
					
					StateListDrawable drawable = new StateListDrawable();    
					drawable.addState(new int[] {android.R.attr.state_pressed, android.R.attr.state_enabled},imgDown);    
					drawable.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_enabled},imgNormal);   
					drawable.addState(new int[] {android.R.attr.state_enabled},imgNormal);
					
					view.setBackgroundDrawable(drawable);
					
					return true;
	
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}	
		
		/**
		 * 내부메모리에 저장된 이미지를 파일명으로 읽어와 배경이미지로 설정한다. 
		 * @param context
		 * @param btn : 배경이미지를 설정한 버튼
		 * @param imgNormalIcon : enabled, focused 이미지
		 * @param imgSelectedIcon : pressed 이미지
		 * @param text : 이미지가 없는 경우 텍스트로 메뉴명 표현
		 */
		public static void setSelector(Context context, Button btn, String imgNormalIcon, String imgSelectedIcon, String text) {
			
			try {
	
				Drawable imgNormal = getDrawableByName(context, imgNormalIcon);
				Drawable imgDown = getDrawableByName(context, imgSelectedIcon);	  
				
				if (imgNormal != null && imgDown != null) {
					StateListDrawable drawable = new StateListDrawable();    
					drawable.addState(new int[] {android.R.attr.state_pressed, android.R.attr.state_enabled},imgDown);    
					drawable.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_enabled},imgNormal);   
					drawable.addState(new int[] {android.R.attr.state_enabled},imgNormal);
					
					btn.setBackgroundDrawable(drawable);				
					
				} else {
					btn.setText(text);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		
		/**
		 * 내부메모리에 저장된 이미지를 파일명으로 StateListDrawable 로 반환한다.
		 * @param context
		 * @param imgNormalIcon
		 * @param imgSelectedIcon
		 * @return
		 */
		public static StateListDrawable getStateListDrawable(Context context, String imgNormalIcon, String imgSelectedIcon) {
			try {
	
				Drawable imgNormal = getDrawableByName(context, imgNormalIcon);
				Drawable imgDown = getDrawableByName(context, imgSelectedIcon);	  
				
				if (imgNormal != null && imgDown != null) {
					StateListDrawable drawable = new StateListDrawable();    
					drawable.addState(new int[] {android.R.attr.state_pressed, android.R.attr.state_enabled},imgDown);    
					drawable.addState(new int[] {android.R.attr.state_focused, android.R.attr.state_enabled},imgNormal);   
					drawable.addState(new int[] {android.R.attr.state_enabled},imgNormal);
					
					return drawable;
					
				} else {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		/**
		 * 내부메모리에 저장된 이미지파일을 Drawable로 반환한다.
		 * @param path
		 * @return
		 */
		public static Drawable getDrawable(String path)
		{
			try
			{
				File imgFile = new  File(path);
				if(imgFile.exists())
				{
		
				    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
				    
				    Drawable d = (Drawable)(new BitmapDrawable(bitmap));
				    
				    return d;
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return null;
		}
		
		public static Drawable getDrawableByName(Context context, String imgName)
		{
			try
			{
				String dir = ComUtil.getInternalMemroyPath(context) + "/";
				String filePath = dir + imgName + ".png";
				File imgFile = new  File(filePath);
				if(imgFile.exists()){
		
				    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
				    
				    Drawable d = (Drawable)(new BitmapDrawable(bitmap));
				    
				    return d;
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return null;
		}
		 
		 /**
		  * 내부메모리에 존재하는 이미지파일을 ImageView에 세팅한다.
		  * 이미지의 파일의 확장자는 반드시  png로 한다.
		  * 
		  * @param activity
		  * @param iv
		  * @param imgName
		  * @return
		  */
		 
		 public static boolean setImageResourceAtIM(Activity activity,ImageView iv, String imgName) 
		 {
	    	  
			 try
			 {
				 String dir = ComUtil.getInternalMemroyPath(activity) + "/";
					String filePath = dir + imgName + ".png";
					
				 	if(filePath !="")
				 	{
				 		Drawable d = getDrawable(filePath);
				 		iv.setImageDrawable(d);
				 		
				 		return true;
				 	}
				 	
					return false;		
			 }
			 catch(Exception e)
			 {
					DlgAlert.Error(activity, Msg.Exp.DEFAULT, e);
			 }
			 
			return false;
		 }
	}
}
