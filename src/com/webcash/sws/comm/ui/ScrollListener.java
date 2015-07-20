package com.webcash.sws.comm.ui;

import java.lang.reflect.Method;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class ScrollListener implements ListView.OnScrollListener{
	public static final int INIT_POSITION = -1;
	
	private Context mContext = null;

	private int m_iPos = INIT_POSITION;
	private int m_iPrevTotalItemCount = 0;

	public ScrollListener(Context cxt) {
		mContext = cxt;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if(m_iPrevTotalItemCount != totalItemCount) {
			m_iPrevTotalItemCount = totalItemCount;
			resetPosition();
		}
		
		if(totalItemCount == visibleItemCount){

			 try {
				 Class<?> cls = mContext.getClass();
				 Method method = cls.getMethod("endOfList");
				 method.invoke(mContext);
			 } catch(Exception e) {
				 e.printStackTrace();
			 }
				
			 m_iPos = view.getLastVisiblePosition(); 
		}
	}

	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		 if(isChangedPos(view.getLastVisiblePosition()) &&
				isEnd(view.getLastVisiblePosition(), view.getCount()) && 
				OnScrollListener.SCROLL_STATE_IDLE == scrollState) {

			 try {
				 Class<?> cls = mContext.getClass();
				 Method method = cls.getMethod("endOfList");
				 method.invoke(mContext);
			 } catch(Exception e) {
				 e.printStackTrace();
			 }
				
			 m_iPos = view.getLastVisiblePosition(); 
		}
	}

	public boolean isChangedPos(int pos) {
		if(m_iPos != pos)
			return true;
		else
			return false;
	}
	
	public boolean isEnd(int visiblePosition, int count) {
		if(visiblePosition == count - 1)
			return true;
		else 
			return false;
	}
	
	public void resetPosition() {
		m_iPos = INIT_POSITION;
	}
}
