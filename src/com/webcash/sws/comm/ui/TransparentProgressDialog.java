package com.webcash.sws.comm.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.webcash.sws.lib.R;

/*
 * private TransparentProgressDialog pd;
	private Handler h;
	private Runnable r;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		h = new Handler();
		pd = new TransparentProgressDialog(this, R.drawable.load_01);
		pd.setCancelable(true);
		pd.setCanceledOnTouchOutside(false);
		r =new Runnable() {
			@Override
			public void run() {
				if (pd.isShowing()) {
					pd.dismiss();
				}
			}
		};
		findViewById(R.id.the_button).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		pd.show();
		h.postDelayed(r,5000);
	}
	
	@Override
	protected void onDestroy() {
		h.removeCallbacks(r);
		if (pd.isShowing() ) {
			pd.dismiss();
		}
		super.onDestroy();
	}
 *
 */

public class TransparentProgressDialog extends Dialog {
	
	private ImageView iv;
	
	private boolean isCanceable = true;
	
	public TransparentProgressDialog(Context context, int resourceIdOfImage) {
		super(context, R.style.TransparentProgressDialog);
		
		createTransparentProgressDialog(context, "", resourceIdOfImage);
	}
	
	public TransparentProgressDialog(Context context, String message, int resourceIdOfImage) {
		super(context, R.style.TransparentProgressDialog);
		
		createTransparentProgressDialog(context, message, resourceIdOfImage);
	}
	
	public void setCancelable(boolean Canceable) {
		isCanceable = Canceable;
	}
	
	private void createTransparentProgressDialog(Context context, String message, int resourceIdOfImage) {
		
		WindowManager.LayoutParams wlmp = getWindow().getAttributes();
    	wlmp.gravity = Gravity.CENTER_HORIZONTAL;
    	getWindow().setAttributes(wlmp);
		setTitle(null);
		setCancelable(isCanceable);
		setCanceledOnTouchOutside(false);
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		iv = new ImageView(context);
		iv.setImageResource(resourceIdOfImage);
		layout.addView(iv, params);
		
		if (!TextUtils.isEmpty(message)) {
			TextView tv = new TextView(context);
			tv.setTextColor(Color.WHITE);
			tv.setTextSize(17);
			tv.setPadding(0, 50, 0, 0);
			tv.setShadowLayer(2, 2, 2, Color.BLACK);
			tv.setText(message);
			layout.addView(tv, params);
		}
		
		addContentView(layout, params);
		
	}
	
	@Override
	public void show() {
		super.show();
		RotateAnimation anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(1500);
		iv.setAnimation(anim);
		iv.startAnimation(anim);
	}
}
