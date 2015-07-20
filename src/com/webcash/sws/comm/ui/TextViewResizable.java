package com.webcash.sws.comm.ui;

import android.R;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;
import android.widget.TextView.BufferType;


/**
 * 
 * @author Administrator
 * {@code tv = (TextView) findViewById(R.id.tv);
        makeTextViewResizable(tv, 3, "View More", true);}
 */
public class TextViewResizable {
	
	
	private String mExpandText = "";
	private String mCollapseText = "";
	
	public void setCollapseText(String collapseText) {
		mCollapseText = collapseText;
	}
	
	private onTextViewClickableSpanListener mTextViewClickableSpan;
	public void setOnTextViewClickableSpanListener(onTextViewClickableSpanListener listener) {
		mTextViewClickableSpan = listener;
	}
	
	public interface onTextViewClickableSpanListener {
		public void onTextViewClickableSpan( boolean viewMore);
	}

	public void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

		
        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
            	try {
	                ViewTreeObserver obs = tv.getViewTreeObserver();
	                obs.removeGlobalOnLayoutListener(this);
	                if (maxLine == 0) {
	                    int lineEndIndex = tv.getLayout().getLineEnd(0);
	                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
	                    tv.setText(text);
	                    tv.setMovementMethod(LinkMovementMethod.getInstance());
	                    tv.setText(
	                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
	                                    viewMore), BufferType.SPANNABLE);
	                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
	                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
	                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
	                    tv.setText(text);
	                    tv.setMovementMethod(LinkMovementMethod.getInstance());
	                    tv.setText(
	                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
	                                    viewMore), BufferType.SPANNABLE);
	                }
	
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
            }
        });

    }
	
	

    private SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {

                	if (mTextViewClickableSpan != null) {
                    	mTextViewClickableSpan.onTextViewClickableSpan(viewMore);
                    }
                	
                	tv.setLayoutParams(tv.getLayoutParams());
                	tv.setText(tv.getTag().toString(), BufferType.SPANNABLE);
	                tv.invalidate();
	                makeTextViewResizable(tv, -1, mCollapseText, false);
                	
                	
//                    if (viewMore) {
//                        tv.setLayoutParams(tv.getLayoutParams());
//                        tv.setText(tv.getTag().toString(), BufferType.SPANNABLE);
//                        tv.invalidate();
//                        makeTextViewResizable(tv, -1, mCollapseText, false);                        
//                    } else {
//                        tv.setLayoutParams(tv.getLayoutParams());
//                        tv.setText(tv.getTag().toString(), BufferType.SPANNABLE);
//                        tv.invalidate();
//                        makeTextViewResizable(tv, 5, mExpandText, true);
//                    }
                    

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;
    }

	
	
	
//	private static String mExpandText = "";
//	private static String mCollapseText = "";
//	
//	public static void setCollapseText(String collapseText) {
//		mCollapseText = collapseText;
//	}
//
//	public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {
//
//		
//        if (tv.getTag() == null) {
//            tv.setTag(tv.getText());
//        }
//        ViewTreeObserver vto = tv.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
//
//            @SuppressWarnings("deprecation")
//            @Override
//            public void onGlobalLayout() {
//            	try {
//	                ViewTreeObserver obs = tv.getViewTreeObserver();
//	                obs.removeGlobalOnLayoutListener(this);
//	                if (maxLine == 0) {
//	                    int lineEndIndex = tv.getLayout().getLineEnd(0);
//	                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
//	                    tv.setText(text);
//	                    tv.setMovementMethod(LinkMovementMethod.getInstance());
//	                    tv.setText(
//	                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
//	                                    viewMore), BufferType.SPANNABLE);
//	                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
//	                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
//	                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
//	                    tv.setText(text);
//	                    tv.setMovementMethod(LinkMovementMethod.getInstance());
//	                    tv.setText(
//	                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
//	                                    viewMore), BufferType.SPANNABLE);
//	                }
//	
//            	} catch (Exception e) {
//            		e.printStackTrace();
//            	}
//            }
//        });
//
//    }
//	
//	
//
//    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
//            final int maxLine, final String spanableText, final boolean viewMore) {
//        String str = strSpanned.toString();
//        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);
//
//        if (str.contains(spanableText)) {
//            ssb.setSpan(new ClickableSpan() {
//
//                @Override
//                public void onClick(View widget) {
//
//                    if (viewMore) {
//                        tv.setLayoutParams(tv.getLayoutParams());
//                        tv.setText(tv.getTag().toString(), BufferType.SPANNABLE);
//                        tv.invalidate();
//                        makeTextViewResizable(tv, -1, mCollapseText, false);
//                    } else {
//                        tv.setLayoutParams(tv.getLayoutParams());
//                        tv.setText(tv.getTag().toString(), BufferType.SPANNABLE);
//                        tv.invalidate();
//                        makeTextViewResizable(tv, 5, mExpandText, true);
//                    }
//
//                }
//            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);
//
//        }
//        return ssb;
//    }

	
	
}
