package com.webcash.sws.lib;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.webcash.sws.comm.ui.ExpandableTextView;
import com.webcash.sws.comm.ui.TextViewResizable;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ExpandableTextView etx = (ExpandableTextView)findViewById(R.id.etx_Test);
		
		final TextView tv = (TextView)findViewById(R.id.tx_Test);

		//TextViewResizable.makeTextViewResizable(tv, 5, "더보기", true);

	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
