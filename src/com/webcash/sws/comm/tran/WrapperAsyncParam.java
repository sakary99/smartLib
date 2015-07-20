package com.webcash.sws.comm.tran;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WrapperAsyncParam {
	
	private Method mMethod;
	private Object[] mParams;
	private Object mReciever;
	
	public WrapperAsyncParam(Method aMethod, Object aReciever , Object... aParams)
	{
		mMethod = aMethod;
		mParams = aParams;
		mReciever = aReciever;
	}
	
	public Object invoke() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException 
	{
		mMethod.setAccessible(true);
		return mMethod.invoke(mReciever, mParams);
	}

}
