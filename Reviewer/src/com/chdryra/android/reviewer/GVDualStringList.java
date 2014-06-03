package com.chdryra.android.reviewer;

import java.util.Comparator;

import com.chdryra.android.mygenerallibrary.GVList;

public class GVDualStringList extends GVList<GVDualString>{

	public GVDualStringList() {
	}

	public void add(String upper, String lower) {
		add(new GVDualString(upper, lower));
	}
	
	public boolean contains(String upper, String lower) {
		return contains(new GVDualString(upper, lower));
	}
	
	public void remove(String upper, String lower) {
		remove(new GVDualString(upper, lower));
	}
	
	@Override
	protected Comparator<GVDualString> getDefaultComparator() {
		
		return new Comparator<GVDualString>() {
			@Override
			public int compare(GVDualString lhs, GVDualString rhs) {
				int comp = lhs.getUpper().compareTo(rhs.getUpper());
				if(comp == 0)
					comp = lhs.getLower().compareTo(rhs.getLower());
				
				return comp;
			}
		};
	}
}
