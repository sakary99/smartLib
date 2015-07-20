package com.webcash.sws.comm.util;

public class BizException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean mExitActivity;
	private int mErrorCode;
	private String mErrorMessage;

	public BizException(String errMsg) {
		this(false, 0, errMsg);
	}
	public BizException(int errCd, String errMsg) {
		this(false, errCd, errMsg);
	}

	public BizException(boolean exitActivity, int errCd, String errMsg) {
		mExitActivity = exitActivity;
		mErrorCode = errCd;
		mErrorMessage = errMsg;
	}

	public int getErrCd() {
		return mErrorCode;
	}

	public String getErrMsg() {
		return mErrorMessage;
	}

	public boolean isExitActivity() {
		return mExitActivity;
	}
}
