package com.chdryra.android.reviewer;

import java.util.Comparator;

import com.chdryra.android.mygenerallibrary.GVString;
import com.chdryra.android.mygenerallibrary.ViewHolder;

public class GVProConList extends GVReviewDataList<GVProConList.GVProCon> {
	
	public void add(String string, boolean isPro) {
		if(string != null && string.length() > 0)
			mData.add(new GVProCon(string, isPro));
	}
	
	public boolean contains(String string, boolean isPro) {
		return mData.contains(new GVProCon(string, isPro));
	}
	
	public void remove(String string, boolean isPro) {
		remove(new GVProCon(string, isPro));
	}

	@Override
	protected Comparator<GVProCon> getDefaultComparator() {
		return new Comparator<GVProCon>() {
			@Override
			public int compare(GVProCon lhs, GVProCon rhs) {				
				int comp = lhs.toString().compareTo(rhs.toString());
				if(comp == 0) {
					if(lhs.isPro() && !rhs.isPro())
						comp = 1;
					else if(rhs.isPro() && !lhs.isPro())
						comp = -1;
				}
				
				return comp;
			}
		};
	}

	@Override
	public GVType getDataType() {
		return GVType.PROCONS;
	}	
	
	class GVProCon extends GVString {
		private boolean mIsPro;
		
		public GVProCon(String proCon, boolean isPro) {
			super(proCon);
			mIsPro = isPro;
		}
		
		public boolean isPro() {
			return mIsPro;
		}
		
		@Override
		public ViewHolder getViewHolder() {
			return new VHProConView(mIsPro);
		}
	}
}
