package com.webcash.sws.comm.tran;

import android.os.AsyncTask;

public class WrapperAsyncCaller extends AsyncTask<WrapperAsyncParam, Integer, Object> {

	@Override
	protected Object doInBackground(WrapperAsyncParam... params) {
		WrapperAsyncParam aWrapperParam;
		if( (null != params)  && ( 1 == params.length ) )
		{
			aWrapperParam = params[0];
			try {
				return aWrapperParam.invoke();
			} catch (Exception e) {

			}
		}
		return null;
	}

}
