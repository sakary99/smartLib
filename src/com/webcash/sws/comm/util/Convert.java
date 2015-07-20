package com.webcash.sws.comm.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.webcash.sws.comm.pref.LibConf;

public class Convert {
	/**
	 * 일자구분자
	 */
	private static final String DATE_DELIMITER 		= ".";
	
	/**
	 * 일자구분자
	 */
	private static final String TIME_DELIMITER 		= ":";

	/**
	 * 구분자없는 일자포멧
	 */
	private static final String FORMAT_YYYYMMDD 	= "yyyyMMdd";
	
	/**
	 * 일시포멧
	 */
	private static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * 요일배열
	 */
	private static String[] DAY_OF_WEEK = {null, "일", "월", "화", "수", "목", "금", "토"};

	public static class strRate{
		public static String paddingZero(String value) {
			String str = value;
			if(value.substring(0,1).equals(".")) {
				str = "0" + value;
			}

			return str;
		}		
	}
		
	public static class strAmount{		
		public static String format(double value) {
			String str = Double.toString(value);

			return format(str);
		}

		/**
		 * 문자열 금액을 넣어주면 ,넣어서 반환해준다.  
		 * @param String
		 * @return String
		 */
		public static String format(String value) {
			String str;

			if(value == null) return "";
			if(value.trim().equals(""))
				return "0";

			NumberFormat money = NumberFormat.getInstance(Locale.US);
			str = money.format(Double.parseDouble(value));
			return str;
		}

		/**
		 * 문자열 금액에서 ,표시를 전부 제거해준다. 
		 * @param String
		 * @return String
		 */
		public static String nonFormat(String value) {
			String str = value.trim().replaceAll("\\,", "");
			str = str.replaceAll("\\*", "");

			return str;
		}
		
		/**
		 * 문자열금액에서 소수점 첫째자리를 반올임하고 ,넣어서  반환해준다.
		 * 
		 * @param value
		 * @return
		 */
		public static String formatRoundHalfUp(String value) {
			if(value == null) return "";
			if(value.trim().equals(""))
				return value;
			
			 double dblVal = Double.parseDouble(value);
			 BigDecimal bigVal =  BigDecimal.valueOf(dblVal);
			 
			return format(bigVal.setScale(0, BigDecimal.ROUND_HALF_UP)+"");
		}

		/**
		 * 금액 콤마 표시 해주고 소수점은 그대로 둔다
		 * @param value
		 * @return
		 */
		public static String fractZero(String value) {
			if(value == null) return "";
			if(value.trim().equals(""))
				return value;
			String[] temp = value.split("[.]");
			String digit = format(temp[0]);
			return digit + ((temp[1] == null || "".equals(temp[1]))? "" : "." + temp[1]);
		}
	}

	/**
	 * 일자관련 클래스
	 */
	public static class ComDate {
		
		/**
		 * 입력된 시간과 현재 시간이 5시간 차이 이상 차이 여부를 판단한다.
		 * @param updateTime
		 * @return
		 */
		public static boolean is5hoursDiff(long updateTime){
			
			long diff = getTimeDiff(updateTime);
			if(diff >= 5) {
				return true;
			}
			return false;
		}
		
		/**
		 * 입력된 시간과 현재 시간 차이
		 * @param updateTime
		 * @return
		 */
		public static long getTimeDiff(long updateTime) {
			
			//현재의 시간 설정	
			long currTime = getCurrentTime();
			long diff= currTime - updateTime;
			
			//시간 으로 변환
			long hours = (diff/60000) / 60;
			
			return hours;
		}
		
		/**
		 * 현재 시간
		 * @return
		 */
		public static long getCurrentTime() {
			
			//현재의 시간 설정	
			Calendar cal=Calendar.getInstance();
			Date endDate=cal.getTime();
			long currTime = endDate.getTime();
			
			return currTime;
		}
		
		/**
		 * 한국표준시간(YYYYMMDDHHMMSS)을 제공한다.
		 * @return
		 */
		public static String tranKST() {
			String result = "";
			
			Date currentTime = new Date ( );
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat (FORMAT_YYYYMMDDHHMMSS, Locale.KOREA );
			mSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			
			result = mSimpleDateFormat.format ( currentTime );
			
			return result;
		}

		public static String CurrDate(String delem){
			Date today;
			today = Calendar.getInstance().getTime();

			SimpleDateFormat format = new SimpleDateFormat(delem);
			String strDay = format.format(today);

			return strDay;
		}
		
		public static String NextDate(int delem) throws Exception{
			return getAfterDate(delem);
		}
		
		/**
		 * 현재일자를 제공한다.
		 * @return 현재일자(YYYYMMDD)
		 */
		public static String today(){
			return CurrDate(FORMAT_YYYYMMDD);
		}
		/**
		 * 내일 일자를 제공한다. 
		 * @return
		 * @throws Exception 
		 */
		public static String tomorrow() throws Exception{
			return NextDate(1);
		}
		
		public static String currDateTime(){
			return CurrDate(FORMAT_YYYYMMDDHHMMSS);
		}
		
		public static String currDateTimeMillisend() {
			return CurrDate("yyyy-MM-dd HH:mm:ss SSS");
		}
		
		public static String formatCurrDate(String format){
			return CurrDate(format);
		}
		
		public static String todayDelimiter(){
			return formatDelimiter(today());
		}
		
		public static String tomorrowDelimiter() throws Exception{
			return formatDelimiter(tomorrow());
		}

		public static String lastDay(String year, String month){
			int day = lastDay(Integer.valueOf(year), Integer.valueOf(month));
			return year + month + pubCharL(String.valueOf(day), 2, "0");
		}

		/**
		 * 해당월의 마지막 날짜를 반환한다.
		 * @param year 년도
		 * @param month 월
		 * @return
		 */
		public static int lastDay(int year, int month){
			Calendar calender = Calendar.getInstance();
			calender.set(year, month-1, 1);
			int lastDay = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
			return lastDay;
		}

		public static String lastDay(String yyyymm){
			int year = Integer.valueOf(yyyymm.substring(0, 4));
			int month = Integer.valueOf(yyyymm.substring(4, 6));
			Calendar calender = Calendar.getInstance();
			calender.set(year, month-1, 1);
			int lastDay = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
			return String.valueOf(lastDay);
		}

		/**
		 * 구분자가 없는 일자를 도트(.) 구분자가 있는 일자로 제공한다.
		 * @param value 구분자가 없는 일자(YYYYMMDD)
		 * @return 대쉬(.)구분자가 있는 일자(YYYY.MM.DD)
		 */
		public static String formatDelimiter(String value) {
			return formatDotDelimiter(value, DATE_DELIMITER);
		}
		
		public static String formatDotDelimiter(String value, String del) {
			if (value.length() != 8) return value;
			String str;
			str = value.substring(0, 4) + del +
			value.substring(4, 6) + del +
			value.substring(6, 8);
			return str;
		}

		/**
		 * 도트(.) 구분자가 있는 일자를 구분자가 없는 일자로 제공한다.
		 * @param value 도트(.)구분자가 있는 일자(YYYY.MM.DD)
		 * @return 구분자가 없는 일자(YYYYMMDD)
		 */
		public static String formatNoneDelimiter(String value) {
			String str;
			
			str = value.substring(0,4) +
			value.substring(5,7) +
			value.substring(8,10);
			
			return str;
		}
		
		/**
		 * 콤마(,) 구분자가 있는 금액을 구분자가 없는 금액으로 제공한다.
		 * @param value 콤마(,)구분자가 있는 일자(123,456,789)
		 * @return 구분자가 없는 일자(123,456,789)
		 */
		public static String formatNoneComma(String value) {
			return value.replaceAll(",", "");
		}
		

		/**
		 * 구분자가 없는 일자의 요일을 제공한다.
		 * @param value 구분자가 없는 일자(YYYYMMDD)
		 * @return 요일
		 */
		public static String dayOfWeek(String value) {
			int year = Integer.parseInt(value.substring(0, 4)); 
			int month = Integer.parseInt(value.substring(4, 6));
			int day = Integer.parseInt(value.substring(6, 8));
			
			return dayOfWeek(year, month, day);
		}

		public static String dayOfWeek(int year, int month, int day) {
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, day);
			int dayofweek = cal.get(Calendar.DAY_OF_WEEK);

			return DAY_OF_WEEK[dayofweek];
		}
		
		/**
		 * 구분자가 없는 일자의 요일을 제공한다.(Calendar No)
		 * @param value 구분자가 없는 일자(YYYYMMDD)
		 * @return 요일
		 */
		public static int dayOfWeekNo(String value) {
			int year = Integer.parseInt(value.substring(0, 4)); 
			int month = Integer.parseInt(value.substring(4, 6));
			int day = Integer.parseInt(value.substring(6, 8));
			
			return dayOfWeekNo(year, month, day);
		}
		public static int dayOfWeekNo(int year, int month, int day) {
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, day);
			int dayofweek = cal.get(Calendar.DAY_OF_WEEK);

			return dayofweek;
		}

		/**
		 * 구분자가 없는 일자의 괄호를 포함한 요일을 제공한다.
		 * @param value 구분자가 없는 일자(YYYYMMDD)
		 * @return 괄호를 포함한 요일
		 */
		public static String dayOfWeekWithBracket(String value) {
			return "(" + dayOfWeek(value) + ")";
		}

		public static String formatNoneDelimiter(int year, int month, int day) {
			return String.valueOf(year) +
			Convert.pubCharL(String.valueOf(month + 1), 2, "0") +
			Convert.pubCharL(String.valueOf(day), 2, "0");
		}

		public static String formatNoneDelimiter(View view, TextView tv, int id ) {
			String str = ((TextView)view.findViewById(id)).getText().toString();
			return str.replaceAll(DATE_DELIMITER, "");
		}

		public static String formatKorYYmmDD(String value) {
			String str = value.replace(DATE_DELIMITER, "").trim();
			if(str.length() != 8) return value;

			String year = str.substring(0, 4);
			String month = str.substring(4, 6);
			String day = str.substring(6, 8);
			month = String.valueOf(Integer.valueOf(month));
			day = String.valueOf(Integer.valueOf(day));
			
			return year + "년 " + month + "월 " + day + "일";
		}
		
		public static String formatKorCurrTime() {
			String str = currDateTime().trim();

			String year = str.substring(0, 4);
			String month = str.substring(4, 6);
			String day = str.substring(6, 8);
			String hour = str.substring(8, 10);
			String min = str.substring(10, 12);
			String sec = str.substring(12, 14);
			
			month = String.valueOf(Integer.valueOf(month));
			day = String.valueOf(Integer.valueOf(day));
			
			return year + "년 " + month + "월 " + day + "일 " + hour + "시 " + min + "분 " + sec + "초";
		}
		
		public static String getCurrHourMn() {
			String str = currDateTime().trim();

			String hour = str.substring(8, 10);
			String min = str.substring(10, 12);
			
			return hour + "" + min ;
		}
		
		public static String formatKormmDD(String value) {
			String str = value.replace(DATE_DELIMITER, "").trim();
			if(str.length() != 8) return value;

			String year = str.substring(0, 4);
			String month = str.substring(4, 6);
			String day = str.substring(6, 8);
			month = String.valueOf(Integer.valueOf(month));
			day = String.valueOf(Integer.valueOf(day));
			
			return  month + "월 " + day + "일";
		}
		
		public static String formatMillisTime(long millisTime) {
			
			Date date = new Date(millisTime);
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat (FORMAT_YYYYMMDDHHMMSS, Locale.KOREA );
			mSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			
			String formatDate = mSimpleDateFormat.format ( date );
			
			String str = formatDate.trim();
			String year = str.substring(0, 4);
			String month = str.substring(4, 6);
			String day = str.substring(6, 8);
			String hour = strTime.convertTime(str.substring(8, 10));
			String min = str.substring(10, 12);
			
			month = String.valueOf(Integer.valueOf(month));
			day = String.valueOf(Integer.valueOf(day));
			
			return year + "년 " + month + "월 " + day + "일 " + hour + "시 " + min + "분 ";
			
		}
		
		/**
		 * 현재일자 반환
		 * @param formmater
		 * @return
		 */
		public static String today(String formmater){
			Date today;
			String dateFormatter = formmater;
			today = Calendar.getInstance().getTime();

			if(dateFormatter.equals("")) dateFormatter = "yyyyMMdd";
			SimpleDateFormat format = new SimpleDateFormat(dateFormatter);
			String strDay = format.format(today);

			return strDay;
		}
		
		/**
		 * yyyymmdd 형태의 형식으로 변환
		 * changeDateType("2010-6-1", "-") -> '20100601' return
		 * @param value
		 * @param delim
		 * @return
		 */
		public static String changeDateType(String value, String delim) {
			String strDate = "";
			if(value == null || value.equals("")) return strDate;

			StringTokenizer stkBegin = new StringTokenizer(value, delim);
			String str = null;
			while(stkBegin.hasMoreElements()){
				str = stkBegin.nextToken();
				if(str.length() < 2)	str = Convert.pubCharL(str, 2, "0");
				strDate += str;
			}
			return strDate;
		}

		/**
		 * 날짜 포멧 (####-##-##) 리턴
		 * 입력된 값이 20100101일경우 2010-01-01로 결과값 리턴 
		 * 사용예 )
		 * formatYYYYMMDD(20100101)
		 * @return String _day
		 * @param String _value
		 */
		public static String formatYYYYMMDD(String _value) {
			String _day = null;
			if ( _value == null || _value.length() < 8 ) {
				_day = _value;
			} else {
				StringBuffer sbr = new StringBuffer();
				sbr.append(_value.substring(0,4));
				sbr.append(DATE_DELIMITER);
				sbr.append(_value.substring(4,6));
				sbr.append(DATE_DELIMITER);
				sbr.append(_value.substring(6,8));
				_day = sbr.toString();
			}
			return _day;
		}

		/**
		 * 날짜 포멧 (####-#-#) 리턴
		 * 입력된 값이 20100101일경우 2010-1-1로 결과값 리턴 
		 * 사용예 )
		 * formatYYYYMMDD(20100101)
		 * @return String _day
		 * @param String _value
		 */
		public static String formatYYYYMD(String _value) {
			String _day = null;

			if ( _value == null || _value.length() < 8 ) {
				_day = _value;
			} else {
				String yyyy = _value.substring(0,4);
				String mm = _value.substring(4,6);
				String dd = _value.substring(6,8);
				
				_day = formatYYYYMD(yyyy, mm, dd);
			}
			return _day;
		}
		
		/**
		 * 날짜 포멧 (####-#-#) 리턴
		 * 입력된 값이 20100101일경우 2010-1-1로 결과값 리턴 
		 * 사용예 )
		 * formatYYMMDD(20100101)
		 */
		public static String formatYYMMDD(String _value) {
			String _day = null;

			if ( _value == null || _value.length() < 8 ) {
				_day = _value;
			} else {
				String yy = _value.substring(2,4);
				String mm = _value.substring(4,6);
				String dd = _value.substring(6,8);
				
				_day = yy + DATE_DELIMITER + mm + DATE_DELIMITER + dd;
			}
			return _day;
		}

		/**
		 * 날짜 포멧 (####-#-#) 리턴
		 * 입력된 값이 2010, 01, 01일경우 2010-1-1로 결과값 리턴 
		 */
		public static String formatYYYYMD(String YYYY, String MM, String DD) {
			String _day = null;
			String yyyy = YYYY;
			String mm = MM;
			String dd = DD;

			StringBuffer sbr = new StringBuffer();
			sbr.append(yyyy);
			sbr.append(DATE_DELIMITER);
			mm = MM.substring(0, 1);
			if(mm.equals("0")){
				sbr.append((MM.replace('0', ' ')).trim());
			} else {
				sbr.append(MM);
			}
			sbr.append(DATE_DELIMITER);
			dd = DD.substring(0, 1);
			if(dd.equals("0")){
				sbr.append((DD.replace('0', ' ')).trim());
			} else {
				sbr.append(DD);
			}
			_day = sbr.toString();

			return _day;
		}
		
		/**
		 * 현재일로부터 #일후의 날짜 자동 계산해서 리턴
		 *  getAfterDate(int) <- 알고자 하는 몇일후의 날짜 입력 
		 * @return String
		 * @param Int
		 * @throws Exception 
		 */
		public static String getAfterDate(int _Day) throws Exception  {
			int y, m, d ;
			y = Integer.parseInt(today().substring(0, 4));
			m = Integer.parseInt(today().substring(4, 6));
			d = Integer.parseInt(today().substring(6, 8));

			return getAfterDate2(y, m, d, _Day);
		}

		/**
		 * 특정일로부터 #일후의 날짜 자동 계산해서 리턴
		 */
		public static String getAfterDate(String _YYYYMMDD, int _Day) throws Exception {
			int y, m, d ;
			y = Integer.parseInt(_YYYYMMDD.substring(0, 4));
			m = Integer.parseInt(_YYYYMMDD.substring(4, 6));
			d = Integer.parseInt(_YYYYMMDD.substring(6, 8));
			
			return getAfterDate2(y, m, d, _Day);
		}

		public static String getAfterDate2(int YYYY, int MM, int DD, int _Day) throws Exception {
			Calendar temp=Calendar.getInstance();
			int y, m, d ;
			y = YYYY;
			m = MM - 1;
			d = DD ;
			temp.set(y, m, d);
			temp.add(Calendar.DAY_OF_MONTH,_Day);
			int nYear  = temp.get(Calendar.YEAR);
			int nMonth = temp.get(Calendar.MONTH) + 1;
			int nDay   = temp.get(Calendar.DAY_OF_MONTH);
			StringBuffer sbDate=new StringBuffer();
			sbDate.append(nYear);
			if (nMonth < 10)sbDate.append("0");
			sbDate.append(nMonth);
			if (nDay < 10)sbDate.append("0");
			sbDate.append(nDay);
			return sbDate.toString();
		}
		
		public static class strTime {
			/***
			 * 24시간 시간표시를 오전/오후시간으로 리턴
			 * 사용예)
			 * converetTime(14) => 오후2시
			 * @param value
			 * @return 
			 */
			public static String convertTime(String value) {  
				int intTime = Integer.valueOf(value);
				String rsltTime = "";		
				if(intTime <= 11) 		// 오전 0시 ~ 오전11시
					rsltTime = "오전 " + String.format("%02d", intTime);
				else if(intTime == 12)	// 오후 12시
					rsltTime = "오후 " + String.format("%02d", intTime);
				else if(intTime >= 13  || intTime <= 23) {	// 오후1시 ~ 오후11시
					intTime  = intTime - 12;
					rsltTime = "오후 " + String.format("%02d", intTime);
				}
				else if(intTime == 24 || intTime == 0)	// 오후 24시
					rsltTime = "오전 00";
				return rsltTime;
			}
			
			/**
			 * 
			 * @param value : hhmmss 또는 hhmm
			 * @return 오전 03:35
			 */
			public static String convertKorTime(String value) {
				
				String hour = "";
				
				if (value.length() >= 4) {
					
					hour = convertTime(value.substring(0, 2));
					
					return hour + ":" + value.substring(2, 4);
					
				} else {
					
					return value;
					
				}				
				
			}
			
			/**
			 * 
			 * @param value : hhmmss 또는 hhmm
			 * @return 오전 03시35분
			 */
			public static String convertKorTimeHourMin(String value) {
				
				String hour = "";
				
				if (value.length() >= 4) {
					
					hour = convertTime(value.substring(0, 2));
					
					return hour + "시" + value.substring(2, 4)+ "분";
					
				} else {
					
					return value;
					
				}				
				
			}
			
			/**
			 * 구분자가 없는 시간를 콜론(:) 구분자가 있는 시간으로 제공한다.
			 * @param value 구분자가 없는 일자(HHMMSS)
			 * @return 콜론(:)구분자가 있는 일자(HH-MM-SS)
			 */
			public static String formatDelimiter(String value) {
				return formatTimeDelimiter(value, TIME_DELIMITER);
			}
			
			public static String formatTimeDelimiter(String value, String del) {
				String str = "";

				if(value.length() == 4){
					str = value.substring(0, 2) + del + 
						value.substring(2, 4);
				}else if(value.length() == 6){
					str = value.substring(0, 2) + del +
						value.substring(2, 4) + del +
						value.substring(4, 6);
				}
				return str;
			}
		}
		/**
		 * 현재일로 부터 _Month 숫자후의 일자를 반환한다.
		 * <BR>반환형식<BR>
		 *  YYYYMMDD
		 * @param _Month
		 * @return
		 */
		
		public static String getAfterMonth(int _Month) {
			Calendar temp=Calendar.getInstance();

			temp.add(Calendar.MONTH, _Month);

			int nYear  = temp.get(Calendar.YEAR);
			int nMonth = temp.get(Calendar.MONTH) + 1;
			int nDay   = temp.get(Calendar.DAY_OF_MONTH);
			StringBuffer sbDate=new StringBuffer();
			sbDate.append(nYear);
			if (nMonth <10)sbDate.append("0");
			sbDate.append(nMonth);
			if (nDay <10)sbDate.append("0");
			sbDate.append(nDay);
			return sbDate.toString();
		}
		
		/**<우리은행 개인 자동이체 - 당/타행 사용>
		 * @author heejeong
		 * 내일로 부터 _Month 숫자후의 일자를 반환한다.
		 * <BR>반환형식<BR>
		 * YYYYMMDD
		 * @param _Month
		 * @return
		 */
		public static String getAfterMonth2(int _Month) {
			Calendar temp=Calendar.getInstance();

			temp.add(Calendar.MONTH, _Month);

			int nYear  = temp.get(Calendar.YEAR);
			int nMonth = temp.get(Calendar.MONTH) + 1;
			int nDay   = temp.get(Calendar.DAY_OF_MONTH)+1;
			StringBuffer sbDate=new StringBuffer();
			sbDate.append(nYear);
			if (nMonth <10)sbDate.append("0");
			sbDate.append(nMonth);
			if (nDay <10)sbDate.append("0");
			sbDate.append(nDay);
			return sbDate.toString();
		}
		
		public static String getAfterMonthAndInitializeDay(int _Month) {
			Calendar temp=Calendar.getInstance();

			temp.add(Calendar.MONTH, _Month);

			int nYear  = temp.get(Calendar.YEAR);
			int nMonth = temp.get(Calendar.MONTH) + 1;
			StringBuffer sbDate=new StringBuffer();
			sbDate.append(nYear);
			if (nMonth <10)sbDate.append("0");
			sbDate.append(nMonth);
			sbDate.append("01");
			return sbDate.toString();
		}

		/**
		 * 해당일자의 요일을 반환한다.
		 * @param 일자 (YYYYMMDD)
		 * @return 요일 (월,화,수,목,금,토,일)
		 */
		public static String getDayOfWeek(String aDate) {
			String strDate = aDate.replace(LibConf.DATE_DELIMITER, "");
			String strReturn = "";
			int Year = Integer.parseInt(strDate.substring(0, 4)); 
			int Month = Integer.parseInt(strDate.substring(4, 6));
			int Date = Integer.parseInt(strDate.substring(6, 8));
			Calendar cal = Calendar.getInstance();
			cal.set(Year, Month - 1, Date);
			switch (cal.get(Calendar.DAY_OF_WEEK)) {
			case  Calendar.SUNDAY :
				strReturn = "일";
				break;
			case Calendar.MONDAY :
				strReturn = "월";
				break;
			case Calendar.TUESDAY :
				strReturn = "화";
				break;
			case Calendar.WEDNESDAY :
				strReturn = "수";
				break;
			case Calendar.THURSDAY :
				strReturn = "목";
				break;
			case Calendar.FRIDAY :
				strReturn = "금";
				break;
			case Calendar.SATURDAY :
				strReturn = "토";
				break;
			}
			return strReturn;
		}
	}

	/**
	 * 카드
	 */
	public static class Card {

		/**
		 * 카드번호를 4자리마다 '-' 처리하여 반환
		 * @param value
		 * @return
		 */
		public static String format(String value) {
			String str = value;
			StringBuffer sbf = new StringBuffer();
			sbf.append(str.substring(0,4) + "-");
			sbf.append(str.substring(4,8) + "-");
			sbf.append(str.substring(8, 12) + "-");
			sbf.append(str.substring(12));

			return sbf.toString();	
		}
		
		/**
		 * 카드번호를 4자리마다 '-' 처리하여 반환
		 * @param value
		 * @return
		 */
		public static String formatToAsta(String value) {
			String str = value;
			StringBuffer sbf = new StringBuffer();
			sbf.append(str.substring(0,4) + "-");
			sbf.append("****" + "-");
			sbf.append("****" + "-");
			sbf.append(str.substring(12));

			return sbf.toString();	
		}
		
		/**
		 * 카드번호 포맷 리턴 (XXXX-XXXX-XXXX-XXXX)
		 * 사용예 )
		 * formatCardNo(1111222233334444) => 1111-2222-3333-4444
		 * @return String CardNo
		 */
		public static String formatCardNo(String CardNo) {
			String _CardNo = "";
			if ( CardNo != null && CardNo.length() >= 15 ) {
				StringBuffer sbr = new StringBuffer();
				sbr.append(CardNo.substring(0,4));
				sbr.append("-");
				sbr.append(CardNo.substring(4,8));
				sbr.append("-");
				sbr.append(CardNo.substring(8,12));
				sbr.append("-");
				sbr.append(CardNo.substring(12,CardNo.length()));
				_CardNo = sbr.toString();
			}
			return _CardNo;
		}
		
		/**
		 * 카드번호 포맷 리턴 (XXXX-XXXX-XXXX-XXXX)
		 * 사용예 )
		 * formatCardNo(1111222233334444) => 1111-****-****-4444
		 * @return String CardNo
		 */
		public static String formatCardNoToAstaWitHyphens(String CardNo) {
			String _CardNo = "";
			if ( CardNo != null && CardNo.length() >= 15 ) {
				StringBuffer sbr = new StringBuffer();
				sbr.append(CardNo.substring(0,4));
				sbr.append("-");
				sbr.append("****");
				sbr.append("-");
				sbr.append("****");
				sbr.append("-");
				sbr.append(CardNo.substring(12,CardNo.length()));
				_CardNo = sbr.toString();
			}
			return _CardNo;
		}

		/**
		 * 카드번호 포맷 리턴 (XXXX-XXXX-XXXX-XXXX)
		 * 사용예 )
		 * formatCardNo(1111-2222-3333-4444) => 1111********4444
		 * @return String CardNo
		 */
		public static String formatCardNoToAsta(String CardNo) {
			if(null == CardNo) return CardNo; // 9430030967341999
			String[] temp = CardNo.split("-");
			if(temp.length < 4) return CardNo; 
			StringBuffer sbr = new StringBuffer();
			sbr.append(temp[0]);
			sbr.append("-");
			sbr.append("****");
			sbr.append("-");
			sbr.append("****");
			sbr.append("-");
			sbr.append(temp[3]);
			return sbr.toString();
		}
	}
	
	/**
	 * 계좌번호
	 * @param String value
	 */
	public static class Account {
		/**
		 * 우리은행 계좌번호
		 * @param value
		 * @return
		 */
		public static String format(String value) {
			if (value == null) return "";
			
			StringBuffer sbr = new StringBuffer();
			
			if(value.trim().length() == 14) {		// 일반계좌번호
				sbr.append(value.substring(0,3));
				sbr.append("-");
				sbr.append(value.substring(3,9));
				sbr.append("-");
				sbr.append(value.substring(9,11));
				sbr.append("-");
				sbr.append(value.substring(11,14));

				return sbr.toString();
			}else if(value.trim().length() == 16){	// 외화계좌번호 
				sbr.append(value.substring(0,3));
				sbr.append("-");
				sbr.append(value.substring(3,9));
				sbr.append("-");
				sbr.append(value.substring(9,11));
				sbr.append("-");
				sbr.append(value.substring(11,16));	
				return sbr.toString();
			}else {
				return value;
			}

		}
		
		public static String formatToAsta(String value) {
			if (value == null) return "";

			StringBuffer sbr = new StringBuffer();

			sbr.append(value.substring(0,3));
			sbr.append("-");
			sbr.append(value.substring(3,9));
			sbr.append("-");
			sbr.append(value.substring(9,11));
			sbr.append("-");
			sbr.append("***");

			return sbr.toString();
		}
		
		/**
		 * 계좌번호 마지막 3자리를 '***' 처리하여 반환
		 * @param value
		 * @return
		 */
		public static String formatToLastAsta(String value) {
			if (value == null) return "";

			StringBuffer sbr = new StringBuffer();
			sbr.append(value.substring(0,value.length()-3));
			sbr.append("***");
			
			return sbr.toString();
		}

		/**
		 * 대쉬(-) 구분자가 있는 계좌번호를 구분자가 없는 계좌번호로 제공한다.
		 * @param value 대쉬(-)구분자가 있는 계좌번호(###-######-##-###)
		 * @return 구분자가 없는 계좌번호(##############)
		 */
		public static String nonFormatAcct(String value) {
			return value.trim().replaceAll("-", "");
		}
	}	

	/**
	 * 10자리인 경우 사업자번호 포멧 (###-##-#####) 리턴
	 * 13자리인 경우 주민번호 포멧 (######-#######) 리턴
	 *
	 * @return String number
	 * @param String _value
	 */
	public static String formatCorpNo(String _value) {
		String number = null;
		if ( _value != null && _value.length() == 10 ) {
			StringBuffer sbr = new StringBuffer();
			sbr.append(_value.substring(0,3));
			sbr.append("-");
			sbr.append(_value.substring(3,5));
			sbr.append("-");
			sbr.append(_value.substring(5,10));
			number = sbr.toString();
		}
		else if( _value != null && _value.length() == 13 )
		{
			StringBuffer sbr = new StringBuffer();
			sbr.append(_value.substring(0,6));
			sbr.append("-");
			sbr.append(_value.substring(6));
			number = sbr.toString();
		}
		else {
			number = _value;
		}
		return number;
	}

	public static String conStr(String msg, String...vargs) {
		String tmp = msg;
		for(String var : vargs){
			tmp = tmp.replaceFirst(LibConf.REGULAR_EXPR, var);
		}
		return tmp;
	}

	public static long StrToLong(String str) {
		return 0;
	}

	// null 값 공백처리
	public static String null2void(String str) {
		if (str == null || "null".equals(str)) {
			return "";
		} else {

		}
		return str.trim();
	}
	public static String null2blank(String str) {
		if (str == null || "null".equals(str)) {
			return " ";
		} 
		return str.trim();
	}

	public static int null2void(int Num) {
		if (Num == 0) {
			return 0;
		} else {

		}
		return Num;
	}

	/**
	 * 문자열 앞으로 특정문자 채우기
	 * @param str 
	 * @param len
	 * @param chr
	 * @return
	 */
	public static String pubCharL(String str, int len, String chr)	{

		int strLen = str.length();
		if(strLen < len)	{
			String rtnStr = str;
			for(int i= 0; i< len-strLen ; i++){
				rtnStr = chr + rtnStr;
			}
			return rtnStr;
		}else {
			return str;
		}
	}

	/**
	 * 문자열 뒤로 특정문자 채우기
	 * @param str 
	 * @param len
	 * @param chr
	 * @return
	 */
	public static String pubCharR(String str, int len, String chr)	{

		int strLen = str.length();
		if(strLen < len)	{
			String rtnStr = str;
			for(int i= 0; i< len-strLen ; i++){
				rtnStr = rtnStr + chr;
			}
			return rtnStr;
		}else {
			return str;
		}
	}
	
	/**
	 * 한글은 크기를 2로 영어는 1 그외 +- 기호는 1로 계산하여 길이값 되돌려 줍니다. 
	 * 스마트 키패드에서 한국인은 한문 입력할 일이 없기때문에 국내에 맞게 한문은 고려하지 않습니다.  
	 * @param str
	 * @return int
	 */
	public static final int getByteSizeToComplex(String str) {
		  int en = 0;
		  int ko = 0;
		  int etc = 0;
		  
		  char[] string = str.toCharArray();
		  
		  for (int j=0; j<string.length; j++) {
		   if (string[j]>='A' && string[j]<='z') {
		    en++;
		   }
		   else if (string[j]>='\uAC00' && string[j]<='\uD7A3') {
		    ko++;
		    ko++;
		   }
		   else {
		    etc++;
		   }
		  }
		  return (en + ko + etc);  
	}



	/**
	 * 숫자만 반환한다.
	 * @param value
	 * @return
	 */
	public static String getNumber(String value) {
		final String regEx = "[0-9]";
		if (value.matches(regEx)) return value;

		StringBuffer sb = new StringBuffer();		
		for(int idx = 0; idx < value.length(); idx++) {
			if(value.substring(idx, idx+1).matches(regEx))
				sb.append(value.charAt(idx));
		}		
		return sb.toString();
	}

	/**
	 * 특수문자(- , * ), 공백을 제거한 숫자와 문자(한글포함)만 리턴
	 * @param value
	 * @return
	 */
	public static String getNoSign(String value) {
		final String regEx = "\\w";
		if (value.matches(regEx)) return value;

		StringBuffer sb = new StringBuffer();		
		for(int idx = 0; idx < value.length(); idx++) {
			if(value.substring(idx, idx+1).matches(regEx))
				sb.append(value.charAt(idx));
		}		
		return sb.toString();
	}
	
	/**
	 * 난수발생 16자리
	 * @return
	 */
	public static String getRandomKey() {
		Random r = new Random();
		
		StringBuffer sb = new StringBuffer();
    	
		for(int i=0; i<16;i++) {
    		
    		sb.append(r.nextInt(9) + 1);
    	}
		
		return sb.toString();
	}
	
	/**
	 * 한글금액표시 (100,000->십만) 리턴
	 * 
	 * @return String
	 * @param CharSequence
	 */
	public static String formatKoreanMoney(CharSequence _value){
		final String [] krNumeric = {"", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};
		final String [] krUnit_L = {"", "십", "백", "천"};			// Lower
		final String [] krUnit_U = {"", "만", "억", "조"};			// Upper
		String amount = "";				// 한글금액
		String thdAmount = "";			// 천단위 변환금액
		int idx = 0;					// 
		int pos = 0;
		boolean showUnit_L = false;
		boolean showUnit_U = false;

		char c = 0;

		CharSequence _Value = _value.toString().replaceAll(",", "");

		pos = _Value.length();
		for(int i = 0; i < _Value.length(); i++){
			c = _Value.charAt(i);
			idx = c - 0x30;

			int share = (int)Math.floor(pos / 4.0);

			if(pos >= 9){
				if(idx > 0){
					showUnit_U = true;
					thdAmount += krNumeric[idx];
					thdAmount += krUnit_L[(pos-1)%4];
				}

				if(showUnit_U && pos == 9)
					thdAmount += krUnit_U[share%4];

			}
			else if(pos >= 5){
				if(idx > 0){
					showUnit_L = true;
					thdAmount += krNumeric[idx];
					thdAmount += krUnit_L[(pos-1)%4];
				}

				if(showUnit_L && pos == 5)
					thdAmount += krUnit_U[share%4];
			}
			else{
				if(idx > 0){
					thdAmount += krNumeric[idx];
					thdAmount += krUnit_L[(pos-1)%4];
				}

			}
			amount = thdAmount;
			pos--;
		}

		return amount;
	}
	
	
	/**
	 * DIP(Density Independent Pixel) 단위를  Pixel 단위로 변환한다.
	 * @param nDip : dip 값
	 * @return Pixel 값
	 */
	public static int getDipToPixel(Activity atv, int nDip) {
		float scale = atv.getResources().getDisplayMetrics().density;
		return (int)(nDip * scale + 0.5f);
	}	

	public static int getDipToPixel(Context context, int nDip) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int)(nDip * scale + 0.5f);
	}	

	public static String getClassToClassName(String ClassName) {
		return ClassName.replace("class", "").replace(" ", "");
		
	}
	
	public static class Map {
		public static String getFormatLocation(String value){
			String srtAddr = null;
			srtAddr = value.replaceAll("특별시", "").replaceAll("광역시", "");
			
			if(value.contains("제주도")){
				srtAddr = value.replaceAll("제주도", "제주");
			}
			if(value.contains("강원도")){
				srtAddr = value.replaceAll("강원도", "강원");
			}
			if(value.contains("경기도")){
				srtAddr = value.replaceAll("경기도", "경기");
			}
			if(value.contains("경상남도")){
				srtAddr = value.replaceAll("경상남도", "경남");
			}
			if(value.contains("경상북도")){
				srtAddr = value.replaceAll("경상북도", "경북");
			}
			if(value.contains("전라남도")){
				srtAddr = value.replaceAll("전라남도", "전남");
			}
			if(value.contains("전라북도")){
				srtAddr = value.replaceAll("전라북도", "전북");
			}
			if(value.contains("충청남도")){
				srtAddr = value.replaceAll("충청남도", "충남");
			}
			if(value.contains("충청북도")){
				srtAddr = value.replaceAll("충청북도", "충북");
			}
			return srtAddr;
		}
	}
	
	public static class PhoneNum {
		/**
		 * 구분자가 없는 전화번호를 구분자(-)가 있는 전화번호로 제공한다.
		 * 
		 * @param num
		 * @return
		 */
		public static String formatPhoneNum(String num) {
			String result = num;
			String temp = num.replaceAll(" ", "").replaceAll("-", "");
			int len = temp.length();

			if(len < 9) {
				result = num;
			} else if(len >=9 && len<=11) {
				String prefix = temp.substring(1,2);
				if(prefix.equals("1")) {	// 휴대전화번호 '010, 011...'
					if(len == 10) { // 10자리(010-123-4567)
						result = temp.substring(0, 3);
						result += "-";
						result += temp.substring(3, 6);
						result += "-";
						result += temp.substring(6, 10);
					} else if(len == 11){	// 11자리(010-1234-5678)
						result = temp.substring(0, 3);
						result += "-";
						result += temp.substring(3, 7);
						result += "-";
						result += temp.substring(7, 11);
					}
				}else if(prefix.equals("2")){	// 지역번호 서울(02)
					if(len == 9) {	// 9자리(02-345-5678)
						result = temp.substring(0, 2);
						result += "-";
						result += temp.substring(2, 5);
						result += "-";
						result += temp.substring(5, 9);
					} else if(len == 10) {	// 10자리(02-3456-7890)
						result = temp.substring(0, 2);
						result += "-";
						result += temp.substring(2, 6);
						result += "-";
						result += temp.substring(6, 10);
					}
				} else {	// 지방번호(031, 051...)
					if(len == 10) {	// 10자리(031-123-4567)
						result = temp.substring(0, 3);
						result += "-";
						result += temp.substring(3, 6);
						result += "-";
						result += temp.substring(6, 10);
					} else if(len == 11) {	// 11자리(031-3456-7890)
						result = temp.substring(0, 3);
						result += "-";
						result += temp.substring(3, 7);
						result += "-";
						result += temp.substring(7, 11);
					}
				}
			} else {
				return result;
			}
			return result;
		}
		
		/**
		 * 콤마(-) 구분자가 있는 전화번호를을 구분자가 없는 전화번호로 제공한다.
		 * @param num
		 * @return
		 */
		public static String  nonformatPhoneNum(String num) {
			String result = "";
			
			result = num.replaceAll("-", "");
			
			return result;
		}
	}
}