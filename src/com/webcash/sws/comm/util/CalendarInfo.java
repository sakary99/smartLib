package com.webcash.sws.comm.util;

import java.util.Calendar;

public class CalendarInfo {
	public static class MonthInfo {
		public int		mYear, mMonth;
		public int		mDayOfFirst;
		public int		mDaysOfMonth;

		public void setMonth(int year, int month) {
			Calendar	cal = Calendar.getInstance();

			cal.set(year, month, 1);

			mYear		= cal.get(Calendar.YEAR);
			mMonth		= cal.get(Calendar.MONTH);

			mDayOfFirst		= cal.get(Calendar.DAY_OF_WEEK);

			cal.set(mYear, mMonth + 1, 0);

			mDaysOfMonth	= cal.get(Calendar.DAY_OF_MONTH);			
		}
		
		public void prevMonth() {
			setMonth(this.mYear, this.mMonth - 1);
		}

		public void nextMonth() {
			setMonth(this.mYear, this.mMonth + 1);
		}	
	}
	
	
	/**
	 * 주 정보
	 * @author Administrator
	 *
	 */
	public static class WeekInfo {
		private int		mWeeks;
		private int		mDays;
		public int  	mStartYear, mStartMonth, mStartDay;		// 주의 시작일자(일요일)
		public int 		mEndYear, mEndMonth, mEndDay;			// 주의 종료일자(토요일)
		public int  	mDaysYear, mDaysMonth, mDaysDay;		// 주간검색시 리스트일자로 사용
		
		public WeekInfo() {
			setWeek(0);
		}

		private void setWeek(int weeks) {
			Calendar	cal = Calendar.getInstance();		
			cal.set(Calendar.WEEK_OF_MONTH, cal.get(Calendar.WEEK_OF_MONTH) + weeks);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); 		
			mStartYear = cal.get(Calendar.YEAR);		
			mStartMonth = cal.get(Calendar.MONTH) + 1;		
			mStartDay = cal.get(Calendar.DATE);	
			// 리스트 일자(주의 시작일자로 셋팅)
			mDaysYear = cal.get(Calendar.YEAR);		
			mDaysMonth = cal.get(Calendar.MONTH) + 1;		
			mDaysDay = cal.get(Calendar.DATE);

			cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY); 			
			mEndYear = cal.get(Calendar.YEAR);		
			mEndMonth = cal.get(Calendar.MONTH) + 1;		
			mEndDay = cal.get(Calendar.DATE);
		}

		// 다음주
		public void setNextWeek() {
			mWeeks++;
			setWeek(mWeeks);
		}

		// 이전주
		public void setPrevWeek() {
			mWeeks--;
			setWeek(mWeeks);
		}
		
		// 다음주, 이전주 값이 있을경우
		public void setWeekPage(int page){
			setWeek(page);
		}
		
		// 리스트일자 mDays만큼 증가
		private void setDays(int mDays) {
			Calendar	cal = Calendar.getInstance();
			cal.set(mStartYear, mStartMonth - 1, mStartDay);
			cal.add(Calendar.DATE, mDays);
			mDaysYear = cal.get(Calendar.YEAR);		
			mDaysMonth = cal.get(Calendar.MONTH) + 1;		
			mDaysDay = cal.get(Calendar.DATE);

		}

		// 다음날
		public void setNextDay() {		
			Calendar	cal = Calendar.getInstance();
			cal.set(mDaysYear, mDaysMonth - 1, mDaysDay);
			cal.add(Calendar.DATE, 1);
			mDaysYear = cal.get(Calendar.YEAR);		
			mDaysMonth = cal.get(Calendar.MONTH) + 1;		
			mDaysDay = cal.get(Calendar.DATE);
			
		}
		
		public String getStartDate() {
			return Convert.pubCharL(String.valueOf(mStartYear), 4, "0")
			  	 + Convert.pubCharL(String.valueOf(mStartMonth), 2, "0")
			  	 + Convert.pubCharL(String.valueOf(mStartDay), 2, "0");
		}
		
		public String getEndDate() {
			return Convert.pubCharL(String.valueOf(mEndYear), 4, "0")
		  	 + Convert.pubCharL(String.valueOf(mEndMonth), 2, "0")
		  	 + Convert.pubCharL(String.valueOf(mEndDay), 2, "0");
		}
	}

	/**
	 * 일 정보
	 * @author Administrator
	 *
	 */
	public static class DayInfo {
		private int		mGrow;
		public int  	mYear, mMonth, mDate;
		public String   mDay;
		
		/**
		 * 일 정보를 당일로 초기화
		 */
		public DayInfo() {
			setDay(0);
		}
		
		/**
		 * 일 정보를 입력일로 초기화
		 * @param 초기화 일자
		 */
		public DayInfo(String date) {
			int year, month, day;
			year = Integer.parseInt(date.substring(1, 4));
			month = Integer.parseInt(date.substring(4, 6)) - 1;
			day = Integer.parseInt(date.substring(6, 8));
			Calendar	cal = Calendar.getInstance();
			cal.set(year, month, day);
			
			mYear = cal.get(Calendar.YEAR);		
			mMonth = cal.get(Calendar.MONTH) + 1;		
			mDate = cal.get(Calendar.DATE);		
		}
		
		private void setDay(int grow) {
			Calendar	cal = Calendar.getInstance();		
			cal.add(Calendar.DATE, grow);
			mYear = cal.get(Calendar.YEAR);		
			mMonth = cal.get(Calendar.MONTH) + 1;		
			mDate = cal.get(Calendar.DATE);	
			
			mDay = Convert.ComDate.getDayOfWeek( Convert.ComDate.formatNoneDelimiter(mYear, mMonth, mDate) );
		}

		public void setNextDay() {
			mGrow++;
			setDay(mGrow);
		}
		
		public void setPrevDay() {
			mGrow--;
			setDay(mGrow);
		}	
		
		public String getDate() {
			return Convert.pubCharL(String.valueOf(mYear), 4, "0")
		  	 + Convert.pubCharL(String.valueOf(mMonth), 2, "0")
		  	 + Convert.pubCharL(String.valueOf(mDate), 2, "0");
		}
	}
}
