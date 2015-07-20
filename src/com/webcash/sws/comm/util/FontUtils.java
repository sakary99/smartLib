package com.webcash.sws.comm.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FontUtils {
	
	
	
	/**
	 * 전체 TextView를 찾아 폰트를 설정한다.
	 * @param context
	 * @param rootGroup
	 */
	public static void setTypeface(final Context context, String fontName, ViewGroup rootGroup) {
		
		for(int i = 0; i < rootGroup.getChildCount(); i++) {
			View view = rootGroup.getChildAt(i);
			if (view instanceof TextView)				
				setTypeface(context, (TextView)view, fontName, false);
			else if (view instanceof Button)
				setTypeface(context, (Button)view, fontName, false);
			else if (view instanceof ViewGroup )
				setTypeface(context, (ViewGroup)view );
		}
	}
	
	/**
	 * 전체 TextView를 찾아 Tag에 String으로 저장한 폰트로 설정한다.
	 * @param context
	 * @param rootGroup
	 */
	public static void setTypeface(final Context context, ViewGroup rootGroup) {
		
		for(int i = 0; i < rootGroup.getChildCount(); i++) {
			View view = rootGroup.getChildAt(i);
			if (view instanceof TextView && view.getTag() != null)				
				setTypeface(context, (TextView)view, view.getTag().toString(), false);
			else if (view instanceof ViewGroup )
				setTypeface(context, (ViewGroup)view );
		}
	}	

	
	/**
	 * TextView를 해당하는 폰트로 적용한다.
	 */
	public static void setTypeface(Context context, TextView textview, String typefaceName, boolean isBold) {

		
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), typefaceName);
		if ( typeface == null ) 
			return;
		
		if (isBold) {
			textview.setTypeface(typeface, Typeface.BOLD);
		} else {
			textview.setTypeface(typeface);
		}
		
	}
	

}
