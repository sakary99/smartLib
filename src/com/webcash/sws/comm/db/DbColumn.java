/**
 * <pre>
 * 단골 2.0
 * @COPYRIGHT (c) 2012 WebCash, Inc. All Right Reserved.
 *
 * @author       : WebCash
 * @Description  : 디비 공통 - 컬럼 모델
 * @History      :
 *
 * </pre>
 **/

package com.webcash.sws.comm.db;


/**
 * 디비 공통 - 컬럼 모델<BR>
 * 컬럼명, 타입, 길이, PK여부, NULL 여부 포함<BR>
 */
public class DbColumn {
	/**
	 * 컬럼 타입<BR>
	 * TYPE_INT - 숫자<BR>
	 * TYPE_TEXT - 텍스트<BR>
	 * TYPE_DATE - 날짜<BR>
	 * TYPE_AMT - 금액<BR>
	 * TYPE_AUTO - 자동채번<BR>
	 */
	public static final int TYPE_INT = 0;
	public static final int TYPE_TEXT = 1;
	public static final int TYPE_DATE = 2;
	public static final int TYPE_AMT = 3;
	public static final int TYPE_AUTO = 4; 

	private String name;
	private int type;
	private int length;
	private boolean pk;
	private boolean notNull;

	public DbColumn(){}

	public DbColumn(String id, String name, int type, int length, boolean pk, boolean notNull){
		this.id = id;
		this.name = name;
		this.type = type;
		this.length = length;
		this.pk = pk;
		this.notNull = notNull;
	}
	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}	
}

