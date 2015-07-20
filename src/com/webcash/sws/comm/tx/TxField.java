/**
 * <pre>
 * 전문 필드 클래스
 * @COPYRIGHT (c) 2010 WebCash, Inc. All Right Reserved.
 *
 * @author       : WebCash
 * @Description  : 
 * @History      : 
 *
 * </pre>
 **/

package com.webcash.sws.comm.tx;

public class TxField {
	/**
	 * 전문필드 타입정의
	 */
	public class DATATYPE {
		public static final int NUM 		= 1;
		public static final int TEXT 		= 2;
		public static final int DATE 		= 3;
		public static final int TIME 		= 4;
		public static final int DATETIME 	= 5;
		public static final int CODE 		= 6;
	}

	private String FieldId;
	private String FieldName;
	private int DataType;
	private int Length;
	private boolean NotNull;
	private String Remark;
	private String CodeValues[];

	/**
	 * 생성자
	 */
	public TxField(){}

	/**
	 * 생성자
	 * @param filedId - 필드ID
	 * @param fieldName - 필드명
	 * @param dataType - 필드타입
	 * @param length - 길이
	 * @param notNull - 필수여부
	 * @param remark - 비고
	 * @param codeValues - 유효코드값(필드타입이 코드일 경우)
	 */
	public TxField(String filedId, String fieldName, int dataType, int length, boolean notNull, String remark, String[] codeValues){
		FieldId = filedId;
		FieldName = fieldName;
		DataType = dataType;
		Length = length;
		NotNull = notNull;
		Remark = remark;
		CodeValues = codeValues;
	}
	
	/**
	 * 생성자
	 * @param filedId - 필드ID
	 * @param fieldName - 필드명
	 */
	public TxField(String filedId, String fieldName){
		FieldId = filedId;
		FieldName = fieldName;
	}
	
	/**
	 * 필드ID를 반환한다
	 * @return - 필드ID
	 */
	public String getId() {
		return FieldId;
	}

	public String getName() {
		return FieldName;
	}

	public int getType() {
		return DataType;
	}

	public int getLength() {
		return Length;
	}

	public boolean getNotNull() {
		return NotNull;
	}
	
	public String getRemark() {
		return Remark;
	}
	
	public String[] getCodeValues() {
		return CodeValues;
	}
}