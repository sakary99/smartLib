package com.webcash.sws.comm.util;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationItem implements Parcelable{
	String name   	= ""; 	// 장소이름
	String seqNo  	= ""; 	// 장소번호
	String telno  	= "";  	// 전화번호
	String address	= ""; 	// 기본주소 
	String address2 = "";	// 상세주소
	String bizType	= "";	// 업종
	String status 	= ""; 	// 단골상태
	String lat    	= "";	// 위도
	String lng    	= "";	// 경도
	int dist 		= 0;	// 거리

	/**
	 * 전화번호
	 * @return
	 */
	public String getTelno() {
		return telno;
	}

	/**
	 * 전화번호
	 * @param telno
	 */
	public void setTelno(String telno) {
		this.telno = telno;
	}
	
	/**
	 * 기본주소
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 기본주소
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 상세주소
	 * @return
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * 상세주소
	 * @param address
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * 업종
	 * @return
	 */
	public String getBizType() {
		return bizType;
	}

	/**
	 * 업종
	 * @param bizType
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	/**
	 * 상태
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * 상태
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 장소이름
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 장소이름
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 위도
	 */
	public String getLat() {
		return lat;
	}
	
	/**
	 * 위도
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}
	
	/**
	 * 경도
	 */
	public String getLng() {
		return lng;
	}
	
	/**
	 * 경도
	 */
	public void setLng(String lng) {
		this.lng = lng;
	}
	
	/**
	 * 장소번호
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * 장소번호
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * 거리
	 */
	public int getDist() {
		return dist;
	}
	
	/**
	 * 거리
	 */
	public void setDist(int dist) {
		this.dist = dist;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
        dest.writeString(seqNo);
        dest.writeString(telno);
        dest.writeString(address);
        dest.writeString(address2);
        dest.writeString(bizType);
        dest.writeString(status);
        
	}
	
	public static final Parcelable.Creator<LocationItem> CREATOR = new Creator<LocationItem>() {
        public LocationItem createFromParcel(Parcel source) {
           LocationItem temp = new LocationItem();
           temp.setName(source.readString());
           temp.setSeqNo(source.readString());
           temp.setTelno(source.readString());
           temp.setAddress(source.readString());
           temp.setAddress2(source.readString());
           temp.setBizType(source.readString());
           temp.setStatus(source.readString());
        	
           return temp;
        }
        public LocationItem[] newArray(int size) {
            return new LocationItem[size];
        }
    };

}
