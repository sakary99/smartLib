package com.webcash.sws.comm.ui;

import java.lang.reflect.Field;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.webcash.sws.comm.debug.PrintLog;
 
/**
 * 
 * A TextView that only click to expand to show full text and click to show shorten text,
 * The ellipsis can be configured too.
 * 
 * @author Wat Chun Pang Gilbert
 *
 */
public class ExpandableTextView extends TextView {
  
	private String mEllipsis = "\u2026\u25BC";
 
	private static final String TAG = ExpandableTextView.class.getSimpleName();
	
	private Boolean mIsExpanded;
	private Integer mMaxLine;
	private CharSequence mOriginalText;
	
	private static final String sMaximumVarName = "mMaximum";
	
	
	/**
	 * @param context
	 */
	public ExpandableTextView(Context context) {
		super(context);
		init();
	}
	
	/**
	 * @param context
	 * @param attrs
	 */
	public ExpandableTextView(Context context, AttributeSet attrs) {
		super(context,attrs);
		init();
	}
	
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
 
	private void init() {
		expand();
		collapse();
		super.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final ExpandableTextView etv = (ExpandableTextView)v;
				toggle(etv);
			}
		});
	}
	
	/**
	 * @return is expanded or not
	 */
	protected Boolean isExpanded() {
		return mIsExpanded;
	}
 
 
	@Override
	public void setOnClickListener(OnClickListener l) {
		PrintLog.printSingleLog(TAG, "operation is not supported!");
	}
 
	/**
	 * Toggle if it is expanded or not
	 */
	public final void toggle() {
		toggle(this);
	}
 
 
	private final void toggle(ExpandableTextView etv) {
		if (etv.isExpanded()) {
			PrintLog.printSingleLog(TAG, TAG + " is collapsed.");
			etv.collapse();
		} else {
			PrintLog.printSingleLog(TAG, TAG + " is expanded.");
			etv.expand();
		}
	}
	
	public void collapse() {
		mIsExpanded = false;
		setMaxLines(mMaxLine);
		postInvalidate();
		//The actual change is in onMeasure 
	}
	
	public void expand() {
		mIsExpanded = true;
		if (mMaxLine == null) {
			storeMaxLine();
		}
		
		setEllipsize(null);
		setMaxLines(Integer.MAX_VALUE);
		setText(mOriginalText);
		postInvalidate();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		if (!mIsExpanded && null != mOriginalText) {
			final String sTruncatedText = TextUtils.ellipsize(mOriginalText.toString().replace('\n', ' ').replace('\t', ' '), getPaint(), 
					(float) (getRight() - getLeft() - getCompoundPaddingLeft() - getCompoundPaddingRight() - getPaint().measureText(mEllipsis)) * mMaxLine,
					TextUtils.TruncateAt.END).toString();
			if (sTruncatedText.length() > 2 && !sTruncatedText.equals(mOriginalText)) {
				SpannableStringBuilder ssb = new SpannableStringBuilder(sTruncatedText.trim(), 0, sTruncatedText.length() - 2);
				
				ssb.append(mEllipsis);
				setText(ssb.toString());
			}
		}
	}
 
	/**
	 * Extract private maxLine from super class
	 */
	private void storeMaxLine() {
		Field f;
		try {
			f = getClass().getSuperclass().getDeclaredField(sMaximumVarName);
			f.setAccessible(true);
			mMaxLine = f.getInt(this);
		
			f.setAccessible(false);
		} catch (SecurityException e) {
			//impossible
			PrintLog.printSingleLog(TAG, e.getMessage());
		} catch (NoSuchFieldException e) {
			//impossible
			PrintLog.printSingleLog(TAG, e.getMessage());
		} catch (IllegalArgumentException e) {
			//impossible
			PrintLog.printSingleLog(TAG, e.getMessage());
		} catch (IllegalAccessException e) {
			//impossible
			PrintLog.printSingleLog(TAG, e.getMessage());
		}
	}
	
	/**
	 * Reset the text
	 * @param text
	 */
	public void resetText(CharSequence text) {
		mOriginalText = null;
		setText(text);
	}
 
	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(text, type);
		if (mOriginalText == null) {
			mOriginalText = text;
		}
	}
	
	/**
	 * Set the ellipsis of the TextView
	 * @param ellipsis
	 */
	public void setEllipsis(final String ellipsis) {
		this.mEllipsis = ellipsis;
	}
	
	/**
	 * @return the ellipsis
	 */
	public String getEllipsis() {
		return this.mEllipsis;
	}
 
}