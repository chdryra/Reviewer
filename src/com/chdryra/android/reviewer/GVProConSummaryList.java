package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVDualString;
import com.chdryra.android.mygenerallibrary.GVList;
import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.GVProConList.GVProCon;

public class GVProConSummaryList extends GVList<GVProConSummaryList.GVProConSummary> {
	private GVProConList mPros = new GVProConList();
	private GVProConList mCons = new GVProConList();
	
	public GVProConSummaryList(GVProConList pros, GVProConList cons) {
		add(pros, cons);
	}
	
	public void add(GVProConList pros, GVProConList cons) {
		mPros.add(pros);
		mCons.add(cons);
	}

	@Override
	public int size() {
		return mPros.size() == 0 && mCons.size() == 0? 0 : 1;
	}
	
	@Override
	public GVProConSummary getItem(int position) {
		String upper = mPros.size() == 1? "+" + mPros.getItem(position).toString() : "+" + String.valueOf(mPros.size());
		String lower = mCons.size() == 1? "-" + mCons.getItem(position).toString() : "-" + String.valueOf(mCons.size());
		
		return new GVProConSummary(upper, lower);
	}
	
	public GVProCon getFirstPro() {
		if(mPros.size() > 0)
			return mPros.getItem(0);
		else
			return null;
	}
	
	public GVProCon getFirstCon() {
		if(mCons.size() > 0)
			return mCons.getItem(0);
		else
			return null;
	}
	
	public int getNumPros() {
		return mPros.size();
	}
	
	public int getNumCons() {
		return mCons.size();
	}
	
	class GVProConSummary extends GVDualString {
		
		private GVProConSummary(String upper, String lower) {
			super(upper, lower);
		}
		
		@Override
		public ViewHolder getViewHolder() {
			return new VHProConSummaryView();
		}
	}
}
