package com.webcash.sws.comm.extras.biz;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.webcash.sws.comm.extras.Extras;

public class Extra_Plus extends Extras {
	private static final String ATVT_NAME			= "ATVT_NAME"; 			// 호출액티비티 이름
	private static final String SEARCH_GUBUN		= "SEARCH_GUBUN";		// 업종, 지역 구분값
	
	private static final String PLACENAME			= "PLACENAME";			// 장소명
	private static final String PLACENO				= "PLACENO";			// 중심 장소 번호
	private static final String BUSINESSNO			= "BUSINESSNO";			// 중심 장소 분류 번호
	private static final String BUSINESSNAME		= "BUSINESSNAME";		// 분류 이름
	private static final String BUSINESSDETAILNO 	= "BUSINESSDETAILNO"; 	// 분류 상세 분류 번호
	private static final String BUSINESSDETAILNAME 	= "BUSINESSDETAILNAME"; // 분류 상세 이름
	private static final String BUSINESSORDER		= "BUSINESSORDER";		// 중심 장소 정렬 구분
	private static final String INDEXYN				= "INDEXYN";			// 분류사용여부
	private static final String INDEXNO				= "INDEXNO";			// 분류번호
	private static final String INDEXNAME			= "INDEXNAME";			// 분류이름
	private static final String DONGNAME			= "DONGNAME";			// 동이름
	private static final String SIDO				= "SIDO";				// 시도
	private static final String GUGUN				= "GUGUN";				// 구군
	private static final String DONG				= "DONG";				// 동
	
	private static final String SEARCH_MODE			= "SEARCH_MODE";		// 업종검색시 조회모드
	private static final String SEARCH_ADDR			= "SEARCH_ADDR";		// 주소검색시 조회모드
	
	public static final String NEW_DANGOL			= "NEW_DANGOL";			// 단골 신규등록시
	public static final String UPDATE_DANGOL		= "UPDATE_DANGOL";		// 단골 신규등록시
	public static final String SEARCH_DANGOL		= "SEARCH_DANGOL";		// 기존 단골 검색시
	
	private static final String DONG_CLOSE			= "DONG_CLOSE";			// 동화면에서 닫기버튼 선택

	private static final String SIDO_ALL_GONE		= "SIDO_ALL_GONE";		// 업종검색시 조회모드
	private static final String GUGUN_ALL_GONE		= "GUGUN_ALL_GONE";		// 업종검색시 조회모드
	private static final String DONG_ALL_GONE		= "DONG_ALL_GONE";		// 업종검색시 조회모드
	
	private static final String GUBUN				= "GUBUN";				// 구분
	private static final String STORYMEMOSEQ		= "STORYMEMOSEQ";		// 스토리 메모 순번
	private static final String ISMYSTORY			= "ISMYSTORY";			// 메모 작성가능한 스토리 여부
	private static final String PLACESETSEQ			= "PLACESETSEQ";		// 장소모음순번
	private static final String PUBLICRANGE			= "PUBLICRANGE";		// 공개방법
	private static final String SUBJECT				= "SUBJECT";			// 제목
	private static final String CONTENT				= "CONTENT";			// 내용
	private static final String PHOTOURL_LIST 		= "PHOTOURL_LIST"; 		// PHOTOURL 리스트
	
	private static final String NAME_LIST	   		= "NAME_LIST";			// 이름 리스트	
	private static final String PHONE_LIST	    	= "PHONE_LIST";			// 전화번호 리스트
	private static final String GROUPCODE			= "GROUPCODE";			// 간지
	
	public _Param Param = new _Param();

	public Extra_Plus(Activity atvt) {
		super(atvt);
	}

	public Extra_Plus(Activity atvt, Intent intent) {
		super(atvt, intent);
	}

	public Extra_Plus(Activity atvt, Bundle bundle) {
		super(atvt, bundle);
	}

	public class _Param {		
		/**
		 * 호출액티비티 이름
		 * @param value
		 */
		public void setAtvtName(String value) {
			setString(ATVT_NAME, value);
		}
		
		/**
		 * 호출액티비티 이름
		 * @return
		 */
		public String getAtvtName() {
			return getString(ATVT_NAME);
		}
		
		/**
		 * 업종, 지역 구분값
		 * @param string
		 */
		public void setSearchGubun(String string) {
			setString(SEARCH_GUBUN, string);
		}

		/**
		 * 업종, 지역 구분값
		 * @return 
		 */
		public String getSearchGubun() {
			return getString(SEARCH_GUBUN);
		}
		
		/**
		 * 장소명
		 * @param value
		 */
		public void setPlaceName(String value) {
			setString(PLACENAME, value);
		}		

		/**
		 * 장소명
		 * @return 
		 */
		public String getPlaceName() {
			return getString(PLACENAME);
		}
		
		/**
		 * 중심 장소 번호
		 * @param string
		 */
		public void setPlaceNo(String string) {
			setString(PLACENO, string);
		}

		/**
		 * 중심 장소 번호
		 * @return 
		 */
		public String getPlaceNo() {
			return getString(PLACENO);
		}
		
		/**
		 * 중심 장소 분류 번호
		 * @param string
		 */
		public void setBusinessNo(String string) {
			setString(BUSINESSNO, string);
		}

		/**
		 * 중심 장소 분류 번호
		 * @return 
		 */
		public String getBusinessNo() {
			return getString(BUSINESSNO);
		}
		
		/**
		 * 분류 이름
		 * @param string
		 */
		public void setBusinessName(String string) {
			setString(BUSINESSNAME, string);
		}

		/**
		 * 분류 이름
		 * @return 
		 */
		public String getBusinessName() {
			return getString(BUSINESSNAME);
		}
		
		
		/**
		 * 업종 상세 번호
		 * @param string
		 */
		public void setBusinessDetailNo(String string) {
			setString(BUSINESSDETAILNO, string);
		}

		/**
		 * 업종 상세 번호
		 * @return 
		 */
		public String getBusinessDetailNo() {
			return getString(BUSINESSDETAILNO);
		}
		
		/**
		 * 업종 상세  이름
		 * @param string
		 */
		public void setBusinessDetailName(String string) {
			setString(BUSINESSDETAILNAME, string);
		}

		/**
		 * 업종 상세  이름
		 * @return 
		 */
		public String getBusinessDetailName() {
			return getString(BUSINESSDETAILNAME);
		}

		/**
		 * 중심 장소 정렬 구분
		 * @param string
		 */
		public void setBusinessOrder(String string) {
			setString(BUSINESSORDER, string);
		}

		/**
		 * 중심 장소 정렬 구분
		 * @return 
		 */
		public String getBusinessOrder() {
			return getString(BUSINESSORDER);
		}

		/**
		 * 분류사용여부
		 * @param string
		 */
		public void setIndexYN(String string) {
			setString(INDEXYN, string);
		}

		/**
		 * 분류사용여부
		 * @return 
		 */
		public String getIndexYN() {
			return getString(INDEXYN);
		}
		
		/**
		 * 분류번호
		 * @param string
		 */
		public void setIndexNo(String string) {
			setString(INDEXNO, string);
		}

		/**
		 * 분류번호
		 * @return 
		 */
		public String getIndexNo() {
			return getString(INDEXNO);
		}
		
		/**
		 * 분류이름
		 * @param string
		 */
		public void setIndexName(String string) {
			setString(INDEXNAME, string);
		}

		/**
		 * 분류이름
		 * @return 
		 */
		public String getIndexName() {
			return getString(INDEXNAME);
		}
		
		/**
		 * 동이름
		 * @param string
		 */
		public void setDongName(String string) {
			setString(DONGNAME, string);
		}

		/**
		 * 동이름
		 * @return 
		 */
		public String getDongName() {
			return getString(DONGNAME);
		}
		
		/**
		 * 시도
		 * @param value
		 */
		public void setSido(String value) {
			setString(SIDO, value);
		}

		/**
		 * 시도
		 * @return 
		 */
		public String getSido() {
			return getString(SIDO);
		}
		
		/**
		 * 구군
		 * @param value
		 */
		public void setGugun(String value) {
			setString(GUGUN, value);
		}

		/**
		 * 구군
		 * @return 
		 */
		public String getGugun() {
			return getString(GUGUN);
		}
		
		/**
		 * 동
		 * @param value
		 */
		public void setDong(String value) {
			setString(DONG, value);
		}

		/**
		 * 동
		 * @return 
		 */
		public String getDong() {
			return getString(DONG);
		}
		
		/**
		 * 업종검색 사용 용도
		 * @return 
		 */
		public void setSearchMode(String string) {
			setString(SEARCH_MODE, string);
		}
		
		/**
		 * 업종검색 사용 용도
		 * @return 
		 */
		public String getSearchMode()
		{
			return getString(SEARCH_MODE);
		}
		
		/**
		 * 주소검색시 조회모드(ADD_ADDR:주소검색시 '전체'는 보이지 않음, SEE_MAP:보기설정에 관계없이 동까지 출력)
		 * @param string
		 */
		public void setSearchAddr(String string) {
			setString(SEARCH_ADDR, string);
		}

		/**
		 * 주소검색시 조회모드(ADD_ADDR:주소검색시 '전체'는 보이지 않음, SEE_MAP:보기설정에 관계없이 동까지 출력) 
		 * @return 
		 */
		public String getSearchAddr() {
			return getString(SEARCH_ADDR);
		}
		
		/**
		 * 동화면 종료
		 * @param string
		 */
		public void setDongClose(String string) {
			setString(DONG_CLOSE, string);
		}

		/**
		 * 동화면 종료 
		 * @return 
		 */
		public String getDongClose() {
			return getString(DONG_CLOSE);
		}
		
		/**
		 * 시도 전체선택 숨기기
		 * @return 
		 */
		public void setSidoAllGone(String string) {
			setString(SIDO_ALL_GONE, string);
		}
		
		/**
		 * 시도 전체선택 숨기기
		 * @return 
		 */
		public String getSidoAllGone()
		{
			return getString(SIDO_ALL_GONE);
		}
		
		/**
		 * 구군 전체선택 숨기기
		 * @return 
		 */
		public void setGugunAllGone(String string) {
			setString(GUGUN_ALL_GONE, string);
		}
		
		/**
		 * 구군 전체선택 숨기기
		 * @return 
		 */
		public String getGugunAllGone()
		{
			return getString(GUGUN_ALL_GONE);
		}
		
		/**
		 * 동 전체선택 숨기기
		 * @return 
		 */
		public void setDongAllGone(String string) {
			setString(DONG_ALL_GONE, string);
		}
		
		/**
		 * 동 전체선택 숨기기
		 * @return 
		 */
		public String getDongAllGone()
		{
			return getString(DONG_ALL_GONE);
		}
		
		/**
		 * 구분
		 * @return 
		 */
		public void setGubun(String string) {
			setString(GUBUN, string);
		}
		
		/**
		 * 구분
		 * @return 
		 */
		public String getGubun()
		{
			return getString(GUBUN);
		}
		
		/**
		 * 스토리 메모 순번
		 * @return 
		 */
		public void setStoryMemoSeq(String string) {
			setString(STORYMEMOSEQ, string);
		}
		
		/**
		 * 스토리 메모 순번
		 * @return 
		 */
		public String getStoryMemoSeq()
		{
			return getString(STORYMEMOSEQ);
		}
		
		/**
		 * 메모 작성가능한 스토리 여부
		 * @return 
		 */
		public void setIsMyStory(String string) {
			setString(ISMYSTORY, string);
		}
		
		/**
		 * 메모 작성가능한 스토리 여부
		 * @return 
		 */
		public String getIsMyStory()
		{
			return getString(ISMYSTORY);
		}
		
		/**
		 * 장소모음순번
		 * @return 
		 */
		public void setPlaceSetSeq(String string) {
			setString(PLACESETSEQ, string);
		}
		
		/**
		 * 장소모음순번
		 * @return 
		 */
		public String getPlaceSetSeq()
		{
			return getString(PLACESETSEQ);
		}
		
		/**
		 * 공개방법
		 * @return 
		 */
		public void setPublicRange(String string) {
			setString(PUBLICRANGE, string);
		}
		
		/**
		 * 공개방법
		 * @return 
		 */
		public String getPublicRange()
		{
			return getString(PUBLICRANGE);
		}
		
		/**
		 * 제목
		 * @return 
		 */
		public void setSubject(String string) {
			setString(SUBJECT, string);
		}
		
		/**
		 * 간지
		 * @return 
		 */
		public String getGroupCode()
		{
			return getString(GROUPCODE);
		}
		
		/**
		 * 간지
		 * @return 
		 */
		public void setGroupCode(String string) {
			setString(GROUPCODE, string);
		}
		
		/**
		 * 제목
		 * @return 
		 */
		public String getSubject()
		{
			return getString(SUBJECT);
		}
		
		/**
		 * 내용
		 * @return 
		 */
		public void setContent(String string) {
			setString(CONTENT, string);
		}
		
		/**
		 * 내용
		 * @return 
		 */
		public String getContent()
		{
			return getString(CONTENT);
		}
		
		/**
		 * PHOTOURL 리스트
		 * @return 
		 */
		public  ArrayList<String> getPhotoURLList() {
			return getList(PHOTOURL_LIST);
		}
		
		/**
		 * PHOTOURL 리스트
		 * @param value
		 */
		public void setPhotoURLList(ArrayList<String> value) {
			setList(PHOTOURL_LIST, value);
		}
		
		/**
		 * 전화 번호 리스트
		 * @param value
		 */
		public void setPhoneList(ArrayList<String> value) {
			setList(PHONE_LIST, value);
		}

		/**
		 * 전화 번호 리스트
		 * @return 
		 */
		public  ArrayList<String> getPhoneList() {
			return getList(PHONE_LIST);
		}
		
		/**
		 * 주소록 이름 리스트
		 * @param value
		 */
		public void setNameList(ArrayList<String> value) {
			setList(NAME_LIST, value);
		}

		/**
		 * 주소록 이름 리스트
		 * @return 
		 */
		public  ArrayList<String> getNameList() {
			return getList(NAME_LIST);
		}
	}
}
