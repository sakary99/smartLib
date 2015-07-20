/**
 * <pre>
 * 전문 레코드 클래스
 * @COPYRIGHT (c) 2010 WebCash, Inc. All Right Reserved.
 *
 * @author       : WebCash
 * @Description  : 
 * @History      : 
 *
 * </pre>
 **/

package com.webcash.sws.comm.tx;

import java.util.ArrayList;

public class TxRecord {
	protected ArrayList<TxField> mFields = new ArrayList<TxField>();
	
	/**
	 * 생성자
	 */
	public TxRecord() {}
	
	/**
	 * 전문필드객체를 반환한다.
	 * @param idx - 전문필드객체 인덱스
	 * @return 전문필드객체
	 */
	public TxField getField(int idx) {
		return mFields.get(idx);
	}
	
	/**
	 * 전문필드객체를 레코드에 추가한다.
	 * @param field - 전문필드객체
	 * @return 추가된 전문필드객체의 인덱스
	 */
	public int addField(TxField field) {
		int idx = mFields.size();
		mFields.add(idx, field);
		return idx;
	}
}
